package interfaces;

import java.util.ArrayList;
import java.util.List;

import objects.Car;
import objects.Customer;
import objects.Rent;

/**
 * �ͻ��˽ӿ�
 * @author yanjisheng
 *
 */
public interface ClientInterface {
	
	/**
	 * ��ʼ��
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * ��¼
	 * @param username �û���
	 * @return Customer �û�����
	 * @throws Exception
	 */
	public Customer login(String username) throws Exception;
	
	/**
	 * ע��
	 * @param username �û���
	 * @param address ��ַ
	 * @return Customer �û�����
	 * @throws Exception
	 */
	public Customer register(String username, String address) throws Exception;
	
	/**
	 * ���õ�ǰ�û�
	 * @param customer �û�����
	 * @throws Exception
	 */
	public void setCustomer(Customer customer) throws Exception;
	
	/**
	 * ��ʾ����ĳ�����Ϣ
	 * @throws Exception
	 */
	public void showRentableCars() throws Exception;
	
	/**
	 * �⳵
	 * @param car ��������
	 * @param rentDays �⳵����
	 * @return totalRent �����
	 * @throws Exception
	 */
	public Rent rentCar(Car car, int rentDays) throws Exception;
	
	/**
	 * ��ʾ����ĳ�����Ϣ
	 * @throws Exception
	 */
	public void showRentedCars() throws Exception;
	
	/**
	 * ����
	 * @param car ��������
	 * @return
	 * @throws Exception
	 */
	public boolean returnCar(Car car) throws Exception;	
	
	/**
	 * �޸�����
	 * @param name ������
	 * @throws Exception
	 */
	public void changeName(String name) throws Exception;
	
	/**
	 * �޸ĵ�ַ
	 * @param address �µ�ַ
	 * @throws Exception
	 */
	public void changeAddress(String address) throws Exception;
	
	/**
	 * �õ�������ϸ��Ϣ
	 * @param carId �������
	 * @return ��������
	 * @throws Exception
	 */
	public Car getCarById(int carId) throws Exception;

	/**
	 * ��ʾδ֧���Ķ�����Ϣ
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Rent> showMyOrders() throws Exception;	
}
