package interfaces;

import java.util.ArrayList;
import java.util.List;

import objects.Car;
import objects.Customer;
import objects.Rent;

/**
 * 客户端接口
 * @author yanjisheng
 *
 */
public interface ClientInterface {
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * 登录
	 * @param username 用户名
	 * @return Customer 用户对象
	 * @throws Exception
	 */
	public Customer login(String username) throws Exception;
	
	/**
	 * 注册
	 * @param username 用户名
	 * @param address 地址
	 * @return Customer 用户对象
	 * @throws Exception
	 */
	public Customer register(String username, String address) throws Exception;
	
	/**
	 * 设置当前用户
	 * @param customer 用户对象
	 * @throws Exception
	 */
	public void setCustomer(Customer customer) throws Exception;
	
	/**
	 * 显示可租的车辆信息
	 * @throws Exception
	 */
	public void showRentableCars() throws Exception;
	
	/**
	 * 租车
	 * @param car 车辆对象
	 * @param rentDays 租车天数
	 * @return totalRent 总租金
	 * @throws Exception
	 */
	public Rent rentCar(Car car, int rentDays) throws Exception;
	
	/**
	 * 显示已租的车辆信息
	 * @throws Exception
	 */
	public void showRentedCars() throws Exception;
	
	/**
	 * 还车
	 * @param car 车辆对象
	 * @return
	 * @throws Exception
	 */
	public boolean returnCar(Car car) throws Exception;	
	
	/**
	 * 修改姓名
	 * @param name 新姓名
	 * @throws Exception
	 */
	public void changeName(String name) throws Exception;
	
	/**
	 * 修改地址
	 * @param address 新地址
	 * @throws Exception
	 */
	public void changeAddress(String address) throws Exception;
	
	/**
	 * 得到车辆详细信息
	 * @param carId 车辆编号
	 * @return 车辆对象
	 * @throws Exception
	 */
	public Car getCarById(int carId) throws Exception;

	/**
	 * 显示未支付的订单信息
	 * @return
	 * @throws Exception 
	 */
	public ArrayList<Rent> showMyOrders() throws Exception;	
}
