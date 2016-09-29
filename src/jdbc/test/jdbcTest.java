package jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;

public class jdbcTest {
	static String dbName="com.mysql.jdbc.Driver";
	static String dbUrl="jdbc:mysql://localhost:3306/jxgl?useUnicode=true&amp;characterEncoding=UTF-8";
	static String user="root";
	static String password="admin";
	@Test 
	public void testJDBC() throws  Exception{
		
		String connName=dbUrl;
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		try {
			Class.forName(dbName);
			conn=(Connection) DriverManager.getConnection(connName,user,password);
			
			String sql ="select * from user";
			stmt=conn.prepareStatement(sql);
			//stmt=conn.createStatement();
			rs=stmt.executeQuery(sql);
			if(rs.next()){
				System.out.println("用户名:"+rs.getString(2));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("连接错误");
		}finally{
			if(rs != null)
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			if(stmt != null)
				try {
					stmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			if(conn != null)
				try {
					conn.close();
				} catch (Exception e2) {
						// TODO: handle exception
					e2.printStackTrace();
				}
			conn=null;
		}
	}
}
