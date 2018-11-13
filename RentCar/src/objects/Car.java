package objects;

import implement.ServerImplement;
import interfaces.ServerInterface;

/**
 * ������
 * carId �������
 * carDetail ������Ϣ
 * carRent �������
 * @author yanjisheng
 *
 */
public class Car {
	private int carId;
	private String carDetail;
	private double carCost;
	
	public Car(int carId, String carDetail, double carRent) throws Exception {
		super();
		this.setCarId(carId);
		this.setCarDetail(carDetail);
		this.setCarCost(carRent);
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public String getCarDetail() {
		return carDetail;
	}
	public void setCarDetail(String carDetail) throws Exception {
		if(carDetail.length()<=100){
			this.carDetail = carDetail;
		} else{
			throw new Exception("������Ϣ���ܳ���100�֣�");
		}
	}
	public double getCarCost() {
		return carCost;
	}
	public void setCarCost(double carRent) throws Exception {
		if(carRent>0&&carRent<=100000){
			this.carCost = carRent;
		} else{
			throw new Exception("�������Ӧ��0��10��֮�䣡");
		}
	}
	
	public byte getCarStatus() throws Exception{
		ServerInterface si = new ServerImplement();
		byte rentStatus = si.getCarStatus(this);
		return rentStatus;
	}
	
	public Rent getRent() throws Exception{
		ServerInterface si = new ServerImplement();
		Rent rent = si.getRentByCar(this);
		return rent;
	}
}
