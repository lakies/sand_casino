package server;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

import java.sql.*;

public class DatabaseHandler {

    private Connection connection;
    final Argon2 argon2 = Argon2Factory.create();
    // Optimal number of iterations on this machine
    private final int iterations = Argon2Helper.findIterations(argon2, 1000, 65536, 1);


    public void setupDatabase() {

        String url = "jdbc:sqlite:sand_casino.db";

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                connection = conn;
                setupTables(connection);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void setupTables(Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	username varchar(20) NOT NULL CHECK (LENGTH(username) <= 20) UNIQUE PRIMARY KEY,\n"
                + "	password varchar NOT NULL,\n"
                + " coins integer NOT NULL\n"
                + ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addUserToDatabase(String username, String password) {
        String hash = argon2.hash(iterations, 65536, 1, password);
        String sql = "INSERT INTO users\n"
                + " (username, password, coins)\n"
                + " VALUES\n"
                + " (?,?,0);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hash);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkUsernameExists(String username) {
        String sql = "SELECT COUNT(username)\n"
                + " FROM users\n"
                + " WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1) > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public boolean checkPassword(String username, String password) {
        String sql = "SELECT password\n"
                + " FROM users\n"
                + " WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String hash = resultSet.getString("password");
                return argon2.verify(hash, password);
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
