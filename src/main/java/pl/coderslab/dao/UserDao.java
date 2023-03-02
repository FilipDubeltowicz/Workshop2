package pl.coderslab.dao;

import pl.coderslab.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users(user_name, user_email, password) VALUES (?, ?, ?)";
    private static final String SELECT_USER_BYID_QUERY = "SELECT * FROM users WHERE id = ?;";
    private static final String SELECT_USER_ALL_QUERY = "SELECT * FROM users";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET user_name=?, user_email=?, password=? WHERE id = ?;";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id=?";


    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        try (Connection connection = DbUtil.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_ALL_QUERY);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long userId = rs.getLong("id");
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                String userPassword = rs.getString("password");

                User user = new User(userId, userName, userPassword, userEmail);
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public User findById(Long id) {

        try (Connection connection = DbUtil.connect()) {

            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BYID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Long userId = rs.getLong("id");
                String userName = rs.getString("user_name");
                String userEmail = rs.getString("user_email");
                String userPassword = rs.getString("password");

                User user = new User(userId, userName, userPassword, userEmail);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User create(User user) {

        if (user == null) return null;

        try (Connection connection = DbUtil.connect()) {

            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = preparedStatement.getGeneratedKeys();
                while (rs.next()) {
                    Long userId = rs.getLong(1);
                    user.setId(userId);
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User update(User user) {
        if (user == null) return null;

        try (Connection connection = DbUtil.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_QUERY);

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setLong(4, user.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(User user) {
        if (user == null) return false;
        return delete(user.getId());
    }

    public boolean delete(Long id) {

        try (Connection connection = DbUtil.connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_QUERY);

            preparedStatement.setLong(1, id);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}