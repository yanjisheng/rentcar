package interfaces;

import java.sql.Connection;
import java.util.ArrayList;

import objects.Car;
import objects.Customer;
import objects.Rent;

/**
 * 管理员端接口
 * @author yanjisheng
 *
 */
public interface ServerInterface {
	
	/**
	 * 初始化
	 * @throws Exception
	 */
	public void init() throws Exception;
	
	/**
	 * 设置连接
	 * @param con
	 * @throws Exception
	 */
	public void setCon(Connection con) throws Exception;
	
	/**
	 * 显示所有车辆信息
	 * @throws Exception
	 */
	public ArrayList<Car> showCars() throws Exception;
	
	/**
	 * 显示所有可租车辆信息
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Car> showRentableCars() throws Exception;
	
	/**
	 * 显示所有用户信息
	 * @throws Exception
	 */
	public ArrayList<Customer> showCustomers() throws Exception;
	
	/**
	 * 添加车辆
	 * @param carDetail 车辆详细信息
	 * @param carRent 车辆租金
	 * @return Car 车辆对象
	 * @throws Exception
	 */
	public Car addCar(String carDetail, double carRent) throws Exception;
	
	/**
	 * 删除车辆
	 * @param car 车辆对象
	 * @return
	 * @throws Exception
	 */
	public boolean removeCar(Car car) throws Exception;
	
	/**
	 * 删除车辆
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
	 * 添加用户
	 * @param customerName 用户姓名
	 * @param customerAddress 用户地址
	 * @return Customer 用户对象
	 * @throws Exception
	 */
	public Customer addCustomer(String customerName, String customerAddress) throws Exception;
	
	/**
	 * 删除用户
	 * @param customer 用户对象
	 * @return
	 * @throws Exception
	 */
	public boolean removeCustomer(Customer customer) throws Exception;
	
	/**
	 * 删除用户
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public boolean removeCustomer(int customerId) throws Exception;
	
	/**
	 * 显示所有订单信息
	 * @return boolean true表示有符合条件的记录
	 * @throws Exception
	 */
	public String showRent() throws Exception;
	
	/**
	 * 显示未还车的订单信息
	 * @return boolean true表示有符合条件的记录
	 * @throws Exception
	 */
	public boolean showRentedOrders() throws Exception;
	
	/**
	 * 删除所有已还车的订单信息
	 * @return boolean true表示删除了记录
	 * @throws Exception
	 */
	public boolean removeRent() throws Exception;	
	
	/**
	 * 得到车辆对象
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public Car getCarById(int carId) throws Exception;
	
	/**
	 * 通过车辆信息得到车辆对象
	 * @param carDetail
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Car> getCarByDetail(String carDetail) throws Exception;
	
	/**
	 * 得到用户对象
	 * @param customerId
	 * @return
	 * @throws Exception
	 */
	public Customer getCustomerById(int customerId) throws Exception;
	
	/**
	 * 通过用户姓名得到用户对象
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public Customer getCustomerByName(String name) throws Exception;
	
	/**
	 * 添加订单对象
	 * @param car
	 * @param customer
	 * @param days
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Rent addRent(Car car, Customer customer, int days, byte status) throws Exception;
	
	/**
	 * 得到订单对象
	 * @param rentId
	 * @return
	 * @throws Exception
	 */
	public Rent getRentById(int rentId) throws Exception;
	
	/**
	 * 得到某个用户的订单
	 * @param c
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Rent> getRentByCustomer(Customer c) throws Exception;

	/**
	 * 改变某条订单的状态
	 * @param rent
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public Rent changeRentStatus(Rent rent, byte status) throws Exception;

	/**
	 * 修改车辆租金
	 * @param car
	 * @param rent
	 * @return
	 * @throws Exception
	 */
	public Car changeCarRent(Car car, double rent) throws Exception;
	
	/**
	 * 获取车辆状态
	 * @param car
	 * @return
	 * @throws Exception
	 */
	public byte getCarStatus(Car car) throws Exception;

	/**
	 * 获取车辆的当前订单
	 * @param car
	 * @return
	 * @throws Exception
	 */
	public Rent getRentByCar(Car car) throws Exception;
}
