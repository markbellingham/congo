package objects;

public class Music_Categories {
	private int id;
	private String name;
	public Music_Categories(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	public Music_Categories() {
		super();
	}
	@Override
	public String toString() {
		return "Music_Categories [id=" + id + ", name=" + name + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
