package util;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class DataBean {
	private static DruidDataSource dataSource = new DruidDataSource();
	private static DruidDataSource dataSource2 = new DruidDataSource();
	static{
		try {
			dataSource.setUsername(DataBeanConfig.username);
			dataSource.setPassword(DataBeanConfig.password);
			dataSource.setUrl(DataBeanConfig.databaseUrl);
			/*为让连接池知道数据库已经断开了*/
			dataSource.setValidationQuery("select 1 from dual");
			dataSource.setTestOnBorrow(false);
			dataSource.setTestOnReturn(false);
			dataSource.setTestWhileIdle(true);
			dataSource.setTimeBetweenConnectErrorMillis(60000);
			dataSource.setDriverClassName(DataBeanConfig.databaseDriver);
			dataSource.setInitialSize(5);
			dataSource.setMinIdle(1);
			dataSource.setMaxActive(10);
			dataSource.setFilters("stat");// for mysql
			dataSource.setPoolPreparedStatements(false);
			/*连接从连接池借出后，长时间不归还，将触发强制回连接,回收周期随timeBetweenEvictionRunsMillis进行，如果连接为从连接池借出状态，并且未执行任何sql，并且从借出时间起已超过removeAbandonedTimeout时间，则强制归还连接到连接池中。*/
			dataSource.setRemoveAbandoned(true);
			//指定时间内回收连接:30分钟
			dataSource.setRemoveAbandonedTimeout(1800);
			//关闭abanded连接时输出错误日志
			dataSource.setLogAbandoned(false);
			//打开PSCache，并且指定每个连接上PSCache的大小，Oracle等支持游标的数据库，打开此开关，会以数量级提升性能
			dataSource.setPoolPreparedStatements(true);
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);


			dataSource2.setUsername(DataBeanConfig.username2);
			dataSource2.setPassword(DataBeanConfig.password2);
			dataSource2.setUrl(DataBeanConfig.databaseUrl);
			dataSource2.setValidationQuery("select 1 from dual");
			dataSource2.setTestOnBorrow(false);
			dataSource2.setTestOnReturn(false);
			dataSource2.setTestWhileIdle(true);
			dataSource2.setTimeBetweenConnectErrorMillis(60000);
			dataSource2.setDriverClassName(DataBeanConfig.databaseDriver);
			dataSource2.setInitialSize(5);
			dataSource2.setMinIdle(1);
			dataSource2.setMaxActive(10);
			dataSource2.setFilters("stat");// for mysql
			dataSource2.setPoolPreparedStatements(false);

			dataSource2.setRemoveAbandoned(true);

			dataSource2.setRemoveAbandonedTimeout(1800);

			dataSource2.setLogAbandoned(false);

			dataSource2.setPoolPreparedStatements(true);
			dataSource2.setMaxPoolPreparedStatementPerConnectionSize(20);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean reFreshDataSourceConfig(){
		try {
			dataSource.close();
			dataSource = null;
			dataSource = new DruidDataSource();
			dataSource.setUsername(DataBeanConfig.username);
			dataSource.setPassword(DataBeanConfig.password);
			dataSource.setUrl(DataBeanConfig.databaseUrl);
			dataSource.setValidationQuery("select 1 from dual");
			dataSource.setTestOnBorrow(false);
			dataSource.setTestOnReturn(false);
			dataSource.setTestWhileIdle(true);
			dataSource.setTimeBetweenConnectErrorMillis(60000);
			dataSource.setDriverClassName(DataBeanConfig.databaseDriver);
			dataSource.setInitialSize(1);
			dataSource.setMinIdle(1);
			dataSource.setMaxActive(10);
			dataSource.setFilters("stat");// for mysql
			dataSource.setPoolPreparedStatements(false);
			/*连接从连接池借出后，长时间不归还，将触发强制回连接,回收周期随timeBetweenEvictionRunsMillis进行，如果连接为从连接池借出状态，并且未执行任何sql，并且从借出时间起已超过removeAbandonedTimeout时间，则强制归还连接到连接池中。*/
			dataSource.setRemoveAbandoned(true);
			//指定时间内回收连接:30分钟
			dataSource.setRemoveAbandonedTimeout(1800);
			//关闭abanded连接时输出错误日志
			dataSource.setLogAbandoned(false);
			//打开PSCache，并且指定每个连接上PSCache的大小，Oracle等支持游标的数据库，打开此开关，会以数量级提升性能
			dataSource.setPoolPreparedStatements(true);
			dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
			System.out.println("*********************刷新连接池*********************"+new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}

	public Connection getConnection2() throws SQLException{
		return dataSource2.getConnection();
	}

	public void closeResource(Connection conn,PreparedStatement ps,ResultSet rs){
		try {
			if(rs!=null){ if(!rs.isClosed()) rs.close();}
			if(ps!=null){ if(!ps.isClosed()) ps.close();}
			if(conn!=null){ if(!conn.isClosed()) conn.close();}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void connRollBack(Connection conn){
		try {
			if(conn!=null){
				if(!conn.isClosed())
					conn.rollback();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
