package crud.backend.entity;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.SaveCallback;
import org.springframework.util.StopWatch;

import crud.utils.Utils;
import crud.vaadin.LanguageEnum;


public class Festival {
	
	//INIT
	private String name;
	private boolean festival;
	private String email;
	private String phone;
	private String website;
	private String country;
	private String city;
	private String address;
	private String postcode;
	private boolean priority;
	private Date startDate;
	private Date endDate;
	private String description;
	private byte[] image;
	private String map;
	
	public String getName() {
		return place.getString("name");
	}

	public void setName(String name) {
		place.put("name", name);
	}
	
	public boolean getFestival() {
		return event.getBoolean("isFestival");
	}

	public void setFestival(boolean isFestival) {
		event.put("isFestival", isFestival);
	}

	public String getEmail() {
		return place.getString("Email");
	}

	public void setEmail(String email) {
		place.put("Email", email);
	}

	public String getPhone() {
		return place.getString("Phone");
	}

	public void setPhone(String phone) {
		place.put("Phone", phone);
	}

	public String getWebsite() {
		return place.getString("Website");
	}

	public void setWebsite(String website) {
		place.put("Website", website);
	}

	public String getCountry() {
		return place.getString("country");
	}

	public void setCountry(String country) {
		place.put("country", country);
	}

	public String getCity() {
		return place.getString("city");
	}

	public void setCity(String city) {
		place.put("city", city);
	}

	public String getAddress() {
		return place.getString("Address");
	}

	public void setAddress(String address) {
		place.put("Address", address);
	}

	public String getPostcode() {
		return place.getString("postal_code");
	}

	public void setPostcode(String postcode) {
		place.put("postal_code", postcode);
	}

	public boolean getPriority() {
		return place.getBoolean("highPriority");
	}

	public void setPriority(boolean priority) {
		place.put("highPriority", priority);
	}

	public Date getStartDate() {
		return place.getDate("startDate");
	}

	public void setStartDate(Date startDate) {
		place.put("startDate", startDate);
	}

	public Date getEndDate() {
		return place.getDate("toDate");
	}

	public void setEndDate(Date endDate) {
		place.put("toDate", endDate);
	}

	public String getDescriptionKey() {
		return place.getString("description");
	}

	public void setDescriptionKey(String description) {
		place.put("description", description);
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
			place.put("image", file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public String getMap() {
		return place.getString("map");
	}

	public void setMap(String map) {
		place.put("map", map);
	}

	//DATA
	private List<ParseObject> stageList = new LinkedList<ParseObject>();
	private ParseObject event;
	private ParseObject place;
	private LanguageEnum lang;
	private Message descriptionValue;

	public List<Stage> getStageList() {
		//STAGE LIST
		List<ParseObject> stageList = new LinkedList<ParseObject>();
		if(place != null){
	        ParseQuery<ParseObject> q = ParseQuery.getQuery("Place");
	        q.whereEqualTo("objectId", place.getObjectId());
	        ParseQuery<ParseObject> stageQuery = ParseQuery.getQuery("Stage");
	        stageQuery.whereMatchesQuery("place", q);
	        try{
		        List<ParseObject> stageResult = stageQuery.find();
		        if(stageResult != null){
		            for(int j = 0; j < stageResult.size(); j++) {
		            	ParseObject stageParseObject = stageResult.get(j);
		            	stageList.add(stageParseObject);
		            }
		        }
	        } catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
        setStageList(stageList);
		
		List<Stage> list = new LinkedList<Stage>();
		for(ParseObject o : stageList){
			Stage s = new Stage();
			s.setStage(o);
			s.setNameMessage(Message.findByKeyAndLanguage(s.getNameKey(), lang));
			if(s.getName() != null && !"".equals(s.getName())){
				list.add(s);
			}
		}
		return list;
	}

	public void setStageList(List<ParseObject> stageList) {
		this.stageList = stageList;
	}
	
	public void addStage(String name){
		ParseObject stage = new ParseObject("Stage");
		String key = UUID.randomUUID().toString();
		stage.put("name", key);
		stage.put("place", place);
		stageList.add(stage);
		stage.saveInBackground();
		Message message = new Message();
		message.create(key, lang);
		message.setValue(name);
		message.save();
	}
	
	public void removeStage(ParseObject stage){
		Message.delete(stage.getString("name"));
		stageList.remove(stage);
		stage.deleteInBackground();
	}

	public ParseObject getEvent() {
		return event;
	}

	public void setEvent(ParseObject event) {
		this.event = event;
	}

	public ParseObject getPlace() {
		return place;
	}

	public void setPlace(ParseObject place) {
		this.place = place;
	}

	public LanguageEnum getLang() {
		return lang;
	}

	public void setLang(LanguageEnum lang) {
		this.lang = lang;
	}

	public static List<Festival> findAll(LanguageEnum lang) {
		StopWatch sw = new StopWatch();
		sw.start();
		//TODO lang Message -- Place.desc, Place.country
		List<Festival> festivalList = new LinkedList<Festival>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Event");
        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject eventParseObject = result.get(i);
                String placeObjectId = eventParseObject.getParseObject("place").getObjectId();
                ParseQuery<ParseObject> placeQuery = ParseQuery.getQuery("Place");
                placeQuery.whereEqualTo("objectId", placeObjectId);
                List<ParseObject> placeResult = placeQuery.find();
                ParseObject placeParseObject = null;
                if(placeResult != null)
                	placeParseObject = placeResult.get(0);
                
                
                Festival festival = new Festival();
                festival.setEvent(eventParseObject);
                festival.setPlace(placeParseObject);
                festival.setLang(lang);
                
                festivalList.add(festival);
                
            }
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        sw.stop();
        System.out.println(sw.prettyPrint());
		return festivalList;
	}
	
	public void save(){
		descriptionValue.save();
		place.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException parseException) {
				event.put("place", place);
				event.saveInBackground();
				
			}
		});
	}
	
	public void delete(){
		descriptionValue.delete(getDescriptionKey());
		for(ParseObject po : stageList){
			Message.delete(po.getString("name"));
			po.deleteInBackground();
		}
		event.deleteInBackground(new DeleteCallback() {
			@Override
			public void done(ParseException parseException) {
				place.deleteInBackground();
			}
		});
		
	}
	
	public void create(LanguageEnum lang){
		place = new ParseObject("Place");
		event = new ParseObject("Event");
		String key = UUID.randomUUID().toString();
		setDescriptionKey(key);
		descriptionValue = new Message();
		descriptionValue.create(key, lang);
	}
}
