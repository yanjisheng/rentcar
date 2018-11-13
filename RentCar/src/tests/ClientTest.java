package tests;

import implement.ClientImplement;
import implement.ServerImplement;
import interfaces.ClientInterface;
import interfaces.ServerInterface;

import java.io.FileInputStream;
import java.io.ObjectInputStream.GetField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import objects.Car;
import objects.Customer;
import objects.Rent;

/**
 * 客户端，可进行租车、还车等操作
 * @author yanjisheng
 *
 */
public class ClientTest {
		
	public static void main(String[] args) {
		try {
			ClientInterface ci = new ClientImplement();
			ci.init();
			Customer customer = null;
			Scanner sc = new Scanner(System.in);
			int choose=0;
			boolean flag = true;
			System.out.println("欢迎您前来租车！");
			System.out.print("1.新用户注册；2.老用户登录\n请选择：");
			if(sc.hasNextInt()){
				choose = sc.nextInt();
			}
			switch(choose){
			case 1:
				System.out.print("请输入姓名：");
				String name = sc.next();
				System.out.print("请输入地址：");
				String address = sc.next();
				try{
					customer = ci.register(name, address);
				}catch(Exception e){
					System.err.println("注册失败");
					throw e;
				}
				System.out.println("注册成功");
				break;
			case 2:
				System.out.print("请输入姓名：");
				name = sc.next();
				try{
					customer = ci.login(name);
				}catch(Exception e){
					System.err.println("登录失败");
					throw e;
				}
				System.out.println("登录成功");
				break;
			default:
				System.err.println("程序错误！");
				throw new Exception();
			}
			ci.setCustomer(customer);
			while(flag){
				sc = new Scanner(System.in);
				System.out.print("1.租车；2.支付租金；3.还车；4.修改姓名；5.修改地址；0.退出\n请选择：");
				choose = 0;
				if(sc.hasNextInt()){
					choose = sc.nextInt();
				}
				switch(choose){
				case 1:
					try {
						ci.showRentableCars();
						System.out.print("请输入要租的车辆编号：");
						int car_id = sc.nextInt();
						System.out.print("请输入租车天数：");
						int days = sc.nextInt();						
						Car car = ci.getCarById(car_id);						
						Rent rent = ci.rentCar(car, days);
						if(rent==null){
							System.out.println("此车已被租走，无法租车");
							break;
						}
						Double price = rent.getRentPrice();
						System.out.println("租金"+price+"元");
						System.out.print("请选择支付方式：1.网上支付；2.到店支付");
						sc = new Scanner(System.in);
						int c = sc.nextInt();
						switch(c){
						case 1:
							System.out.println("请支付");
							rent.setRentStatus(Rent.PAID);
							System.out.println("支付成功");
							break;
						default:
							System.out.println("请到店后支付租金");
							break;
						}
						System.out.println("租车成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					try{
						ArrayList<Rent> orders = ci.showMyOrders();
						double totalPrice = 0;
						if(orders.size()>0){
							System.out.println("请输入要支付的订单编号，0表示全部支付");
							sc = new Scanner(System.in);
							int c = sc.nextInt();
							if(c==0){								
								for(int i=0;i<orders.size();i++){
									Rent order = orders.get(i);
									totalPrice += order.getRentPrice();
								}
								System.out.println("请支付总租金"+totalPrice+"元");
								System.out.println("支付成功");
							}else if(c>0){
								ServerInterface si = new ServerImplement();
								Rent order = si.getRentById(c);
								totalPrice = order.getRentPrice();
								System.out.println("请支付总租金"+totalPrice+"元");
								System.out.println("支付成功");
							}
						}else{
							System.out.println("您没有未支付的订单");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						ci.showRentedCars();
						System.out.print("请输入要还的车辆编号：");
						int car_id = sc.nextInt();
						Car car = ci.getCarById(car_id);
						ci.returnCar(car);
						System.out.println("还车成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					try{
						System.out.print("请输入新姓名：");
						String newName = sc.next();
						ci.changeName(newName);
						System.out.println("改名成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					try{
						System.out.println("您的当前地址为："+((Customer) ci).getCustomerAddress());
						System.out.print("请输入新地址：");
						String newAdd = sc.next();
						ci.changeAddress(newAdd);
						System.out.println("修改地址成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					flag = false;
					System.out.println("谢谢使用！");
				}
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
