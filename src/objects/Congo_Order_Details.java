package objects;

public class Congo_Order_Details {
	private int orderid;
	private int recording_id;
	private int order_quantity;
	
	
	public Congo_Order_Details(int orderid, int recording_id, int order_quantity) {
		super();
		this.orderid = orderid;
		this.recording_id = recording_id;
		this.order_quantity = order_quantity;
	}


	public Congo_Order_Details() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Congo_Order_Details [orderid=" + orderid + ", recording_id=" + recording_id + ", order_quantity="
				+ order_quantity + "]";
	}


	public int getOrderid() {
		return orderid;
	}


	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}


	public int getRecording_id() {
		return recording_id;
	}


	public void setRecording_id(int recording_id) {
		this.recording_id = recording_id;
	}


	public int getOrder_quantity() {
		return order_quantity;
	}


	public void setOrder_quantity(int order_quantity) {
		this.order_quantity = order_quantity;
	}
	
	
	
}
