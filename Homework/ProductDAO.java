package Homework;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO implements Repository<Product, Integer> {
    private String serverUrl;
    private String userName;
    private String passWord;

    public ProductDAO(String serverUrl, String userName, String passWord) {
        super();
        this.serverUrl = serverUrl;
        this.userName = userName;
        this.passWord = passWord;
        init();
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(this.serverUrl + "/ProductManagement", this.userName,
                    this.passWord);
            return conn;
        } catch (SQLException e) {
            System.out.println("Connection error! " + e);
            return null;
        }

    }

    public void init() {
        try {
            Connection conn = DriverManager.getConnection(this.serverUrl, this.userName, this.passWord);
            createDatabaseIfNotExist(conn);
            createProductTableIfNotExist(conn);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createDatabaseIfNotExist(Connection conn) {
        try {
            String sqlStatement1 = "CREATE DATABASE IF NOT EXISTS ProductManagement";
            PreparedStatement prestm1 = conn.prepareStatement(sqlStatement1);
            String sqlStatement2 = "use ProductManagement";
            PreparedStatement prestm2 = conn.prepareStatement(sqlStatement2);

            prestm1.executeUpdate();
            prestm2.execute();

            prestm1.close();
            prestm2.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createProductTableIfNotExist(Connection conn) {
        try {
            String sqlStatement1 = "DROP TABLE IF EXISTS Product";
            PreparedStatement preStm1 = conn.prepareStatement(sqlStatement1);
            String sqlStatement2 = "CREATE TABLE Product(id int NOT NULL AUTO_INCREMENT, name VARCHAR(200), price int, PRIMARY KEY (id))";
            PreparedStatement preStm2 = conn.prepareStatement(sqlStatement2);

            preStm1.executeUpdate();
            preStm2.executeUpdate();

            preStm1.close();
            preStm2.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Integer add(Product item) {
        Integer id = null;
        String sql = "INSERT INTO Product (name,price) values (?, ?)";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setNString(1, (String) item.getName());
            statement.setInt(2, (Integer) item.getPrice());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
            conn.commit();

            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<Product> readAll() {
        List<Product> results = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();

                product.setId(resultSet.getInt("id"));
                product.setName(resultSet.getNString("name"));
                product.setPrice(resultSet.getInt("price"));
                results.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public Product read(Integer id) {
        Product result = null;
        String sql = "SELECT * FROM Product WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement(sql);
            statement.setInt(1, (Integer) id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = new Product();
                result.setId(resultSet.getInt("id"));
                result.setName(resultSet.getNString("name"));
                result.setPrice(resultSet.getInt("price"));
            } else {
                return null;
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public boolean update(Product item) {
        String sql = "UPDATE Product SET name = ?, price = ? WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            statement.setNString(1, (String) item.getName());
            statement.setInt(2, (Integer) item.getPrice());
            statement.setInt(3, (Integer) item.getId());
            int rows = statement.executeUpdate();
            conn.commit();

            if (rows == 1)
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (statement != null)
                    statement.close();
                if (resultSet != null)
                    resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            statement = conn.prepareStatement(sql);
            statement.setInt(1, (Integer) id);
            int rows = statement.executeUpdate();
            conn.commit();

            if (rows == 1)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
                if (statement != null)
                    statement.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return false;
    }

}