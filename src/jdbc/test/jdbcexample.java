package jdbc.test;

import static org.junit.Assert.*;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class jdbcexample {

	@Test
	public void testConnnet() throws SQLException {
		String driverName="com.mysql.jdbc.Driver";
		String dbUrl="jdbc:mysql://localhost/jxgl?useUnicode=true&amp;characterEncoding=UTF-8";
		Connection con=null;
		Statement stmt=null;
		ResultSet result=null;
    	String connName = dbUrl;
    	String sql="SELECT * FROM USER";
		try {
			//Driver driver=new com.mysql.jdbc.Driver();
			Class.forName(driverName);
			con=(Connection) DriverManager.getConnection(connName,"root","123456");
//			if(con!= null){
//				System.out.println("连接成功");
//			}
//			System.out.println(con);
			stmt=(Statement) con.prepareStatement(sql);
			result = stmt.executeQuery(sql);
			if(result.next()){
				System.out.println("用户名:"+result.getString(2));
				System.out.println("登录名:"+result.getString(3));
				System.out.println("密码:"+result.getString(4));
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("加载驱动程序出错");
		}finally{
			result.close();
			stmt.close();
			con.close();
		}
	}

	private String getInt(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
