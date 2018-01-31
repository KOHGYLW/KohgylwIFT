package kohgylw.listener;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 该监听器用于在服务器启动时自动检查数据库并对相应的表进行初始化
 * <p>将内置数据库H2进行初始化并创建出必备的表（如果没有的话），默认存储位置为用户文件夹下的 文档/kohgylwift/ </p>
 * 
 */
public class H2DBinitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO 自动生成的方法存根
		try {
			Class.forName("org.h2.Driver");
			// 内嵌数据库无需启动服务器，只需要开启链接即可使用，默认在程序根目录下创建
			Connection conn = DriverManager.getConnection("jdbc:h2:file:~/Documents/KohgylwIFT/filenodes/kift", "root", "301537gY");
			Statement state1 = conn.createStatement();// 获取执行声明
			// 判定是否存在表stata
			ResultSet result = state1
					.executeQuery("SELECT count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME='FOLDER'");
			while (result.next()) {
				if (result.getInt("count(*)") > 0) {

				} else {
					Statement state2=conn.createStatement();
					//初始化表结构
					state2.execute("CREATE TABLE FOLDER(folder_id VARCHAR(128) PRIMARY KEY,"
							+ "  folder_name VARCHAR(128) NOT NULL,folder_creation_date VARCHAR(128) NOT NULL,"
							+ "  folder_creator VARCHAR(128) NOT NULL,folder_parent VARCHAR(128) NOT NULL)");// 执行键表语句
					//载入根节点
					state2.execute("INSERT INTO FOLDER VALUES('root', 'ROOT', '--', '--', 'null')");
					state2.close();
				}
			}
			result = state1.executeQuery("SELECT count(*) from INFORMATION_SCHEMA.TABLES where TABLE_NAME='FILE'");
			while (result.next()) {
				if (result.getInt("count(*)") > 0) {

				} else {
					Statement state3=conn.createStatement();
					state3.execute("CREATE TABLE FILE(file_id VARCHAR(128) PRIMARY KEY,"
							+ "file_name VARCHAR(128) NOT NULL,file_size VARCHAR(128) NOT NULL,"
							+ "file_parent_folder varchar(128) NOT NULL,file_creation_date varchar(128) NOT NULL,"
							+ "file_creator varchar(128) NOT NULL,file_path varchar(128) NOT NULL)");// 执行键表语句
					state3.close();
					//清空垃圾文件块
					String fileblocks=sce.getServletContext().getRealPath("/fileblocks");
					File fb=new File(fileblocks);
					if(fb.exists()&&fb.isDirectory()) {
						String[] flist=fb.list();
						for(String s:flist) {
							new File(fb,s).delete();
						}
					}else {
						fb.mkdirs();
					}
				}
			}
			state1.close();
			conn.close();
			System.out.println("KohgylwIFT:[INIT]SUCCESS:h2db data storage location init complete");
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			System.out.println("KohgylwIFT:[INIT]ERROR:cannot init h2db with path '{USER_HOME}/Documents/kohgylwift/'");
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO 自动生成的方法存根

	}

}
