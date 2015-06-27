package crud.backend.entity;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

public class Concert {
	
	private Date startDate;
	private Date endDate;
	
	
	
	public Date getStartDate() {
		return concert.getDate("startDate");
	}

	public void setStartDate(Date startDate) {
		concert.put("startDate", startDate);
	}

	public Date getEndDate() {
		return concert.getDate("toDate");
	}

	public void setEndDate(Date endDate) {
		concert.put("toDate", endDate);
	}

	//DATA
	private ParseObject concert;
	private String bandName;
	
	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public String getStageId() {
		return concert.getParseObject("stage").getObjectId();
	}

	public void setStage(ParseObject stage) {
		concert.put("stage", stage);
	}

	public String getEventId() {
		return concert.getParseObject("event").getObjectId();
	}

	public void setEvent(ParseObject event) {
		concert.put("event", event);
	}

	public String getBandId() {
		return concert.getParseObject("band").getObjectId();
	}

	public void setBand(ParseObject band) {
		concert.put("band", band);
	}

	public ParseObject getConcert() {
		return concert;
	}

	public void setConcert(ParseObject concert) {
		this.concert = concert;
	}
	
	public static List<Concert> findByEventAndStage(String eventId, String stageId){
		ParseQuery<ParseObject> eventQuery = ParseQuery.getQuery("Event");
		eventQuery.whereEqualTo("objectId", eventId);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
		query.whereMatchesQuery("event", eventQuery);
		
		List<Concert> subList = find(query);
		List<Concert> returnList = new LinkedList<Concert>();
		for (Concert c : subList){
			if(stageId.equals(c.getStageId())){
				returnList.add(c);
			}
		}
		
		
		return returnList;
	}
	
	public static List<Concert> findByEvent(String eventId){
		ParseQuery<ParseObject> eventQuery = ParseQuery.getQuery("Event");
		eventQuery.whereEqualTo("objectId", eventId);
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
		query.whereMatchesQuery("event", eventQuery);
		return find(query);
	}

	public static List<Concert> findAll() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
		return find(query);
	}
	
	public static List<Concert> find(ParseQuery<ParseObject> query) {
		ParseCache parseCache = new ParseCache(ParseCache.BAND);
		List<Concert> concertList = new LinkedList<Concert>();
		
        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject concertParseObject = result.get(i);
                
                if(parseCache.getBandMap().get(concertParseObject.getParseObject("band").getObjectId()) != null){
	                Concert concert = new Concert();
	                concert.setConcert(concertParseObject);
	                concert.setBandName(parseCache.getBandMap().get(concertParseObject.getParseObject("band").getObjectId()).getString("name"));
	                concertList.add(concert);
                }
            }
        }catch(ParseException e){
        	e.printStackTrace();
        }
		return concertList;
	}
	
	public void save(){
		try {
			concert.save();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void delete(){
		concert.deleteInBackground();
	}
	
	public void create(){
		ParseObject c = new ParseObject("Concert");
		setConcert(c);
	}
}
