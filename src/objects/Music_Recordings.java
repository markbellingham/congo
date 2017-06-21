package objects;

public class Music_Recordings {
	
	private int id;
	private String artist;
	private String title;
	private String category;
	private String image_name;
	private int num_tracks;
	private float price;
	private int stock_count;
	
	public Music_Recordings(int id, String artist, String title, String category, String image_name, int num_tracks, float price,
			int stock_count) {
		super();
		this.id = id;
		this.artist = artist;
		this.title = title;
		this.category = category;
		this.image_name = image_name;
		this.num_tracks = num_tracks;
		this.price = price;
		this.stock_count = stock_count;
	}
	

	@Override
	public String toString() {
		return "Album [id=" + id + ", artist=" + artist + ", title=" + title + ", category=" + category
				+ ", image_name=" + image_name + ", num_tracks=" + num_tracks + ", price=" + price + ", stock_count="
				+ stock_count + "]";
	}
	

	public Music_Recordings() {
		super();
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getImage_name() {
		return image_name;
	}
	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}

	public int getNum_tracks() {
		return num_tracks;
	}
	public void setNum_tracks(int num_tracks) {
		this.num_tracks = num_tracks;
	}

	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}

	public int getStock_count() {
		return stock_count;
	}
	public void setStock_count(int stock_count) {
		this.stock_count = stock_count;
	}
	
		
	
}
