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
 * �ͻ��ˣ��ɽ����⳵�������Ȳ���
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
			System.out.println("��ӭ��ǰ���⳵��");
			System.out.print("1.���û�ע�᣻2.���û���¼\n��ѡ��");
			if(sc.hasNextInt()){
				choose = sc.nextInt();
			}
			switch(choose){
			case 1:
				System.out.print("������������");
				String name = sc.next();
				System.out.print("�������ַ��");
				String address = sc.next();
				try{
					customer = ci.register(name, address);
				}catch(Exception e){
					System.err.println("ע��ʧ��");
					throw e;
				}
				System.out.println("ע��ɹ�");
				break;
			case 2:
				System.out.print("������������");
				name = sc.next();
				try{
					customer = ci.login(name);
				}catch(Exception e){
					System.err.println("��¼ʧ��");
					throw e;
				}
				System.out.println("��¼�ɹ�");
				break;
			default:
				System.err.println("�������");
				throw new Exception();
			}
			ci.setCustomer(customer);
			while(flag){
				sc = new Scanner(System.in);
				System.out.print("1.�⳵��2.֧�����3.������4.�޸�������5.�޸ĵ�ַ��0.�˳�\n��ѡ��");
				choose = 0;
				if(sc.hasNextInt()){
					choose = sc.nextInt();
				}
				switch(choose){
				case 1:
					try {
						ci.showRentableCars();
						System.out.print("������Ҫ��ĳ�����ţ�");
						int car_id = sc.nextInt();
						System.out.print("�������⳵������");
						int days = sc.nextInt();						
						Car car = ci.getCarById(car_id);						
						Rent rent = ci.rentCar(car, days);
						if(rent==null){
							System.out.println("�˳��ѱ����ߣ��޷��⳵");
							break;
						}
						Double price = rent.getRentPrice();
						System.out.println("���"+price+"Ԫ");
						System.out.print("��ѡ��֧����ʽ��1.����֧����2.����֧��");
						sc = new Scanner(System.in);
						int c = sc.nextInt();
						switch(c){
						case 1:
							System.out.println("��֧��");
							rent.setRentStatus(Rent.PAID);
							System.out.println("֧���ɹ�");
							break;
						default:
							System.out.println("�뵽���֧�����");
							break;
						}
						System.out.println("�⳵�ɹ�");
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
							System.out.println("������Ҫ֧���Ķ�����ţ�0��ʾȫ��֧��");
							sc = new Scanner(System.in);
							int c = sc.nextInt();
							if(c==0){								
								for(int i=0;i<orders.size();i++){
									Rent order = orders.get(i);
									totalPrice += order.getRentPrice();
								}
								System.out.println("��֧�������"+totalPrice+"Ԫ");
								System.out.println("֧���ɹ�");
							}else if(c>0){
								ServerInterface si = new ServerImplement();
								Rent order = si.getRentById(c);
								totalPrice = order.getRentPrice();
								System.out.println("��֧�������"+totalPrice+"Ԫ");
								System.out.println("֧���ɹ�");
							}
						}else{
							System.out.println("��û��δ֧���Ķ���");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 3:
					try {
						ci.showRentedCars();
						System.out.print("������Ҫ���ĳ�����ţ�");
						int car_id = sc.nextInt();
						Car car = ci.getCarById(car_id);
						ci.returnCar(car);
						System.out.println("�����ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 4:
					try{
						System.out.print("��������������");
						String newName = sc.next();
						ci.changeName(newName);
						System.out.println("�����ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 5:
					try{
						System.out.println("���ĵ�ǰ��ַΪ��"+((Customer) ci).getCustomerAddress());
						System.out.print("�������µ�ַ��");
						String newAdd = sc.next();
						ci.changeAddress(newAdd);
						System.out.println("�޸ĵ�ַ�ɹ�");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					flag = false;
					System.out.println("ллʹ�ã�");
				}
			}			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
