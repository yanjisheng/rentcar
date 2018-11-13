package objects;

/**
 * 顾客类
 * customerId 顾客编号
 * customerName 顾客姓名
 * customerAddress 顾客地址
 * @author yanjisheng
 *
 */
public class Customer {
	private int customerId;
	private String customerName;
	private String customerAddress;
		
	public Customer(){
		
	}	
	public Customer(int customerId, String customerName, String customerAddress) throws Exception {
		super();
		setCustomerId(customerId);
		setCustomerName(customerName);
		setCustomerAddress(customerAddress);
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) throws Exception {
		if(customerName.length()<=10){
			this.customerName = customerName;
		}else{
			throw new Exception("顾客姓名不能长于10个字！");
		}
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) throws Exception{
		if (customerAddress.length()<=40) {
			this.customerAddress = customerAddress;
		}else{
			throw new Exception("顾客地址不能长于40个字！");
		}
	}
	
	
}
