package com.cdut.Dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import com.cdut.tools.DBtools;

/**
 * @author Administrator
 *
 */
public class DAO {
	
	public static <T> T get(Class<T> clazz,String sql ,
			Object...args) throws SQLException{
		T entity=null;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			connection=DBtools.connectToDB();
			preparedStatement=connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet=preparedStatement.executeQuery();
			ResultSetMetaData rsmd=resultSet.getMetaData();
			//3 创建一个map对象
			Map<String,Object> values=new HashMap<>();
			//4 处理结果集 利用ResultSetMetaData填充3对用的map
			if(resultSet.next()){
				//Class clazz=User.class;
				//User user=new User();
				//user.setId(resultSet.getInt(1));
				//user.setLoginName(resultSet.getString(2));
				//通过解析sql语句来确定选择那些列
				for(int i=0;i<rsmd.getColumnCount();i++){
					String columnLabel=rsmd.getColumnLabel(i+1);
					Object columnValue=resultSet.getObject(i+1);
					values.put(columnLabel, columnValue);
				}
			}
			//5 若map不为空集 利用反射创建clazz对应对象
			if(values.size()>0){
				//利用反射来创建对象
				entity=clazz.newInstance();
				for(Map.Entry<String, Object> entry:values.entrySet()){
				//String filedName=entry.getKey();
				//Object filedValue=entry.getValue();
				//ReflectionUtils.setFieldValue(entity,filedName,filedValue)
				String propertyName=entry.getKey();
				Object propertyValue=entry.getValue();
				BeanUtils.setProperty(entity,propertyName,propertyValue);
				}
			}
		} catch ( Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBtools.releaseDB(resultSet, preparedStatement, connection);
		}
		return entity;
		
	}
	/**
	 * @param resultSet
	 * @param values
	 * @return 
	 * @throws SQLException
	 */	
	public <T> List<T> getForList(Class<T> clazz,String sql ,Object...args) throws SQLException{
		List<T> list=new ArrayList<>();
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			// 1  得到结果集
			connection=DBtools.connectToDB();
			preparedStatement=connection.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				preparedStatement.setObject(i+1, args[i]);
			}
			resultSet=preparedStatement.executeQuery();
			
			/*方法一：
			 * ResultSetMetaData rsmd=rs.getMetaData();
			Map<String,Object> map=null;
			while(rs.next()){
				map=new HashMap<>();
				for(int i=0;i<rsmd.getColumnCount();i++){
					String columnLabel=rsmd.getColumnLabel(i+1);
					Object value=rs.getObject(i+1);
					map.put(columnLabel, value);
				}
				values.add(map);
			}*/
			/*方法二
			 * List<String> columnLabels=getColumnLabel(rs);
			Map<String,Object> map=null;
			while(rs.next()){
				map=new HashMap<>();
				for(int i=0;i<columnLabels.size();i++){
					String columnLabel=columnLabels.get(i);
					Object value=rs.getObject(columnLabel);
					map.put(columnLabel, value);
				}
				values.add(map);
			}*/
			//2 处理结果集，得到Map的List，一个Map就是一条记录 map的key值是resultSet的列的别名，Map放入值为列的值
			List<Map<String, Object>> values=handleResultSetToMapList(resultSet);
			//3 将map的List对象转化为clazz对应的List对象
			// 其中map的key对应clazz对象的propertyName
			// map的values对应clazz的对应的propertyValues
			list=transferMapListToBeanList(clazz,values);
		} catch ( Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			DBtools.releaseDB(resultSet, preparedStatement, connection);
		}
		return list;
	}
	
	
	/**
	 * 
	 * @param clazz
	 * @param values
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private <T> List<T> transferMapListToBeanList(Class<T> clazz,List<Map<String, Object>> values)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		List<T> result=new ArrayList<>();	
		T bean=null;
		 if(values.size()>0)
			//判断List是否为空集合，若不为空
			//遍历List，得到一个一个的Map对象，在把Map对象转化为class参数对应的Object对象
			for(Map<String,Object>m:values){
				bean=clazz.newInstance();
				for(Map.Entry<String, Object> entry:m.entrySet()){
					//String filedName=entry.getKey();
					//Object filedValue=entry.getValue();
					//ReflectionUtils.setFieldValue(entity,filedName,filedValue)
					String propertyName=entry.getKey();
					Object value=entry.getValue();
					BeanUtils.setProperty(bean,propertyName,value);
					}
				result.add(bean);
			}
		 return result;
	}
	/**
	 * @param resultSet
	 * @param values
	 * @return 
	 * @throws SQLException
	 */
	private List<Map<String, Object>> handleResultSetToMapList(ResultSet resultSet) throws SQLException {
		List<Map<String,Object>> values =new ArrayList<>();
		List<String> columnLabels=getColumnLabel(resultSet);
		Map<String,Object> map=null;
		 //7 处理结果 用while循环
			while(resultSet.next()){
				map=new HashMap<>();
				for(String columnLabel:columnLabels ){
					Object value=resultSet.getObject(columnLabel);
					map.put(columnLabel, value);
				}
				//把一条记录的Map对象放入到准备放入values列表中
				values.add(map);
			}
			return values;
	}
	
	/**
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	private List<String> getColumnLabel(ResultSet rs) throws SQLException{
		List<String> labels=new ArrayList<>();
		ResultSetMetaData rsmd=rs.getMetaData();
		for(int i=0;i<rsmd.getColumnCount();i++){
			labels.add(rsmd.getColumnLabel(i+1));
		}		
		return labels;
		
	}
	
}
