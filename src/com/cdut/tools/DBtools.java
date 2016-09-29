package com.cdut.tools;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @author huchaochao
 *
 */
public class DBtools {
	/*static String user="root";
	static String password="admin";
	static String driverClass="com.mysql.jdbc.Driver";
	static String dbUrl="jdbc:mysql://localhost/jxgl?useUnicode=true&amp;characterEncoding=UTF-8";*/
	
	public static java.sql.Connection connectToDB() throws Exception{
		Properties properties=new Properties();
		InputStream inStream=DBtools.class.getClassLoader()
				.getResourceAsStream("db.properties");
		properties.load(inStream);
		String user=properties.getProperty("user");
		String password=properties.getProperty("password");
		String dbUrl=properties.getProperty("dbUrl");
		String driverClass=properties.getProperty("driverClass");
		
		Connection connection=null;
	//加载驱动
	 try{
		 Class.forName(driverClass).newInstance(); 
		 connection=DriverManager.getConnection(dbUrl,user,password);
	 }catch(ClassNotFoundException e){
 		System.out.println("加载驱动程序出错");
 		e.printStackTrace();
 	}
 	catch(Exception e){
 		System.out.println("出现了错误");
 		e.printStackTrace();
 	}
 		return connection;
	
	}
	/**
	 * @param resultset
	 * @param statement
	 * @param connection
	 */
	public static void releaseDB(ResultSet resultset ,Statement  statement, Connection  connection){
		if(resultset !=null){
			try {
				resultset.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(statement !=null){
			try {
				statement.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		if(connection !=null){
			try {
				connection.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	/**
	 * 执行SQL方法
	 * @param sql insert update delete 不包含select；可变参数
	 */
	public static void update(String sql,Object...args){
		 Connection  connection=null;
		 PreparedStatement  preparedstatement=null;
		 //ResultSet resultset=null;
		 try {
			 connection=connectToDB();
			 preparedstatement=connection.prepareStatement(sql);
			
			 for(int i=0;i<args.length;i++){
				 preparedstatement.setObject(i+1, args[i]);
			 }
			 preparedstatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBtools.releaseDB(null, preparedstatement, connection);
		}
	}
}
