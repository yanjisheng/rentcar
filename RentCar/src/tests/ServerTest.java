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
 * ����Ա�ˣ��ɽ�����ӡ�ɾ���Ȳ���
 * @author yanjisheng
 *
 */
public class ServerTest {
	
	public static void main(String[] args) {
		try{
			ServerInterface si = new ServerImplement();
			si.init();
			System.out.println("��ӭʹ���⳵����ϵͳ��");
			int choose;
			boolean flag = true;
			while(flag){
				Scanner sc = new Scanner(System.in);
				System.out.print("1.��ʾ������Ϣ��2.��ʾ�˿���Ϣ��3.��ӳ�����4.ɾ��������5.��ӹ˿ͣ�6.ɾ���˿ͣ�\n"
						+ "7.ɾ���ѻ����Ķ�����¼��8.��ѯ���ж�����¼��9.�˿��ֳ��⳵��10.�˿�ȡ����11.�˿ͻ�����\n"
						+ "12.�޸ĳ������0.�˳�\n��ѡ��");
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
						System.out.print("�����복���ͺţ�");
						String car_detail = sc.next();
						System.out.print("�����복�����");
						double car_rent = sc.nextDouble();
						Car c = si.addCar(car_detail, car_rent);
						if(c==null){
							System.out.println("������Ϣ�ظ������ʧ��");
							break;
						}
						System.out.println("��ӳ���"+car_detail+"�ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					try {
						sc = new Scanner(System.in);
						System.out.print("�����복����ţ�");
						int car_id = sc.nextInt();
						Car car = si.getCarById(car_id);
						si.removeCar(car);
						System.out.println("ɾ������"+car.getCarDetail()+"�ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					try {
						sc = new Scanner(System.in);
						System.out.print("������˿�������");
						String customer_name = sc.next();
						System.out.print("������˿͵�ַ��");
						String customer_address = sc.next();
						Customer c = si.addCustomer(customer_name, customer_address);
						if(c==null){
							System.out.println("�˿������ظ������ʧ��");
							break;
						}
						System.out.println("��ӹ˿�"+customer_name+"�ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 6:
					try {
						sc = new Scanner(System.in);
						System.out.print("������˿ͱ�ţ�");
						int customer_id = sc.nextInt();
						Customer customer = si.getCustomerById(customer_id);
						si.removeCustomer(customer);
						System.out.println("ɾ���˿�"+customer.getCustomerName()+"�ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 7:
					try {
						si.removeRent();
						System.out.println("ɾ����ʷ�����ɹ�");
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
						System.out.print("�������⳵�Ĺ˿�������");
						sc = new Scanner(System.in);
						String customer = sc.next();
						System.out.print("�����복����ţ�");
						int car = sc.nextInt();
						System.out.print("�������⳵������");
						int days = sc.nextInt();
						Rent rent = si.addRent(si.getCarById(car), si.getCustomerByName(customer), days, Rent.RENTED);
						if(rent == null){
							System.out.println("�����ѱ������˿����ߣ��޷��⳵");
							break;
						}
						double price = rent.getRentPrice();
						System.out.println("��֧�����"+price+"Ԫ");
						System.out.println("�⳵�ɹ���");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				case 10:
					try{
						si.showRent();
						System.out.println("������ȡ��������ţ�");
						sc = new Scanner(System.in);
						int rentId = sc.nextInt();
						Rent rent = si.getRentById(rentId);
						if(rent.getRentStatus()==Rent.NOT_PAID){
							double price = rent.getRentPrice();
							System.out.println("��֧�����"+price+"Ԫ");
							si.changeRentStatus(rent, Rent.RENTED);
						}else if(rent.getRentStatus()==Rent.PAID){
							si.changeRentStatus(rent, Rent.RENTED);
						}else{
							throw new Exception("����״̬�����޷�ȡ��");
						}
						System.out.println("ȡ���ɹ���");
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
						System.out.println("�����뻹��������ţ�");
						sc = new Scanner(System.in);
						int rentId = sc.nextInt();
						Rent rent = si.getRentById(rentId);
						if(rent.getRentStatus()==Rent.RENTED){
							si.changeRentStatus(rent, Rent.RETURNED);
						}else if(rent.getRentStatus()==Rent.PAID){
							System.out.println("�˻����"+rent.getRentPrice()+"Ԫ");
							si.changeRentStatus(rent, Rent.RETURNED);
						}else if(rent.getRentStatus()==Rent.NOT_PAID){
							System.out.println("ȡ������");
							si.changeRentStatus(rent, Rent.RETURNED);
						}else{
							throw new Exception("����״̬�����޷�����");
						}
						System.out.println("�����ɹ���");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				case 12:
					try{
						System.out.println("������Ҫ�޸����ĳ�����ţ�");
						sc = new Scanner(System.in);
						int carId = sc.nextInt();
						Car car = si.getCarById(carId);
						System.out.println("����ԭ���Ϊ"+car.getCarCost()+"Ԫ");
						System.out.println("�������޸ĺ�ĳ������");
						double rent = sc.nextDouble();
						si.changeCarRent(car, rent);
						System.out.println("�޸����ɹ�");
					} catch(Exception e){
						e.printStackTrace();
					}
					break;
				default:
					flag = false;
					System.out.println("ллʹ�ã�");
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
