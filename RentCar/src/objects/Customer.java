package objects;

/**
 * �˿���
 * customerId �˿ͱ��
 * customerName �˿�����
 * customerAddress �˿͵�ַ
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
			throw new Exception("�˿��������ܳ���10���֣�");
		}
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) throws Exception{
		if (customerAddress.length()<=40) {
			this.customerAddress = customerAddress;
		}else{
			throw new Exception("�˿͵�ַ���ܳ���40���֣�");
		}
	}
	
	
}
