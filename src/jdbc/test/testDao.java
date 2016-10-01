package jdbc.test;
import java.sql.SQLException;

import org.junit.Test;

import com.cdut.Dao.DAO;
import com.cdut.bean.User;
public class testDao {
		@Test
		public void testGet() throws SQLException {
			String sql="SELECT id Id,loginName 用户名  FROM user WHERE id=?";
			User user=DAO.get(User.class,sql,90);
			System.out.println(user);
		}
	}


