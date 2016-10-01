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
			DAO dao=new DAO();
			String sql="SELECT *  FROM user WHERE id=?";
			User user=dao.get(User.class,sql,90);
			System.out.println(user);
			String sql1="SELECT *  FROM user ";//args的参数直接是通过sql解析
			List<User> list=dao.getForList(User.class,sql1);
			System.out.println(list.toString());
			
			String sql2="SELECT loginName FROM user WHERE id=?";
			String id=dao.getForValues(sql2, 90);
			System.out.println(id);
		}
	}


