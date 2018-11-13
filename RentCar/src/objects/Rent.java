package objects;

import implement.ServerImplement;
import interfaces.ServerInterface;

import java.util.Date;//注意这里不是java.sql.Date

/**
 * 租车订单类
 * @author yanjisheng
 *
 */
public class Rent {
	private int rentId;
	private int rentCar;
	private int rentCustomer;
	private Date rentDate;
	private int rentDays;
	private byte rentStatus;
	
	public static final byte NOT_PAID = 1;
	public static final byte PAID = 2;
	public static final byte RENTED = 3;
	public static final byte RETURNED = 4;
	
	public Rent(int rentId, int rentCar, int rentCustomer, Date rentDate,
			int rentDays, byte rentStatus) {
		super();
		this.rentId = rentId;
		this.rentCar = rentCar;
		this.rentCustomer = rentCustomer;
		this.rentDate = rentDate;
		this.rentDays = rentDays;
		this.rentStatus = rentStatus;
	}
	public int getRentId() {
		return rentId;
	}
	public void setRentId(int rentId) {
		this.rentId = rentId;
	}
	public int getRentCar() {
		return rentCar;
	}
	public void setRentCar(int rentCar) {
		this.rentCar = rentCar;
	}
	public int getRentCustomer() {
		return rentCustomer;
	}
	public void setRentCustomer(int rentCustomer) {
		this.rentCustomer = rentCustomer;
	}
	public Date getRentDate() {
		return rentDate;
	}
	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	public int getRentDays() {
		return rentDays;
	}
	public void setRentDays(int rentDays) {
		this.rentDays = rentDays;
	}
	public byte getRentStatus() {
		return rentStatus;
	}
	public void setRentStatus(byte rentStatus) throws Exception {
		ServerInterface si = new ServerImplement();
		si.changeRentStatus(this, rentStatus);
		this.rentStatus = rentStatus;		
	}
	
	public double getRentPrice() throws Exception{
		ServerInterface si = new ServerImplement();
		Car car = si.getCarById(getRentCar());
		double rentPrice = car.getCarCost() * this.getRentDays();
		return rentPrice;		
	}
}
