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
import com.vaadin.ui.components.calendar.CalendarDateRange;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClick;
import com.vaadin.ui.components.calendar.CalendarComponentEvents.EventClickHandler;
import com.vaadin.ui.components.calendar.event.BasicEvent;
import com.vaadin.ui.components.calendar.event.CalendarEvent;

import crud.backend.entity.Concert;

public class ConcertCalendar {
	
	private Calendar calendar;
	private Layout layout;
	
	public ConcertCalendar(Layout layout){
		this.layout = layout;
		init();
	}
	
	private void loadConcertData(){
		
		List<Concert> concertList = Concert.findAll();
		if(concertList != null){
			for(Concert concert : concertList){
		
			    calendar.addEvent(new ConcertEvent("Calendar study",
			            "Learning how to use Vaadin Calendar",
			            concert.getStartDate(), concert.getEndDate(), concert));
			    
			}
		}

	}
	
	
	private void init(){
	    calendar = new Calendar();
	    calendar.setLocale(new Locale("hu", "HU"));
	    calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
	    
	    loadConcertData();
	    
	    calendar.setHandler(new EventClickHandler() {
			
			@Override
			public void eventClick(EventClick event) {
				ConcertEvent e = (ConcertEvent) event.getCalendarEvent();
				//e.getConcert()
				e.getStart();
				e.getEnd();
				//TODO ConcertWindow
			}
		});
	    
	    calendar.addActionHandler(addHandler());
	    
	    layout.addComponent(calendar);
    }
	
	private Action.Handler addHandler(){
		Action.Handler actionHandler = new Action.Handler() {
	        private static final long serialVersionUID = 2122177837743583633L;
	        
	        Action addEventAction    = new Action("Koncert hozzáadása");
	
	        @Override
	        public Action[] getActions(Object target, Object sender) {
	            // The target should be a CalendarDateRage for the
	            // entire day from midnight to midnight.
	            if (! (target instanceof CalendarDateRange))
	                return null;
	            CalendarDateRange dateRange = (CalendarDateRange) target;
	
	            // The sender is the Calendar object
	            if (! (sender instanceof Calendar))
	                return null;
	            Calendar calendar = (Calendar) sender;
	            
	            // List all the events on the requested day
	            List<CalendarEvent> events =
	                    calendar.getEvents(dateRange.getStart(),
	                                       dateRange.getEnd());
	            

	            return new Action[] {addEventAction};

	        }
	
	        @Override
	        public void handleAction(Action action, Object sender, Object target) {
	            // The sender is the Calendar object
	            Calendar calendar = (Calendar) sender;
	
	            if (action == addEventAction) {
	                // Check that the click was not done on an event
	                if (target instanceof Date) {
	                    Date date = (Date) target;
	                    // Add an event from now to plus one hour
	                    GregorianCalendar start = new GregorianCalendar();
	                    start.setTime(date);
	                    GregorianCalendar end   = new GregorianCalendar();
	                    end.setTime(date);
	                    end.add(java.util.Calendar.HOUR, 1);
	                    
	                    calendar.addEvent(new ConcertEvent("Új koncert", "Kattints rá szerkesztéshez", start.getTime(), end.getTime(), new Concert()));
	                } 
	                //else
	                	//getWindow().showNotification("Can't add on an event");
	            } 
	        }
	    };
	    return actionHandler;
	}
	
	private Action.Handler addHandler2(){
		Action.Handler actionHandler = new Action.Handler() {
	        private static final long serialVersionUID = 2122177837743583633L;
	        
	        Action addEventAction    = new Action("Koncert hozzáadása");
	        Action deleteEventAction = new Action("Delete Event");
	
	        @Override
	        public Action[] getActions(Object target, Object sender) {
	            // The target should be a CalendarDateRage for the
	            // entire day from midnight to midnight.
	            if (! (target instanceof CalendarDateRange))
	                return null;
	            CalendarDateRange dateRange = (CalendarDateRange) target;
	
	            // The sender is the Calendar object
	            if (! (sender instanceof Calendar))
	                return null;
	            Calendar calendar = (Calendar) sender;
	            
	            // List all the events on the requested day
	            List<CalendarEvent> events =
	                    calendar.getEvents(dateRange.getStart(),
	                                       dateRange.getEnd());
	            
	            // You can have some logic here, using the date
	            // information.
	            if (events.size() == 0)
	                return new Action[] {addEventAction};
	            else
	                return new Action[] {addEventAction, deleteEventAction};
	        }
	
	        @Override
	        public void handleAction(Action action, Object sender, Object target) {
	            // The sender is the Calendar object
	            Calendar calendar = (Calendar) sender;
	
	            if (action == addEventAction) {
	                // Check that the click was not done on an event
	                if (target instanceof Date) {
	                    Date date = (Date) target;
	                    // Add an event from now to plus one hour
	                    GregorianCalendar start = new GregorianCalendar();
	                    start.setTime(date);
	                    GregorianCalendar end   = new GregorianCalendar();
	                    end.setTime(date);
	                    end.add(java.util.Calendar.HOUR, 1);
	                    calendar.addEvent(new BasicEvent("Calendar study",
	                            "Learning how to use Vaadin Calendar",
	                            start.getTime(), end.getTime()));
	                } 
	                //else
	                	//getWindow().showNotification("Can't add on an event");
	            } else if (action == deleteEventAction) {
	                // Check if the action was clicked on top of an event
	                if (target instanceof CalendarEvent) {
	                    CalendarEvent event = (CalendarEvent) target;
	                    calendar.removeEvent(event);
	                } 
	//                    else
	//                    getWindow().showNotification("No event to delete");
	            }
	        }
	    };
	    return actionHandler;
	}
}
