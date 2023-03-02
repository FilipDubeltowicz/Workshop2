package pl.coderslab.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";
    private static final String  DB_URL = "jdbc:mysql://localhost:3306/{db_name}?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";
    private static final String  DB_USER = "root";
    private static final String  DB_PASS = "coderslab";

    public static Connection connect() throws SQLException {
        return connect("workshop2");
    }
    public static Connection connect(String db) throws SQLException {
        return DriverManager.getConnection(DB_URL.replace("{db_name}",db),DB_USER,DB_PASS);
    }

    public static void insert (Connection conn, String query, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData(Connection conn, String query, String... columnNames)  {

        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                for (String columnName : columnNames) {
                    System.out.println(resultSet.getString(columnName));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static List<List<String>> executeQuery(Connection conn, String sql, String... params){
        List<List<String>> rows = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql)){

            for (int i = 0; i <params.length ; i++) {
                ps.setString(i+1,params[i]);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                List<String> row = new ArrayList<>();
                for (int i = 0; i < rs.getMetaData().getColumnCount() ; i++) {//<- ile kolumn wyszlo z sqla
                    String value = rs.getString(i+1);
                    row.add(value);
                }
                rows.add(row);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return rows;
    }

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
