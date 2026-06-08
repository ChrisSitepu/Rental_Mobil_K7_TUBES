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

                CASE
                    WHEN m.id_member IS NOT NULL THEN 'MEMBER'
                    WHEN p.id_jabatan = 1 THEN 'MANAGER'
                    WHEN p.id_jabatan = 2 THEN 'PEGAWAI'
                END AS role

            FROM Users u

            LEFT JOIN Member m
                ON u.id_user = m.id_user

            LEFT JOIN Pegawai p
                ON u.id_user = p.id_user

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

            Role role =
                Role.valueOf(
                    rs.getString("role")
                );

            User user = new User(
                rs.getString("nama"),
                rs.getString("email"),
                dbPassword,
                rs.getString("no_telp"),
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
            (nama,email,no_telp,alamat,password)
            VALUES
            (?,?,?,?,?)
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

        // insert user

        PreparedStatement psUser =
                conn.prepareStatement(
                    insertUser,
                    PreparedStatement.RETURN_GENERATED_KEYS
                );

        psUser.setString(1, nama);
        psUser.setString(2, email);
        psUser.setString(3, noTelp);
        psUser.setString(4, alamat);
        psUser.setString(5, password);

        psUser.executeUpdate();

        ResultSet generatedKeys =
                psUser.getGeneratedKeys();

        if(!generatedKeys.next()) {

            conn.rollback();
            return false;
        }

        int idUser =
                generatedKeys.getInt(1);

        // insert member

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