package jdbc.test;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.cdut.Dao.DAO;
import com.cdut.bean.User;
public class testDao {
		@Test
		public void testGet() throws SQLException {
			//String sql="SELECT id Id,loginName 用户名  FROM user WHERE id=?";
			//以上sql语句有问题 导致得不到对象
			String sql="SELECT *  FROM user WHERE id=?";
			User user=DAO.get(User.class,sql,90);
			System.out.println(user);
			String sql1="SELECT *  FROM user ";
			List<User> list=DAO.getForList(User.class,sql1,
					"id","loginName","userName","password","gender","age","note");
			System.out.println(list.toString());
		}
	}


