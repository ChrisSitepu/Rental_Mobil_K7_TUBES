package service;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.*;

public class AuthService {

    public AuthResponse login(String email, String password) {

        String sql =
            "SELECT
            SELECT
                u.id_user,
                m.id_member,
                p.id_pegawai,
                u.nama,
                u.email,
                u.password,
                u.no_telp,
                u.alamat,
                m.id_member,
                p.id_pegawai,
                p.id_cabang,
                j.nama_jabatan,
                m.status AS member_status,
                p.status AS pegawai_status,
                j.nama_jabatan,
                CASE
                    WHEN m.id_member IS NOT NULL THEN 'MEMBER'
                    WHEN j.nama_jabatan = 'Manager' THEN 'MANAGER'
                    WHEN p.id_pegawai IS NOT NULL THEN 'PEGAWAI'
                END AS role

            FROM Users u
            LEFT JOIN Member m ON u.id_user = m.id_user
            LEFT JOIN Pegawai p ON u.id_user = p.id_user
            LEFT JOIN Jabatan j ON p.id_jabatan = j.id_jabatan
            WHERE u.email = ?
            """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return new AuthResponse(AuthStatus.ACCOUNT_NOT_FOUND, null);
            }

            String dbPassword = rs.getString("password");

            if (!dbPassword.equals(password)) {
                return new AuthResponse(AuthStatus.WRONG_PASSWORD, null);
            }

            // check account status before allowing login
            String memberStatus = null;
            try { memberStatus = rs.getString("member_status"); } catch (Exception ignored) {}
            String pegawaiStatus = null;
            try { pegawaiStatus = rs.getString("pegawai_status"); } catch (Exception ignored) {}

            if (memberStatus != null && !memberStatus.equalsIgnoreCase("Aktif")) {
                return new AuthResponse(AuthStatus.ACCOUNT_NOT_FOUND, null);
            }
            if (pegawaiStatus != null && !pegawaiStatus.equalsIgnoreCase("Aktif")) {
                return new AuthResponse(AuthStatus.ACCOUNT_NOT_FOUND, null);
            }

            Role role = Role.valueOf(rs.getString("role"));

            // 🔥 SAFE READ idPegawai (anti NULL jadi 0 masalah FK)
            int idPegawai = rs.getInt("id_pegawai");
            if (rs.wasNull()) {
                idPegawai = -1;
            }

            int idMember = rs.getInt("id_member");
            if (rs.wasNull()) {
                idMember = -1;
            }

            User user = new User(
                rs.getInt("id_user"),
                idMember,
                idPegawai,
                rs.getString("nama"),
                rs.getString("email"),
                dbPassword,
                rs.getString("no_telp"),
                null,
                null,
                role
            );

            if (role != Role.PEGAWAI) {
                user.setIdPegawai(-1);
            }

            return new AuthResponse(AuthStatus.SUCCESS, user);

        } catch (Exception e) {
            e.printStackTrace();
            return new AuthResponse(AuthStatus.ACCOUNT_NOT_FOUND, null);
        }
    }

    public boolean register(
        String nama,
        String email,
        String password,
        String noTelp,
        String alamat,
        String noSim
    ) {

        String cekEmail = "SELECT id_user FROM Users WHERE email = ?";

        String insertUser =
            """
            INSERT INTO Users
            (nama,email,no_telp,alamat,password,role)
            VALUES (?,?,?,?,?,'MEMBER')
            """;

        String insertMember =
            """
            INSERT INTO Member
            (id_user,no_sim,tanggal_daftar,status)
            VALUES (?, ?, GETDATE(), 'Aktif')
            """;

        try (
            Connection conn = SQLDatabaseConnection.getConnection()
        ) {

            conn.setAutoCommit(false);

            PreparedStatement cek = conn.prepareStatement(cekEmail);
            cek.setString(1, email);

            ResultSet rs = cek.executeQuery();

            if (rs.next()) {
                return false;
            }

            PreparedStatement psUser =
                conn.prepareStatement(insertUser, PreparedStatement.RETURN_GENERATED_KEYS);

            psUser.setString(1, nama);
            psUser.setString(2, email);
            psUser.setString(3, noTelp);
            psUser.setString(4, alamat);
            psUser.setString(5, password);

            psUser.executeUpdate();

            ResultSet generatedKeys = psUser.getGeneratedKeys();

            if (!generatedKeys.next()) {
                conn.rollback();
                return false;
            }

            int idUser = generatedKeys.getInt(1);

            PreparedStatement psMember =
                conn.prepareStatement(insertMember);

            psMember.setInt(1, idUser);
            psMember.setString(2, noSim);

            psMember.executeUpdate();

            conn.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}