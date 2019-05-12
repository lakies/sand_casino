package server;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Helper;

import java.sql.*;

public class DatabaseHandler {

    private Connection connection;
    private final Argon2 argon2 = Argon2Factory.create();

    public void setupDatabase() {

        String url = "jdbc:sqlite:sand_casino.db";

        try {
            Connection conn = DriverManager.getConnection(url);
            if (conn != null) {
                connection = conn;
                setupTables(connection);
            }

        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    private void setupTables(Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	username varchar(20) NOT NULL CHECK (LENGTH(username) <= 20) UNIQUE PRIMARY KEY,\n"
                + "	password varchar NOT NULL,\n"
                + " coins integer NOT NULL,\n"
                + " last_free_spin integer NOT NULL);";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public void addUserToDatabase(String username, String password) {
        String hash = argon2.hash(20, 65536, 1, password);
        String sql = "INSERT INTO users\n"
                + " (username, password, coins, last_free_spin)\n"
                + " VALUES\n"
                + " (?,?,400,0);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hash);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
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
            throw new DatabaseException(e);
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
            throw new DatabaseException(e);
        }
    }

    public void saveCoins(String username, int coins) {
        String sql = "UPDATE users"
            + " SET coins = ?"
            + " WHERE username = ?";
        executeUpdate(username, coins, sql);
    }

    public int getCoins(String username) {
        String sql = "SELECT coins"
            + " FROM users"
            + " WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    public void saveLastFreeSpinTime(String username, long spinTime) {
        String sql = "UPDATE users"
            + " SET last_free_spin = ?"
            + " WHERE username = ?";
        executeUpdate2(username, spinTime, sql);
    }

    public long getLastFreeSpinTime(String username) {
        String sql = "SELECT last_free_spin"
            + " FROM users"
            + " WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }

    }

    private void executeUpdate(String username, int newValue, String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, newValue);
            preparedStatement.setString(2, username);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
    private void executeUpdate2(String username, long newValue, String sql) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, newValue);
            preparedStatement.setString(2, username);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

}
