package service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import config.SQLDatabaseConnection;
import model.*;

public class AuthService {

    public AuthResponse login(String email, String password) {

        String sql =
            """
            SELECT
                u.id_user,
                u.nama,
                u.email,
                u.password,
                u.no_telp,
                u.alamat,
                m.id_member,
                m.no_sim,
                p.id_pegawai,
                p.id_cabang,
                j.nama_jabatan,
                CASE
                    WHEN m.id_member IS NOT NULL THEN 'MEMBER'
                    WHEN j.nama_jabatan = 'Manager' THEN 'MANAGER'
                    WHEN p.id_pegawai IS NOT NULL THEN 'PEGAWAI'
                END AS role

            FROM Users u

            LEFT JOIN Member m
                ON u.id_user = m.id_user

            LEFT JOIN Pegawai p
                ON u.id_user = p.id_user
                
            LEFT JOIN Jabatan j
                ON p.id_jabatan = j.id_jabatan

            WHERE u.email = ?
            """;

        try (
            Connection conn =
                SQLDatabaseConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);
        ) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {

                return new AuthResponse(
                    AuthStatus.ACCOUNT_NOT_FOUND,
                    null
                );
            }

            String dbPassword =
                rs.getString("password");

            if (!dbPassword.equals(password)) {

                return new AuthResponse(
                    AuthStatus.WRONG_PASSWORD,
                    null
                );
            }

            String roleStr = rs.getString("role");
            if (roleStr == null) {
                return new AuthResponse(AuthStatus.ACCOUNT_NOT_FOUND, null);
            }
            
            Role role = Role.valueOf(roleStr);

            int idMem = rs.getInt("id_member");
            if (rs.wasNull()) idMem = 0;
            int idPeg = rs.getInt("id_pegawai");
            if (rs.wasNull()) idPeg = 0;
            int idCab = rs.getInt("id_cabang");
            if (rs.wasNull()) idCab = 0;

            User user = new User(
                rs.getInt("id_user"),
                idMem,
                idPeg,
                idCab,
                rs.getString("nama"),
                rs.getString("email"),
                dbPassword,
                rs.getString("no_telp"),
                rs.getString("alamat"),
                rs.getString("no_sim") != null ? rs.getString("no_sim") : "-",
                role
            );

            return new AuthResponse(
                AuthStatus.SUCCESS,
                user
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new AuthResponse(
                AuthStatus.ACCOUNT_NOT_FOUND,
                null
            );
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

    String cekEmail =
            "SELECT id_user FROM Users WHERE email = ?";

    String insertUser =
            """
            INSERT INTO Users
            (id_user, nama,email,no_telp,alamat,password)
            VALUES
            (?,?,?,?,?,?)
            """;

    String insertMember =
            """
            INSERT INTO Member
            (id_user,no_sim,tanggal_daftar,status)
            VALUES
            (?, ?, GETDATE(), 'Aktif')
            """;

    try (
        Connection conn =
            SQLDatabaseConnection.getConnection()
    ) {

        conn.setAutoCommit(false);

        // cek email sudah ada atau belum

        PreparedStatement cek =
                conn.prepareStatement(cekEmail);

        cek.setString(1, email);

        ResultSet rs = cek.executeQuery();

        if(rs.next()) {
            return false;
        }

        // Get next user id
        int nextId = 1;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id_user) FROM Users");
             ResultSet rsMax = stmt.executeQuery()) {
            if (rsMax.next()) {
                nextId = rsMax.getInt(1) + 1;
            }
        }

        // insert user

        PreparedStatement psUser =
                conn.prepareStatement(insertUser);

        psUser.setInt(1, nextId);
        psUser.setString(2, nama);
        psUser.setString(3, email);
        psUser.setString(4, noTelp);
        psUser.setString(5, alamat);
        psUser.setString(6, password);

        psUser.executeUpdate();

        // insert member

        PreparedStatement psMember =
                conn.prepareStatement(insertMember);

        psMember.setInt(1, nextId);
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