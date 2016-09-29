package jdbc.test;
import static org.junit.Assert.*;
import java.sql.SQLException;
import org.junit.Test;
import com.cdut.Dao.DAO;
public class testDao {
		@Test
		public void testGet() throws SQLException {
			String sql="SELECT id Id,loginName 用户名  FROM user WHERE id=?";

		}
	}


