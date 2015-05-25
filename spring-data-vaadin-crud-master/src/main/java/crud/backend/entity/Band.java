package crud.backend.entity;

import java.util.LinkedList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class Band {
	
	//INIT
	private String name;
	private String nationality;
	private String style;
	private String description;
	private byte[] image;

	public String getName() {
		return band.getString("name");
	}

	public void setName(String name) {
		band.put("name", name);
	}

	public String getNationality() {
		return band.getString("nationality");
	}

	public void setNationality(String nationality) {
		band.put("nationality", nationality);
	}

	public String getStyle() {
		return band.getString("style");
	}

	public void setStyle(String style) {
		band.put("style", style);
	}

	public String getDescription() {
		return band.getString("description");
	}

	public void setDescription(String description) {
		band.put("description", description);
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	//DATA
	private ParseObject band;
	
	public ParseObject getBand() {
		return band;
	}

	public void setBand(ParseObject band) {
		this.band = band;
	}

	public static List<Band> findAll() {
		List<Band> bandList = new LinkedList<Band>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Band");
        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject bandParseObject = result.get(i);
                Band band = new Band();
                band.setBand(bandParseObject);
                bandList.add(band);
            }
        }catch(ParseException e){
        	e.printStackTrace();
        }
		return bandList;
	}
	
	public void save(){
		band.saveInBackground();
	}
	
	public void delete(){
		band.deleteInBackground();
	}
	
	public void create(){
		band = new ParseObject("Band");
	}

}
