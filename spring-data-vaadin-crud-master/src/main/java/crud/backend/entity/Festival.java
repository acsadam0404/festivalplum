package crud.backend.entity;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseFile;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.DeleteCallback;
import org.parse4j.callback.SaveCallback;
import org.springframework.util.StopWatch;

import crud.utils.Utils;


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
		this.email = email;
	}

	public String getPhone() {
		return place.getString("Phone");
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return place.getString("Website");
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getCountry() {
		return place.getString("country");
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return place.getString("city");
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return place.getString("Address");
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostcode() {
		return place.getString("postal_code");
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public boolean getPriority() {
		return place.getBoolean("highPriority");
	}

	public void setPriority(boolean priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return place.getDate("startDate");
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return place.getDate("toDate");
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return place.getString("description");
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Stage> getStageList() {
		List<Stage> list = new LinkedList<Stage>();
		for(ParseObject o : stageList){
			Stage s = new Stage();
			s.setStage(o);
			list.add(s);
		}
		return list;
	}

	public void setStageList(List<ParseObject> stageList) {
		this.stageList = stageList;
	}
	
	public void addStage(String name){
		ParseObject stage = new ParseObject("Stage");
		stage.put("name", name);
		stage.put("place", place);
		stageList.add(stage);
		stage.saveInBackground();
	}
	
	public void removeStage(ParseObject stage){
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

	public static List<Festival> findAll() {
		StopWatch sw = new StopWatch();
		sw.start();
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
                
                //STAGE LIST
                ParseQuery<ParseObject> q = ParseQuery.getQuery("Place");
                q.whereEqualTo("objectId", placeObjectId);
                ParseQuery<ParseObject> stageQuery = ParseQuery.getQuery("Stage");
                stageQuery.whereMatchesQuery("place", q);
                List<ParseObject> stageResult = stageQuery.find();
                List<ParseObject> stageList = new LinkedList<ParseObject>();;
                if(stageResult != null){
	                for(int j = 0; j < stageResult.size(); j++) {
	                	ParseObject stageParseObject = stageResult.get(j);
	                	stageList.add(stageParseObject);
	                }
                }
                Festival festival = new Festival();
                festival.setEvent(eventParseObject);
                festival.setPlace(placeParseObject);
                festival.setStageList(stageList);
                
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
		place.saveInBackground(new SaveCallback() {
			@Override
			public void done(ParseException parseException) {
				event.put("place", place);
				event.saveInBackground();
				
			}
		});
	}
	
	public void delete(){
		for(ParseObject po : stageList){
			po.deleteInBackground();
		}
		event.deleteInBackground(new DeleteCallback() {
			@Override
			public void done(ParseException parseException) {
				place.deleteInBackground();
			}
		});
		
	}
	
	public void create(){
		place = new ParseObject("Place");
		event = new ParseObject("Event");
	}
}
