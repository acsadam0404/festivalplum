package crud.vaadin.calendar;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.parse4j.ParseObject;

import com.vaadin.event.Action;
import com.vaadin.ui.Calendar;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import crud.backend.entity.Concert;
import crud.backend.entity.ParseCache;
import crud.vaadin.window.ConcertWindow;

public class ConcertCalendar {
	
	private Calendar calendar;
	private Layout layout;
	private ParseCache parseCache;
	
	public ConcertCalendar(Layout layout){
		this.layout = layout;
		parseCache = new ParseCache();
		init();
	}
	
	private void loadConcertData(){
		
		List<Concert> concertList = Concert.findAll();
		if(concertList != null){
			for(Concert concert : concertList){
			    calendar.addEvent(new ConcertEvent(concert.getBandName(), "", concert.getStartDate(), concert.getEndDate(), concert));
			}
		}

	}
	
	private void init(){
		calendar = new Calendar("");
		calendar.setLocale(new Locale("hu", "HU"));
	    calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	    loadConcertData();
	    
	    calendar.setHandler(new EventClickHandler() {
			
			@Override
			public void eventClick(EventClick event) {
				ConcertEvent e = (ConcertEvent) event.getCalendarEvent();
				UI.getCurrent().addWindow(new ConcertWindow("Koncert", e.getStart(), e.getEnd(), parseCache, e.getConcert()));
			}
		});
		        

		calendar.setHandler(new RangeSelectHandler() {
		    @Override
		    public void rangeSelect(RangeSelectEvent event) {
		        calendar.addEvent(new ConcertEvent("Új koncert", "", event.getStart(), event.getEnd(), new Concert()));
		    }
		});
		        
		layout.addComponent(calendar);
	}
	
}
