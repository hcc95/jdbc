package jdbc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cdut.tools.DBtools;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class GetKeyValue {
	
	/**
	 * 主要用于一个订单绑定商品
	 */
	@Test
	public void testGetKeyValue() {
		Connection connection=null;
		PreparedStatement prepareStatement=null;
		try {
			connection=(Connection) DBtools.connectToDB();
			String sql="insert into user(loginName,userName,gender) value(?,?,?)";
			prepareStatement=(PreparedStatement) connection.prepareStatement(sql);
			prepareStatement.setString(1, "hcc");
			prepareStatement.setString(2, "hcc");
			prepareStatement.setString(3, "hcc");
			prepareStatement.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBtools.releaseDB(null, prepareStatement, connection);
		}
	} 

}
