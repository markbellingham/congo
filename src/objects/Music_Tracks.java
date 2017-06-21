package objects;

public class Music_Tracks {
	private int id;
	private int recording_id;
	private String title;
	private int duration;
	
	public Music_Tracks(int id, int recording_id, String title, int duration) {
		super();
		this.id = id;
		this.recording_id = recording_id;
		this.title = title;
		this.duration = duration;
	}

	public Music_Tracks() {
		super();
	}

	@Override
	public String toString() {
		return "Music_Tracks [id=" + id + ", recording_id=" + recording_id + ", title=" + title + ", duration="
				+ duration + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRecording_id() {
		return recording_id;
	}

	public void setRecording_id(int recording_id) {
		this.recording_id = recording_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	
}
