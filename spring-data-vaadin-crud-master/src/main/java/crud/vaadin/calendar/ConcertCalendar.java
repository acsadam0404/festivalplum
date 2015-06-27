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
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectHandler;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.RangeSelectEvent;
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import crud.backend.entity.Concert;
import crud.backend.entity.ParseCache;
import crud.vaadin.LanguageEnum;
import crud.vaadin.view.MainView;
import crud.vaadin.window.ConcertWindow;

public class ConcertCalendar {
	
	private Calendar calendar;
	private Layout layout;
	private ParseCache parseCache;
	private LanguageEnum lang;
	
	public ConcertCalendar(Layout layout, LanguageEnum lang, ParseCache parseCache, String eventId, String stageId, MainView view){
		this.lang = lang;
		this.layout = layout;
		this.parseCache = parseCache;
		init(eventId, stageId, view);
	}
	
	private void loadConcertData(String eventId, String stageId){
		
		List<Concert> concertList;
		if(eventId != null && stageId != null)
			concertList = Concert.findByEventAndStage(eventId, stageId);
		else if(eventId != null)
			concertList = Concert.findByEvent(eventId);
		else
			concertList = Concert.findAll();
		
		if(concertList != null){
			for(Concert concert : concertList){
			    calendar.addEvent(new ConcertEvent(concert.getBandName(), "", concert.getStartDate(), concert.getEndDate(), concert));
			}
		}

	}
	
	private void init(final String eventId, final String stageId, MainView view){
		calendar = new Calendar("");
		calendar.setLocale(new Locale("hu", "HU"));
	    //calendar.setTimeZone(TimeZone.getTimeZone("GMT+2"));
	    calendar.setSizeFull();
	    loadConcertData(eventId, stageId);
	    
	    calendar.setHandler(new EventClickHandler() {
			
			@Override
			public void eventClick(EventClick event) {
				ConcertEvent e = (ConcertEvent) event.getCalendarEvent();
				
				ConcertWindow concertWindow = new ConcertWindow("Koncert", e.getStart(), e.getEnd(), parseCache, e.getConcert(),lang);
				concertWindow.addCloseListener(new CloseListener() {
					
					@Override
					public void windowClose(CloseEvent e) {
						view.loadConcert(eventId, stageId);
						
					}
				});
				
				UI.getCurrent().addWindow(concertWindow);

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
