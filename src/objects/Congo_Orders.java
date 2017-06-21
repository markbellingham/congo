package objects;

import java.util.Date;

public class Congo_Orders {
	private int orderid;
	private int custid;
	private Date order_date;
	private String comments;
	
	
	public Congo_Orders(int orderid, int custid, Date order_date, String comments) {
		super();
		this.orderid = orderid;
		this.custid = custid;
		this.order_date = order_date;
		this.comments = comments;
	}


	public Congo_Orders() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Congo_Orders [orderid=" + orderid + ", custid=" + custid + ", order_date=" + order_date + ", comments="
				+ comments + "]";
	}


	public int getOrderid() {
		return orderid;
	}


	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}


	public int getCustid() {
		return custid;
	}


	public void setCustid(int custid) {
		this.custid = custid;
	}


	public Date getOrder_date() {
		return order_date;
	}


	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
}
