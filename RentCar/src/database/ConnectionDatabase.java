package database;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * ������ر����ݿ���
 * 
 * @author yanjisheng
 * 
 */
public class ConnectionDatabase {

	private static Connection con = null;

	// private static int countCon = 0;

	/**
	 * �������ӷ���
	 * 
	 * @return Connection
	 * @throws Exception
	 */
	private Connection startConnection() throws Exception {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/rentcar?characterEncoding=utf8&autoReconnect=true";
		String username = "yanjisheng";
		String password = "abc123";
		Properties properties = new Properties();
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		//String path = "";
		while (path.indexOf("%20") >= 0) {
			path = path.replaceAll("%20", " ");
		}
		File file = new File(path, "rentcar.properties");
		System.out.println("�ļ�����·��Ϊ��" + file.getAbsolutePath());
		if(file.exists()){
		//if (false) {
			properties.load(new FileInputStream(file));
			System.out.println("�ļ�rentcat.properties�Ѵ���");
			driver = properties.getProperty("driver");
			url = properties.getProperty("url");
			username = properties.getProperty("username");
			password = properties.getProperty("password");
		} else {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write("driver=" + driver);
			writer.newLine();
			writer.write("url=" + url);
			writer.newLine();
			writer.write("username=" + username);
			writer.newLine();
			writer.write("password=" + password);
			writer.close();
			System.out.println("�Ѵ����ļ�rentcat.properties");
		}
		Class.forName(driver);
		con = DriverManager.getConnection(url, username, password);
		// countCon++;
		// SimpleDateFormat format = new
		// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date date = new Date();
		// System.out.print(format.format(date));
		// System.out.println(" ����������"+con);
		// System.out.println("�ѽ�����������"+countCon);
		// st = con.createStatement();
		return con;
	}

	/**
	 * �ر����ӷ���
	 * 
	 * @throws Exception
	 */
	public void closeConnection() throws Exception {
		con.close();
	}

	/**
	 * ��ȡ���ӷ���
	 * 
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		if (con == null || con.isClosed()) {
			con = startConnection();
		}
		return con;
	}

	/**
	 * �������ӷ������ɲ����������ݿ��Ƿ�ɹ�
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ConnectionDatabase c = new ConnectionDatabase();
		try {
			c.startConnection();
			System.out.println("���ӳɹ���");
			c.closeConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
