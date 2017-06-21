package objects;

public class Congo_Customers {
	private int custid;
	private String fname;
	private String lname;
	private String address1;
	private String address2;
	private String city;
	private String postcode;
	private String phone;
	private String email;
	private String password;
	private String admin;
	
	
	public Congo_Customers(int custid, String fname, String lname, String address1, String address2, String city,
			String postcode, String phone, String email, String password, String admin) {
		super();
		this.custid = custid;
		this.fname = fname;
		this.lname = lname;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.postcode = postcode;
		this.phone = phone;
		this.email = email;
		this.password = password;
		this.admin = admin;
	}


	public Congo_Customers() {
		super();
		// TODO Auto-generated constructor stub
	}


	@Override
	public String toString() {
		return "Congo_Customers [custid=" + custid + ", fname=" + fname + ", lname=" + lname + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", postcode=" + postcode + ", phone=" + phone
				+ ", email=" + email + ", password=" + password + ", admin=" + admin + "]";
	}


	public int getCustid() {
		return custid;
	}


	public void setCustid(int custid) {
		this.custid = custid;
	}


	public String getFname() {
		return fname;
	}


	public void setFname(String fname) {
		this.fname = fname;
	}


	public String getLname() {
		return lname;
	}


	public void setLname(String lname) {
		this.lname = lname;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAdmin() {
		return admin;
	}


	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	
}
