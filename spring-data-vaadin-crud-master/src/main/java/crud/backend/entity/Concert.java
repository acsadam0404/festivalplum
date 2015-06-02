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
	private ParseObject stage;
	private ParseObject event;
	private ParseObject band;
	
	public ParseObject getConcert() {
		return concert;
	}

	public void setConcert(ParseObject concert) {
		this.concert = concert;
	}

	public ParseObject getStage() {
		return stage;
	}

	public void setStage(ParseObject stage) {
		this.stage = stage;
	}

	public ParseObject getEvent() {
		return event;
	}

	public void setEvent(ParseObject event) {
		this.event = event;
	}

	public ParseObject getBand() {
		return band;
	}

	public void setBand(ParseObject band) {
		this.band = band;
	}

	public static List<Concert> findAll() {
		List<Concert> concertList = new LinkedList<Concert>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Concert");
        try {
            List<ParseObject> result = query.find();
            for(int i = 0; i < result.size(); i++) {
                ParseObject concertParseObject = result.get(i);
                Concert concert = new Concert();
                concert.setConcert(concertParseObject);
                concertList.add(concert);
            }
        }catch(ParseException e){
        	e.printStackTrace();
        }
		return concertList;
	}
}
