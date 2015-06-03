package crud.backend.entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
import org.parse4j.callback.FindCallback;
import org.springframework.util.StopWatch;

import crud.vaadin.calendar.ComboBoxItem;

public class ParseCache {
	
	public static final String BAND = "Band";
	public static final String STAGE = "Stage";
	public static final String EVENT = "Event";
	public static final String PLACE = "Place";
	
	private Map<String, ParseObject> bandMap;
	private Map<String, ParseObject> stageMap;
	private Map<String, ParseObject> eventMap;
	private Map<String, ParseObject> placeMap;
	
	private List<ComboBoxItem> bandItemList;
	private List<ComboBoxItem> stageItemList;
	private List<ComboBoxItem> eventItemList;
	
	public ParseCache() {
		findInBackgroundAll(BAND);
		findInBackgroundAll(STAGE);
		findInBackgroundAll(EVENT);
	}
	
	public ParseCache(String parseTable) {
		findAll(parseTable);
	}
	
	public void findAll(final String parseTable){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(parseTable);
		try{
			List<ParseObject> list = query.find();
			setTable(list, parseTable);
		}catch(ParseException e){
        	e.printStackTrace();
        }
	}
	
	public void findInBackgroundAll(final String parseTable){
		ParseQuery<ParseObject> query = ParseQuery.getQuery(parseTable);
		query.findInBackground(new FindCallback<ParseObject>() {
			
			@Override
			public void done(List<ParseObject> list, ParseException parseException) {
				setTable(list, parseTable);
			}
		});
	}
	
	private void setTable(List<ParseObject> list, String parseTable){
		switch (parseTable) {
		case BAND:
			bandMap = new LinkedHashMap<String, ParseObject>();
			bandItemList = new LinkedList<ComboBoxItem>();
			for(ParseObject o : list){
				bandMap.put(o.getObjectId(), o);
				bandItemList.add(new ComboBoxItem(o.getObjectId(), o.getString("name")));
			}
			break;
		case STAGE:
			stageMap = new LinkedHashMap<String, ParseObject>();
			stageItemList = new LinkedList<ComboBoxItem>();
			for(ParseObject o : list){
				stageMap.put(o.getObjectId(), o);
				stageItemList.add(new ComboBoxItem(o.getObjectId(), o.getString("name")));
			}
			break;
		case EVENT:
			eventMap = new LinkedHashMap<String, ParseObject>();
			eventItemList = new LinkedList<ComboBoxItem>();
			for(ParseObject o : list){
				eventMap.put(o.getObjectId(), o);
				eventItemList.add(new ComboBoxItem(o.getObjectId(), o.getString("name")));
			}
			break;
		case PLACE:
			placeMap = new LinkedHashMap<String, ParseObject>();
			for(ParseObject o : list){
				placeMap.put(o.getObjectId(), o);
			}
			break;
		default:
			break;
		}
	}

	public Map<String, ParseObject> getBandMap() {
		return bandMap;
	}

	public Map<String, ParseObject> getStageMap() {
		return stageMap;
	}

	public Map<String, ParseObject> getEventMap() {
		return eventMap;
	}

	public Map<String, ParseObject> getPlaceMap() {
		return placeMap;
	}

	public List<ComboBoxItem> getBandItemList() {
		return bandItemList;
	}

	public List<ComboBoxItem> getStageItemList() {
		return stageItemList;
	}

	public List<ComboBoxItem> getEventItemList() {
		return eventItemList;
	}
	
	public ComboBoxItem getSelectedBand(String id){
		for(int i = 0; i < bandItemList.size(); i++){
			if(id.equals(bandItemList.get(i).getId())){
				return bandItemList.get(i);
			}
		}
		return null;
	}
	
	public ComboBoxItem getSelectedEvent(String id){
		for(int i = 0; i < eventItemList.size(); i++){
			if(id.equals(eventItemList.get(i).getId())){
				return eventItemList.get(i);
			}
		}
		return null;
	}
	
	public ComboBoxItem getSelectedStage(String id){
		for(int i = 0; i < stageItemList.size(); i++){
			if(id.equals(stageItemList.get(i).getId())){
				return stageItemList.get(i);
			}
		}
		return null;
	}

}
