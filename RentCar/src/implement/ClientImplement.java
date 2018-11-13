package implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.ConnectionDatabase;
import objects.Car;
import objects.Customer;
import objects.Rent;
import interfaces.ClientInterface;
import interfaces.ServerInterface;

/**
 * 客户端实现类
 * @author yanjisheng
 *
 */
public class ClientImplement extends Customer implements ClientInterface{

	public ClientImplement(int customerId, String customerName,	String customerAddress) throws Exception {
		super(customerId, customerName, customerAddress);
		// TODO Auto-generated constructor stub
	}
	
	public ClientImplement(){
		
	}
	
	public ClientImplement(Customer c) throws Exception{
		super(c.getCustomerId(), c.getCustomerName(), c.getCustomerAddress());
	}
	
	private static Connection con = null;
	private Statement st = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	@Override
	public void init() throws Exception{
		ConnectionDatabase cd = new ConnectionDatabase();
		con = cd.getConnection();
	}
	
	@Override
	public Customer login(String name) throws Exception {
		// TODO Auto-generated method stub
		ServerInterface si = new ServerImplement();
		si.setCon(con);
		Customer c = si.getCustomerByName(name);
		return c;		
	}

	@Override
	public Customer register(String name, String address) throws Exception{
		// TODO Auto-generated method stub		
		ServerInterface si = new ServerImplement();
		si.setCon(con);
		Customer c = si.addCustomer(name, address);
		return c;		
	}

	public void setCustomer(Customer c) throws Exception{
		this.setCustomerId(c.getCustomerId());
		this.setCustomerName(c.getCustomerName());
		this.setCustomerAddress(c.getCustomerAddress());
	}
	
	@Override
	public void showRentableCars() throws Exception{
		// TODO Auto-generated method stub
		ServerInterface si = new ServerImplement();
		si.setCon(con);
		si.showRentableCars();
	}

	@Override
	public Rent rentCar(Car car, int rentDays) throws Exception{
		// TODO Auto-generated method stub
		ServerInterface si = new ServerImplement();
		si.setCon(con);
		Rent rent = si.addRent(car, this, rentDays, Rent.NOT_PAID);
		return rent;
	}

	@Override
	public void showRentedCars() throws Exception{
		// TODO Auto-generated method stub
		pst = con.prepareStatement("SELECT * FROM cars WHERE caa_id IN(SELECT rent_car FROM rent WHERE rent_customer = ? and rent_status = 3) ORDER BY car_id");
		pst.setInt(1, this.getCustomerId());
		rs = pst.executeQuery();
		System.out.println("已租车辆信息：");
		System.out.println("车辆编号\t车辆型号\t每天租金");
		while(rs.next()){
			System.out.println(rs.getInt("car_id")+"\t"
					+rs.getString("car_detail")+"\t"+
					rs.getDouble("car_cost"));
		}
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
	}

	@Override
	public boolean returnCar(Car car) throws Exception{
		// TODO Auto-generated method stub
		ServerInterface si = new ServerImplement();
		si.setCon(con);
		Rent rent = car.getRent();
		si.changeRentStatus(rent, Rent.RETURNED);
		return false;
	}
	
	@Override
	public void changeName(String name) throws Exception{
		pst = con.prepareStatement("update customers set customer_name = ? where customer_id = ?");
		pst.setString(1, name);
		pst.setInt(2, getCustomerId());
		pst.executeUpdate();
		this.setCustomerName(name);
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
	}
	
	@Override
	public void changeAddress(String address) throws Exception{
		pst = con.prepareStatement("update customers set customer_address = ? where customer_id = ?");
		pst.setString(1, address);
		pst.setInt(2, getCustomerId());
		pst.executeUpdate();
		this.setCustomerAddress(address);
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
	}
	
	@Override
	public Car getCarById(int carId) throws Exception{
		ServerInterface si = new ServerImplement();
		si.setCon(con);
		Car car = si.getCarById(carId);
		return car;
	}

	@Override
	public ArrayList<Rent> showMyOrders() throws Exception{
		// TODO Auto-generated method stub
		ArrayList<Rent> rents = new ArrayList<Rent>();
		ServerInterface si = new ServerImplement();
		si.init();
		pst = con.prepareStatement("select * from view_rent where customer_name = ? order by rent_id");
		pst.setString(1, this.getCustomerName());	
		rs = pst.executeQuery();
		if(!rs.next()){
			System.out.println("您没有订单");
			return rents;
		}
		System.out.println("订单：");
		System.out.println("订单编号\t车辆信息\t顾客姓名\t订单日期\t租车天数\t订单状态");
		do{
			Date date = rs.getDate("rent_date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			int status = rs.getInt("rent_status");
			String statusString = null;
			switch(status){
			case 1:
				statusString = "未支付";
				break;
			case 2:
				statusString = "未取车";
				break;
			case 3:
				statusString = "已取车";
				break;
			case 4:
				statusString = "已还车";
				break;
			default:
				throw new Exception("订单状态错误！");
			}
			int rentId = rs.getInt("rent_id");
			Rent rent = si.getRentById(rentId);
			System.out.println(rentId+"\t"+
					rs.getString("car_detail")+"\t"+
					rs.getString("customer_name")+"\t"+
					sdf.format(date)+"\t"+
					rs.getInt("rent_days")+"\t"+
					statusString);
			rents.add(rent);
		}while(rs.next());
		if(rs!=null){rs.close();}
		if(st!=null){st.close();}
		if(pst!=null){pst.close();}
		return rents;
	}

}
