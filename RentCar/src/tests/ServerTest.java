package tests;

import implement.ServerImplement;
import interfaces.ServerInterface;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import objects.Car;
import objects.Customer;
import objects.Rent;

/**
 * 管理员端，可进行添加、删除等操作
 * @author yanjisheng
 *
 */
public class ServerTest {
	
	public static void main(String[] args) {
		try{
			ServerInterface si = new ServerImplement();
			si.init();
			System.out.println("欢迎使用租车管理系统！");
			int choose;
			boolean flag = true;
			while(flag){
				Scanner sc = new Scanner(System.in);
				System.out.print("1.显示车辆信息；2.显示顾客信息；3.添加车辆；4.删除车辆；5.添加顾客；6.删除顾客；\n"
						+ "7.删除已还车的订单记录；8.查询所有订单记录；9.顾客现场租车；10.顾客取车；11.顾客还车；\n"
						+ "12.修改车辆租金；0.退出\n请选择：");
				choose = 0;
				if(sc.hasNextInt()){
					choose = sc.nextInt();
				}
				switch(choose){
				case 1:
					try {
						si.showCars();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 2:
					try {
						si.showCustomers();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						sc = new Scanner(System.in);
						System.out.print("请输入车辆型号：");
						String car_detail = sc.next();
						System.out.print("请输入车辆租金：");
						double car_rent = sc.nextDouble();
						Car c = si.addCar(car_detail, car_rent);
						if(c==null){
							System.out.println("车辆信息重复，添加失败");
							break;
						}
						System.out.println("添加车辆"+car_detail+"成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					try {
						sc = new Scanner(System.in);
						System.out.print("请输入车辆编号：");
						int car_id = sc.nextInt();
						Car car = si.getCarById(car_id);
						si.removeCar(car);
						System.out.println("删除车辆"+car.getCarDetail()+"成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					try {
						sc = new Scanner(System.in);
						System.out.print("请输入顾客姓名：");
						String customer_name = sc.next();
						System.out.print("请输入顾客地址：");
						String customer_address = sc.next();
						Customer c = si.addCustomer(customer_name, customer_address);
						if(c==null){
							System.out.println("顾客姓名重复，添加失败");
							break;
						}
						System.out.println("添加顾客"+customer_name+"成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 6:
					try {
						sc = new Scanner(System.in);
						System.out.print("请输入顾客编号：");
						int customer_id = sc.nextInt();
						Customer customer = si.getCustomerById(customer_id);
						si.removeCustomer(customer);
						System.out.println("删除顾客"+customer.getCustomerName()+"成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 7:
					try {
						si.removeRent();
						System.out.println("删除历史订单成功");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 8:
					try {
						si.showRent();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 9:
					try{
						si.showCars();
						System.out.print("请输入租车的顾客姓名：");
						sc = new Scanner(System.in);
						String customer = sc.next();
						System.out.print("请输入车辆编号：");
						int car = sc.nextInt();
						System.out.print("请输入租车天数：");
						int days = sc.nextInt();
						Rent rent = si.addRent(si.getCarById(car), si.getCustomerByName(customer), days, Rent.RENTED);
						if(rent == null){
							System.out.println("车辆已被其他顾客租走，无法租车");
							break;
						}
						double price = rent.getRentPrice();
						System.out.println("请支付租金"+price+"元");
						System.out.println("租车成功！");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				case 10:
					try{
						si.showRent();
						System.out.println("请输入取车订单编号：");
						sc = new Scanner(System.in);
						int rentId = sc.nextInt();
						Rent rent = si.getRentById(rentId);
						if(rent.getRentStatus()==Rent.NOT_PAID){
							double price = rent.getRentPrice();
							System.out.println("请支付租金"+price+"元");
							si.changeRentStatus(rent, Rent.RENTED);
						}else if(rent.getRentStatus()==Rent.PAID){
							si.changeRentStatus(rent, Rent.RENTED);
						}else{
							throw new Exception("订单状态错误，无法取车");
						}
						System.out.println("取车成功！");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				case 11:
					try{
						boolean b = si.showRentedOrders();
						if(!b){
							break;
						}
						System.out.println("请输入还车订单编号：");
						sc = new Scanner(System.in);
						int rentId = sc.nextInt();
						Rent rent = si.getRentById(rentId);
						if(rent.getRentStatus()==Rent.RENTED){
							si.changeRentStatus(rent, Rent.RETURNED);
						}else if(rent.getRentStatus()==Rent.PAID){
							System.out.println("退还租金"+rent.getRentPrice()+"元");
							si.changeRentStatus(rent, Rent.RETURNED);
						}else if(rent.getRentStatus()==Rent.NOT_PAID){
							System.out.println("取消订单");
							si.changeRentStatus(rent, Rent.RETURNED);
						}else{
							throw new Exception("订单状态错误，无法还车");
						}
						System.out.println("还车成功！");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				case 12:
					try{
						System.out.println("请输入要修改租金的车辆编号：");
						sc = new Scanner(System.in);
						int carId = sc.nextInt();
						Car car = si.getCarById(carId);
						System.out.println("车辆原租金为"+car.getCarCost()+"元");
						System.out.println("请输入修改后的车辆租金：");
						double rent = sc.nextDouble();
						si.changeCarRent(car, rent);
						System.out.println("修改租金成功");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				default:
					flag = false;
					System.out.println("谢谢使用！");
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
