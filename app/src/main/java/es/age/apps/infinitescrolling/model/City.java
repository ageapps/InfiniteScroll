package es.age.apps.infinitescrolling.model;


/**
 * Created by Adrián García Espinosa on 25/3/16.
 */
public class City {

	// Getter and Setter model for recycler view items
	private String title, location, description;
	private int image;

	public City(String title, String location, String description, int image) {
		this.description = description;
		this.title = title;
		this.location = location;
		this.image = image;
	}

	public String getTitle() {
		return title;
	}

	public String getLocation() {
		return location;
	}

	public String getDescription() {
		return description;
	}

	public int getImage() {
		return image;
	}
}
