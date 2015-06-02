package crud.vaadin.calendar;

import java.util.Date;

import org.parse4j.ParseObject;

import com.vaadin.ui.components.calendar.event.BasicEvent;

import crud.backend.entity.Concert;

public class ConcertEvent extends BasicEvent {

	private Concert concert;
	
	public ConcertEvent(String caption, String description, Date startDate, Date endDate, Concert concert) {
        super(caption, description, startDate, endDate);
        this.concert = concert;
	}

	public Concert getConcert() {
		return concert;
	}

	public void setConcert(Concert concert) {
		this.concert = concert;
	}
	
}
