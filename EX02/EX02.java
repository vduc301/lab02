package EX02;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EX02 {
    public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // connect way #1
        String url1 = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String password = "";
		try {
			Connection connection=DriverManager.getConnection(url1, user, password);
            System.out.println("ket noi thanh cong");

            // Tạo bảng
            String sql = "Create table student(name nvarchar(30) primary key, age int)";
            Statement stm = (Statement) connection.createStatement();
            stm.executeUpdate(sql);
            System.out.println("Tao bang thanh cong");

            // insert bảng
            String sql1 = "insert into student value(?,?)";
            PreparedStatement ptm = (PreparedStatement) connection.prepareStatement(sql1);

            ptm.setString(1, "Phan Le Thanh Dat");
            ptm.setInt(2, 21);

            int rows = ptm.executeUpdate();

            if (rows == 1 ){
                System.out.println("Them thanh cong");
            }
            stm.close();

			connection.close();
		} catch (Exception ex) {
			// TODO: handle exception
			System.out.println("An error occurred. Maybe user/password is invalid");
//            ex.printStackTrace();
		}

	}
}
