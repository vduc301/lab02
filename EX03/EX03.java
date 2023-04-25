
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EX03 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // connect way #1
        String url1 = "jdbc:mysql://localhost:3306/demo";
        String user = "root";
        String password = "";
		try (Connection connection=DriverManager.getConnection(url1, user, password);
			Statement stm = (Statement) connection.createStatement();)
			{
				String sql = "Select * from student";
				ResultSet data = stm.executeQuery(sql);
				List<Student> studentList = new ArrayList<Student>();

				while(data.next()){
					String name = data.getString(1);
					int age = data.getInt(2);
					Student student = new Student(name, age);
					studentList.add(student);
				}

				for (Student s: studentList){
					System.out.println("Name: " + s.getName() + " - " + "Age: " + s.getAge());
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