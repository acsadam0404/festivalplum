package crud.backend.entity;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import crud.utils.Utils;
import crud.vaadin.LanguageEnum;

public class Band {
	
	//INIT
	private String name;
	private String nationality;
	private String style;
	private String description;
	private boolean existImg;
	private byte[] image;

	public boolean getExistImg() {
		ParseFile imageFile = (ParseFile) band.get("image");
	    if(imageFile == null)
	    	return false;
	    else
	    	return true;
	}

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

	public String getDescriptionKey() {
		return band.getString("description");
	}

	public void setDescriptionKey(String description) {
		band.put("description", description);
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(String name, byte[] image) {
		byte[] resizedImage;
		try {
			resizedImage = Utils.imageResize(image);
			ParseFile file = new ParseFile(name, resizedImage);
			try {
				file.save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			band.put("image", file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	//DATA
	private ParseObject band;
	private Message descriptionValue;
	private LanguageEnum lang;
	
	public ParseObject getBand() {
		return band;
	}

	public void setBand(ParseObject band) {
		this.band = band;
	}

	public LanguageEnum getLang() {
		return lang;
	}

	public void setLang(LanguageEnum lang) {
		this.lang = lang;
	}
	
	public String getDescriptionValue() {
		if(getDescriptionKey() != null && !"".equals(getDescriptionKey())){
			descriptionValue = Message.findByKeyAndLanguage(getDescriptionKey(), getLang());
			return descriptionValue.getValue();
		}
		String key = UUID.randomUUID().toString();
		setDescriptionKey(key);
		descriptionValue = new Message();
		descriptionValue.create(key, lang);
		return descriptionValue.getValue();
	}

	public void setDescriptionValue(String descriptionValue) {
		this.descriptionValue.setValue(descriptionValue);
	}

	public static List<Band> findAll(LanguageEnum lang) {
		List<Band> bandList = new LinkedList<Band>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Band");
        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject bandParseObject = result.get(i);
                Band band = new Band();
                band.setBand(bandParseObject);
                band.setLang(lang);
                bandList.add(band);
            }
        }catch(ParseException e){
        	e.printStackTrace();
        }
		return bandList;
	}
	
	public void save(){
		try {
			band.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		descriptionValue.save();
	}
	
	public void delete(){
		descriptionValue.delete(getDescriptionKey());
		band.deleteInBackground();
	}
	
	public void create(LanguageEnum lang){
		band = new ParseObject("Band");
		String key = UUID.randomUUID().toString();
		setDescriptionKey(key);
		descriptionValue = new Message();
		descriptionValue.create(key, lang);
	}

}
