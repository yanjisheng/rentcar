package interfaces;

import java.sql.Connection;
import java.util.ArrayList;

import objects.Car;
import objects.Customer;
import objects.Rent;

/**
 * ����Ա�˽ӿ�
 * @author yanjisheng
 *
 */
public interface ServerInterface {
	
	/**
	 * ��ʼ��
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * ��������
	 * @param con
	 * @throws Exception
	 */
	public void setCon(Connection con) throws Exception;
	
	/**
	 * ��ʾ���г�����Ϣ
	 * @throws Exception
	 */
	public ArrayList<Car> showCars() throws Exception;
	
	/**
	 * ��ʾ���п��⳵����Ϣ
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Car> showRentableCars() throws Exception;
	
	/**
	 * ��ʾ�����û���Ϣ
	 * @throws Exception
	 */
	public ArrayList<Customer> showCustomers() throws Exception;
	
	/**
	 * ��ӳ���
	 * @param carDetail ������ϸ��Ϣ
	 * @param carRent �������
	 * @return Car ��������
	 * @throws Exception
	 */
	public Car addCar(String carDetail, double carRent) throws Exception;
	
	/**
	 * ɾ������
	 * @param car ��������
	 * @return
	 * @throws Exception
	 */
	public boolean removeCar(Car car) throws Exception;
	
	/**
	 * ɾ������
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	/**
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public boolean removeCar(int carId) throws Exception;
	
	/**
	 * ����û�
	 * @param customerName �û�����
	 * @param customerAddress �û���ַ
	 * @return Customer �û�����
	 * @throws Exception
	 */
	public Customer addCustomer(String customerName, String customerAddress) throws Exception;
	
	/**
	 * ɾ���û�
	 * @param customer �û�����
	 * @return
	 * @throws Exception
	 */
	public boolean removeCustomer(Customer customer) throws Exception;
	
	/**
	 * ɾ���û�
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public boolean removeCustomer(int customerId) throws Exception;
	
	/**
	 * ��ʾ���ж�����Ϣ
	 * @return boolean true��ʾ�з��������ļ�¼
	 * @throws Exception
	 */
	public String showRent() throws Exception;
	
	/**
	 * ��ʾδ�����Ķ�����Ϣ
	 * @return boolean true��ʾ�з��������ļ�¼
	 * @throws Exception
	 */
	public boolean showRentedOrders() throws Exception;
	
	/**
	 * ɾ�������ѻ����Ķ�����Ϣ
	 * @return boolean true��ʾɾ���˼�¼
	 * @throws Exception
	 */
	public boolean removeRent() throws Exception;	
	
	/**
	 * �õ���������
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public Car getCarById(int carId) throws Exception;
	
	/**
	 * ͨ��������Ϣ�õ���������
	 * @param carDetail
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Car> getCarByDetail(String carDetail) throws Exception;
	
	/**
	 * �õ��û�����
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public Customer getCustomerById(int customerId) throws Exception;
	
	/**
	 * ͨ���û������õ��û�����
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Customer getCustomerByName(String name) throws Exception;
	
	/**
	 * ��Ӷ�������
	 * @param car
	 * @param customer
	 * @param days
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Rent addRent(Car car, Customer customer, int days, byte status) throws Exception;
	
	/**
	 * �õ���������
	 * @param rentId
	 * @return
	 * @throws Exception
	 */
	public Rent getRentById(int rentId) throws Exception;
	
	/**
	 * �õ�ĳ���û��Ķ���
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Rent> getRentByCustomer(Customer c) throws Exception;

	/**
	 * �ı�ĳ��������״̬
	 * @param rent
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Rent changeRentStatus(Rent rent, byte status) throws Exception;

	/**
	 * �޸ĳ������
	 * @param car
	 * @param rent
	 * @return
	 * @throws Exception
	 */
	public Car changeCarRent(Car car, double rent) throws Exception;
	
	/**
	 * ��ȡ����״̬
	 * @param car
	 * @return
	 * @throws Exception
	 */
	public byte getCarStatus(Car car) throws Exception;

	/**
	 * ��ȡ�����ĵ�ǰ����
	 * @param car
	 * @return
	 * @throws Exception
	 */
	public Rent getRentByCar(Car car) throws Exception;
}
