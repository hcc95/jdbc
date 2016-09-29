package jdbc.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;
import com.cdut.tools.DBtools;
public class testDB {

	@Test
	public void testDB() throws Exception {
		Statement st = null;
		Connection conn = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM student";
		conn = DBtools.connectToDB();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString("Sno")+"  "+rs.getString("Sname"));
			}
			st.close();
			rs.close();
			conn.close();
			conn = null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
