package jdbc.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cdut.tools.DBtools;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.DatabaseMetaData;
import com.cdut.tools.DBtools;
public class MetaDataTest {

	
	/**
	 * DatabaseMetaData 是描述数据库的元数据对象
	 * 可以由Connection得到
	 */
	@Test
	public void testMetaDate() {
		Connection connection=null;
		try {
			connection=(Connection) DBtools.connectToDB();
			DatabaseMetaData data=(DatabaseMetaData) connection.getMetaData();
			
			//可以得到数据库本身的一些基本信息
			
			//数据库的版本号
			int version=data.getDatabaseMajorVersion();
			System.out.println(version);
			
			System.out.println(data.getUserName());
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			DBtools.releaseDB(null, null, connection);
		}
		
		
	}

}
