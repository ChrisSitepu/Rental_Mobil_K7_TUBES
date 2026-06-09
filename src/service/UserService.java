package service;

import config.SQLDatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import model.Member;
import model.Role;
import model.User;

public class UserService {

    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT u.id_user, u.nama, u.email, u.password, u.no_telp, u.alamat, " +
                "m.id_member, m.no_sim, p.id_pegawai, j.nama_jabatan " +
                "FROM Users u " +
                "LEFT JOIN Member m ON u.id_user = m.id_user " +
                "LEFT JOIN Pegawai p ON u.id_user = p.id_user " +
                "LEFT JOIN Jabatan j ON p.id_jabatan = j.id_jabatan " +
                "WHERE u.email = ? AND u.password = ?";

        try (Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Role role = null;

                    String jab = rs.getString("nama_jabatan");
                    int idMem = rs.getInt("id_member");
                    if (rs.wasNull()) idMem = 0;
                    
                    int idPeg = rs.getInt("id_pegawai");
                    if (rs.wasNull()) idPeg = 0;

                    if (idPeg > 0) {
                        if (jab != null && jab.trim().equalsIgnoreCase("Manager")) {
                            role = Role.MANAGER;
                        } else {
                            role = Role.PEGAWAI;
                        }
                    } else if (idMem > 0) {
                        role = Role.MEMBER;
                    }

                    if (role == null) {
                        return null;
                    }

                    return new User(
                            rs.getInt("id_user"),
                            idMem,
                            idPeg,
                            rs.getString("nama"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("no_telp"),
                            rs.getString("alamat"),
                            (rs.getString("no_sim") != null) ? rs.getString("no_sim") : "-",
                            role);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean registerMember(Member member) {
        if (member.getNama().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Nama tidak boleh kosong");
        }
        if (member.getAlamat().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Alamat tidak boleh kosong");
        }
        if (!member.getEmail().toLowerCase().endsWith("@gmail.com")) {
            throw new IllegalArgumentException(
                    "Email harus menggunakan @gmail.com");
        }

        if (member.getPassword().length() < 8) {
            throw new IllegalArgumentException(
                    "Password minimal 8 karakter");
        }

        if (!member.getNomorTelepon().matches("\\d+")) {
            throw new IllegalArgumentException(
                    "Nomor telepon hanya boleh berisi angka");
        }

        if (member.getNomorTelepon().length() < 10 ||
                member.getNomorTelepon().length() > 15) {

            throw new IllegalArgumentException(
                    "Nomor telepon harus 10-15 digit");
        }

        if (!member.getTanggalBerlakuSim().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "SIM sudah tidak berlaku");
        }

        if (emailExists(member.getEmail())) {
            throw new IllegalArgumentException(
                    "Email sudah terdaftar");
        }
        String sqlUser = "INSERT INTO Users " +
                "(id_user, nama, email, no_telp, alamat, password) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlMember = "INSERT INTO Member " +
                "(id_user, no_sim, tanggal_berlaku_sim, tanggal_daftar, status) " +
                "VALUES (?, ?, ?, GETDATE(), 'Aktif')";

        Connection conn = null;

        try {
            conn = SQLDatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            int nextUserId = getNextUserId(conn);

            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                stmtUser.setInt(1, nextUserId);
                stmtUser.setString(2, member.getNama());
                stmtUser.setString(3, member.getEmail());
                stmtUser.setString(4, member.getNomorTelepon());
                stmtUser.setString(5, member.getAlamat());
                stmtUser.setString(6, member.getPassword());

                stmtUser.executeUpdate();
            }

            try (PreparedStatement stmtMember = conn.prepareStatement(sqlMember)) {
                stmtMember.setInt(1, nextUserId);
                stmtMember.setString(2, member.getNoSim());
                stmtMember.setDate(3, java.sql.Date.valueOf(member.getTanggalBerlakuSim()));

                stmtMember.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();

            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return false;

        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public ArrayList<User> getAllByRole(Role targetRole) {
        ArrayList<User> list = new ArrayList<>();

        String sql = "SELECT u.*, m.id_member, m.no_sim, p.id_pegawai, j.nama_jabatan " +
                "FROM Users u " +
                "LEFT JOIN Member m ON u.id_user = m.id_user " +
                "LEFT JOIN Pegawai p ON u.id_user = p.id_user " +
                "LEFT JOIN Jabatan j ON p.id_jabatan = j.id_jabatan";

        try (Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Role role = null;

                String jab = rs.getString("nama_jabatan");
                int idMem = rs.getInt("id_member");
                int idPeg = rs.getInt("id_pegawai");

                if (idPeg > 0) {
                    if (jab != null && jab.equalsIgnoreCase("Manager")) {
                        role = Role.MANAGER;
                    } else {
                        role = Role.PEGAWAI;
                    }
                } else if (idMem > 0) {
                    role = Role.MEMBER;
                }

                if (role == targetRole) {
                    list.add(new User(
                            rs.getInt("id_user"),
                            idMem,
                            idPeg,
                            rs.getString("nama"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("no_telp"),
                            rs.getString("alamat"),
                            rs.getString("no_sim") != null ? rs.getString("no_sim") : "-",
                            role));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteUserByEmail(String email) {
        String findUser = "SELECT id_user FROM Users WHERE email = ?";
        String updateMember = "UPDATE Member SET status = 'Nonaktif' WHERE id_user = ?";
        String updatePegawai = "UPDATE Pegawai SET status = 'Nonaktif' WHERE id_user = ?";

        Connection conn = null;
        try {
            conn = SQLDatabaseConnection.getConnection();
            PreparedStatement psFind = conn.prepareStatement(findUser);
            psFind.setString(1, email);
            ResultSet rs = psFind.executeQuery();

            if (!rs.next()) {
                return false;
            }

            int idUser = rs.getInt("id_user");

            conn.setAutoCommit(false);
            boolean updated = false;

            try (PreparedStatement psMem = conn.prepareStatement(updateMember)) {
                psMem.setInt(1, idUser);
                if (psMem.executeUpdate() > 0) updated = true;
            }

            try (PreparedStatement psPeg = conn.prepareStatement(updatePegawai)) {
                psPeg.setInt(1, idUser);
                if (psPeg.executeUpdate() > 0) updated = true;
            }

            conn.commit();
            return updated;

        } catch (SQLException e) {
            System.out.println("Gagal menghapus: pastikan data member/pegawai terkait sudah dihapus.");
            return false;
        }
    }

    private int getNextUserId(Connection conn) throws SQLException {
        String sql = "SELECT MAX(id_user) FROM Users";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
        }

        return 1;
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM Users WHERE email = ?";

        try (Connection conn = SQLDatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}