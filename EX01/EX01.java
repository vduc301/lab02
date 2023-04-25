import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EX01 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // connect way #1
        String url1 = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String password = "";
		try {
			Connection connection=DriverManager.getConnection(url1, user, password);
            System.out.println("ket noi thanh cong");
            // String sql = "Create table student(name nvarchar(30) primary key, age int";
            // Statement stm = (Statement) connection.createStatement();
            // stm.executeUpdate(sql);
            // stm.close();

			connection.close();
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("An error occurred. Maybe user/password is invalid");
//            ex.printStackTrace();
		}

	}
}