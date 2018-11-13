package implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.ConnectionDatabase;
import objects.Car;
import objects.Customer;
import objects.Rent;
import interfaces.ServerInterface;

/**
 * ����Ա��ʵ����
 * @author yanjisheng
 *
 */
public class ServerImplement implements ServerInterface {

	private static Connection con = null;
	private Statement st = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	public void init() throws Exception{
		ConnectionDatabase cd = new ConnectionDatabase();
		con = cd.getConnection();
	}
	
	public void setCon(Connection con) throws Exception{
		ServerImplement.con = con;
	}
	
	@Override
	public ArrayList<Car> showCars() throws Exception {
		// TODO Auto-generated method stub
		ArrayList<Car> cars = new ArrayList<Car>();
		st = con.createStatement();
		rs = st.executeQuery("SELECT * FROM cars ORDER BY car_id");
		if(!rs.next()){
			System.out.println("����Ϊ��");
			return null;
		}
		System.out.println("������Ϣ��");
		System.out.println("�������\t�����ͺ�\tÿ�����\t����״̬");
		do{
			int carId = rs.getInt("CAR_ID");
			String carDetail = rs.getString("car_detail");
			double carCost = rs.getDouble("car_cost");
			byte rentStatus = 0;
			String statusString;
			Car car = new Car(carId, carDetail, carCost);
			rentStatus = car.getCarStatus();
			switch(rentStatus){
			case 1:
				statusString = "δ֧�������⳵";
				break;
			case 2:
				statusString = "��֧���������⳵";
				break;
			case 3:
				statusString = "�ѱ����ߣ������⳵";
				break;
			case 4:
				statusString = "���ã����⳵";
				break;
			default:
				throw new Exception("����״̬����");
			}
			System.out.println(carId+"\t"+carDetail+"\t"+carCost+"\t"+statusString);			
			cars.add(car);
		}while(rs.next());
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return cars;
	}
	
	public ArrayList<Car> showRentableCars() throws Exception{
		ArrayList<Car> cars = new ArrayList<Car>();
		st = con.createStatement();
		rs = st.executeQuery("SELECT * FROM cars WHERE car_id NOT IN"
				+ "(SELECT rent_car FROM rent WHERE rent_status = 2 OR rent_status = 3) ORDER BY car_id");
		if(!rs.next()){
			System.out.println("���⳵��Ϊ��");
			return null;
		}
		System.out.println("���⳵����Ϣ��");
		System.out.println("�������\t�����ͺ�\tÿ�����");
		do{
			int carId = rs.getInt("car_id");
			String carDetail = rs.getString("car_detail");
			double carCost = rs.getDouble("car_cost");
			System.out.println(carId+"\t"+carDetail+"\t"+carCost);
			Car car = new Car(carId, carDetail, carCost);
			cars.add(car);
		}while(rs.next());
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return cars;
	}

	@Override
	public ArrayList<Customer> showCustomers() throws Exception {
		// TODO Auto-generated method stub
		st = con.createStatement();
		rs = st.executeQuery("SELECT * FROM customers ORDER BY customer_id");
		ArrayList<Customer> customers = new ArrayList<Customer>();
		if(!rs.next()){
			System.out.println("�˿���ϢΪ��");
		}
		System.out.println("�˿���Ϣ��");
		System.out.println("�˿ͱ��\t�˿�����\t�˿͵�ַ");
		do{
			int customerId = rs.getInt("customer_id");
			String customerName = rs.getString("customer_name");
			String customerAddress = rs.getString("customer_address");
			System.out.println(customerId+"\t"+customerName+"\t"+customerAddress);
			Customer customer = new Customer(customerId, customerName, customerAddress);
			customers.add(customer);
		}while(rs.next());
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return customers;
	}

	@Override
	public Car addCar(String carDetail, double carRent) throws Exception {
		// TODO Auto-generated method stub
		if(carDetail!=null&&carRent>0){
			pst = con.prepareStatement("insert into cars(car_detail, car_cost) values(?, ?)");
			pst.setString(1, carDetail);
			pst.setDouble(2, carRent);
			pst.executeUpdate();
			pst = con.prepareStatement("select car_id from cars where car_detail = '"+carDetail+"'");		
			rs = pst.executeQuery();
			rs.next();
			int carId = rs.getInt("car_id");
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return new Car(carId, carDetail, carRent);
		}else{
			return null;
		}
	}

	@Override
	public boolean removeCar(Car car) throws Exception {
		// TODO Auto-generated method stub
		int car_id = car.getCarId();
		removeCar(car_id);
		return true;
	}
	
	public boolean removeCar(int carId) throws Exception{
		pst = con.prepareStatement("delete from rent where rent_car = ? and rent_status = 4");
		pst.setInt(1, carId);
		pst.execute();//�˴���ɾ��rent���е���ؼ�¼����������Լ�������޷�ɾ��cars���еĳ���
		pst = con.prepareStatement("delete from cars where car_id = ?");
		pst.setInt(1, carId);
		pst.execute();
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return false;		
	}
	
	public Customer getCustomerByName(String name) throws Exception{
		pst = con.prepareStatement("select * from customers where customer_name = ?");
		pst.setString(1, name);		
		rs = pst.executeQuery();
		if(rs.next()){
			int customer_id = rs.getInt("customer_id");
			String customer_name = rs.getString("customer_name");
			String customer_address = rs.getString("customer_address");
			return new Customer(customer_id, customer_name, customer_address);
		}
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return null;
	}
	
	@Override
	public Customer addCustomer(String customer_name, String customer_address) throws Exception {
		// TODO Auto-generated method stub
		if(customer_name!=null&&customer_address!=null){
			pst = con.prepareStatement("insert into customers(customer_name, customer_address) values(?, ?)");
			pst.setString(1, customer_name);
			pst.setString(2, customer_address);
			pst.executeUpdate();
			pst = con.prepareStatement("select customer_id from customers where customer_name = ? and customer_address = ?");
			pst.setString(1, customer_name);
			pst.setString(2, customer_address);
			rs = pst.executeQuery();
			rs.next();
			int customerId = rs.getInt("customer_id");
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return new Customer(customerId, customer_name, customer_address);
		}else{
			return null;
		}
	}

	@Override
	public boolean removeCustomer(Customer customer) throws Exception {
		// TODO Auto-generated method stub
		int customer_id = customer.getCustomerId();
		removeCustomer(customer_id);
		return true;
	}
	
	public boolean removeCustomer(int customer_id) throws Exception{
		pst = con.prepareStatement("delete from rent where rent_customer = ? and rent_status =4");
		pst.setInt(1, customer_id);
		pst.execute();
		pst = con.prepareStatement("delete from customers where customer_id = ?");
		pst.setInt(1, customer_id);
		pst.execute();
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return true;
	}

	@Override
	public String showRent() throws Exception {
		// TODO Auto-generated method stub
		st = con.createStatement();
		rs = st.executeQuery("select * from view_rent order by rent_id");	
		StringBuffer temp = new StringBuffer();
		if(!rs.next()){
			System.out.println("��ʷ����Ϊ��");
			temp.append("<tr><td colspan='8'>��ʷ����Ϊ��</td></tr>");
		}else{		
			System.out.println("��ʷ������");
			System.out.println("�������\t������Ϣ\t�˿�����\t��������\t�⳵����\t����״̬");
			do{
				Date date = rs.getDate("rent_date");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				int status = rs.getInt("rent_status");
				String statusString = null;
				switch(status){
				case 1:
					statusString = "δ֧��";
					break;
				case 2:
					statusString = "δȡ��";
					break;
				case 3:
					statusString = "��ȡ��";
					break;
				case 4:
					statusString = "�ѻ���";
					break;
				default:
					throw new Exception("����״̬����");
				}
				ServerImplement si = new ServerImplement();
				si.init();
				System.out.println(rs.getInt("rent_id")+"\t"+
						rs.getString("car_detail")+"\t"+
						rs.getString("customer_name")+"\t"+
						sdf.format(date)+"\t"+
						rs.getInt("rent_days")+"\t"+
						statusString);
				temp.append("<tr><td>"+rs.getInt("rent_id")+"</td><td>"+
						rs.getString("car_detail")+"</td><td>"+
						rs.getString("customer_name")+"</td><td>"+
						sdf.format(date)+"</td><td>"+
						rs.getInt("rent_days")+"</td><td>"+
						si.getRentById(rs.getInt("rent_id")).getRentPrice()+"</td><td>"+
						statusString+"</td>");
				switch(status){
				case 1:
					temp.append("<td><a href='takeCar.jsp?rentStatus=3&rentId="+
							rs.getInt("rent_id")+"'>֧����ȡ��</a> <a href='takeCar.jsp?rentStatus=4&rentId="+
							rs.getInt("rent_id")+"'>ȡ������</a></td></tr>");
					break;
				case 2:
					temp.append("<td><a href='takeCar.jsp?rentStatus=3&rentId="+
							rs.getInt("rent_id")+"'>ȡ��</a> <a href='takeCar.jsp?rentStatus=4&rentId="+
							rs.getInt("rent_id")+"'>ȡ������</a></td></tr>");
					break;
				case 3:
					temp.append("<td><a href='takeCar.jsp?rentStatus=4&rentId="+
							rs.getInt("rent_id")+"'>����</a></td></tr>");
					break;
				case 4:
					temp.append("</tr>");
					break;	
				}
			}while(rs.next());
		}
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return temp.toString();
	}
	
	@Override
	public boolean showRentedOrders() throws Exception {
		// TODO Auto-generated method stub
		st = con.createStatement();
		rs = st.executeQuery("select * from view_rent where rent_status !=4 order by rent_id");
		if(!rs.next()){
			System.out.println("��������ʷ����Ϊ��");
			return false;
		}
		System.out.println("��������ʷ������");
		System.out.println("�������\t������Ϣ\t�˿�����\t��������\t�⳵����\t����״̬");
		do{
			Date date = rs.getDate("rent_date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int status = rs.getInt("rent_status");
			String statusString = null;
			switch(status){
			case 1:
				statusString = "δ֧��";
				break;
			case 2:
				statusString = "δȡ��";
				break;
			case 3:
				statusString = "��ȡ��";
				break;
			case 4:
				statusString = "�ѻ���";
				break;
			default:
				throw new Exception("����״̬����");
			}
			System.out.println(rs.getInt("rent_id")+"\t"+
					rs.getString("car_detail")+"\t"+
					rs.getString("customer_name")+"\t"+
					sdf.format(date)+"\t"+
					rs.getInt("rent_days")+"\t"+
					statusString);
		}while(rs.next());
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return true;
	}

	@Override
	public boolean removeRent() throws Exception {
		// TODO Auto-generated method stub
		st = con.createStatement();
		rs = st.executeQuery("select * from rent where rent_status = 4");
		boolean result = false;
		if(rs.next()){
			st.executeUpdate("delete from rent where rent_status = 4");
			result = true;
		}	
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		//System.out.println("ɾ����ʷ�����ɹ�");
		return result;
	}
	
	@Override
	public Car getCarById(int carId) throws Exception{
		pst = con.prepareStatement("select * from cars where car_id = ?");
		pst.setInt(1, carId);
		rs = pst.executeQuery();
		if(rs.next()){
			String carDetail = rs.getString("car_detail");
			double carRent = rs.getDouble("car_cost");
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return new Car(carId, carDetail, carRent);	
		}else{
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return null;
		}
	}

	public ArrayList<Car> getCarByDetail(String carDetail) throws Exception{
		ArrayList<Car> cars = new ArrayList<Car>();
		rs = st.executeQuery("select * from cars where car_detail like(_"+carDetail+"_)");
		while(rs.next()){
			int carId = rs.getInt("CAR_ID");
			carDetail = rs.getString("CAR_DETAIL");
			double carCost = rs.getDouble("CAR_COST");
			System.out.println(carId+"\t"+carDetail+"\t"+carCost);
			Car car = new Car(carId, carDetail, carCost);
			cars.add(car);
		}
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return cars;		
	}
	
	@Override
	public Customer getCustomerById(int customerId) throws Exception{
		// TODO Auto-generated method stub
		pst = con.prepareStatement("select * from customers where customer_id = ?");
		pst.setInt(1, customerId);
		rs = pst.executeQuery();
		if(rs.next()){
			String cName = rs.getString("customer_name");
			String cAdd = rs.getString("customer_address");
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return new Customer(customerId, cName, cAdd);	
		}else{
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return null;
		}
	}

	public Rent getRentById(int rentId) throws Exception{
		st = con.createStatement();
		rs = st.executeQuery("select * from rent where rent_id = '"+rentId+"'");
		rs.next();
		int rentCar = rs.getInt("rent_car");
		int rentCustomer = rs.getInt("rent_customer");
		Date rentDate = rs.getDate("rent_date");
		int rentDays = rs.getInt("rent_days");
		byte rentStatus = rs.getByte("rent_status");
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return new Rent(rentId, rentCar, rentCustomer, rentDate, rentDays, rentStatus);
	}
	
	public ArrayList<Rent> getRentByCustomer(Customer c) throws Exception{
		ArrayList<Rent> rents = new ArrayList<Rent>();
		pst = con.prepareStatement("select * from rent where rent_customer = ?");
		pst.setInt(1, c.getCustomerId());
		rs = pst.executeQuery();
		while(rs.next()){
			int rentId = rs.getInt("rent_id");
			int rentCar = rs.getInt("rent_car");
			int rentCustomer = rs.getInt("rent_customer");
			Date rentDate = rs.getDate("rent_date");
			int rentDays = rs.getInt("rent_days");
			byte rentStatus = rs.getByte("rent_status");
			Rent rent = new Rent(rentId, rentCar, rentCustomer, rentDate, rentDays, rentStatus);
			rents.add(rent);
		}
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return rents;
	}
	
	public Rent addRent(Car car, Customer customer, int days, byte status) throws Exception{
		if(car.getCarStatus()==Rent.NOT_PAID){
			pst = con.prepareStatement("delete from rent where rent_id = ?");
			int rentId = car.getRent().getRentId();
			pst.setInt(1, rentId);
			pst.executeUpdate();
		}		
		if(car.getCarStatus()==Rent.RETURNED){
			pst = con.prepareStatement("insert into rent(rent_car, rent_customer, rent_date, rent_days, rent_status) values(?, ?, now(), ?, ?)");
			pst.setInt(1, car.getCarId());
			pst.setInt(2, customer.getCustomerId());
			pst.setInt(3, days);
			pst.setByte(4, status);
			pst.executeUpdate();
			st = con.createStatement();
			//rs = st.executeQuery("select rentid.currval from dual");
			rs = st.executeQuery("select last_insert_id()");
			rs.next();
			int rentId = rs.getInt(1);
			rs = st.executeQuery("select rent_id, rent_date from rent where rent_id = "+rentId);
			rs.next();
			Date rentDate = rs.getDate("rent_date");
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return new Rent(rentId, car.getCarId(), customer.getCustomerId(), rentDate, days, status);
		}else{
			if(rs!=null){rs.close();}
			if(st!=null){st.close();}
			if(pst!=null){pst.close();}
			return null;
		}		
	}
	
	public Rent changeRentStatus(Rent rent, byte status) throws Exception{
		pst = con.prepareStatement("update rent set rent_status = ? where rent_id = ?");
		pst.setByte(1, status);
		pst.setInt(2, rent.getRentId());
		pst.executeUpdate();
		//rent.setRentStatus(status);
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return rent;
	}
	
	public Car changeCarRent(Car car, double rent) throws Exception{
		pst = con.prepareStatement("update cars set car_cost = ? where car_id = ?");
		pst.setDouble(1, rent);
		pst.setInt(2, car.getCarId());
		pst.executeUpdate();
		car.setCarCost(rent);
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return car;
	}
	
	public byte getCarStatus(Car car) throws Exception{
		//�Ժ󽨱��Ǻã���Ӧ��״̬���ֶΣ��������鷳������������
		pst = con.prepareStatement("SELECT * FROM view_cars WHERE car_id = ?");
		int carId = car.getCarId();
		pst.setInt(1, carId);
		rs = pst.executeQuery();
		byte status = 4;
		while(rs.next()){
			byte statusTemp = rs.getByte("rent_status");
			if(statusTemp==0){
				break;//״̬Ϊ0��ʾ�ó���δ�����
			}else if(statusTemp==1||statusTemp==2||statusTemp==3){
				if(status==4){
					status=statusTemp;//��һ��δ������״̬
				}else{
					throw new Exception("����״̬��������ͬʱ������û����");
				}
			}else if(statusTemp==4){
				//�ѻ�����״̬����ԭ���ͺ�
			}else{
				throw new Exception("����״̬���������ܵ�״̬");
			}
		}
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return status;
	}
	
	public Rent getRentByCar(Car car) throws Exception{
		pst = con.prepareStatement("select * from rent where rent_car = ? and rent_status <> 4");
		int carId = car.getCarId();
		pst.setInt(1, carId);
		rs = pst.executeQuery();
		int count = 0;
		int rentId = 0;
		while(rs.next()){
			rentId = rs.getInt("rent_id");
			count++;
		}
		if(count>=2){
			throw new Exception("����״̬��������ͬʱ������û����");
		}
		ServerInterface si = new ServerImplement();
		Rent rent = si.getRentById(rentId);
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return rent;
	}
	
}
