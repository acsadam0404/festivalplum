package crud.backend.entity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseObject;

import crud.vaadin.LanguageEnum;
import crud.vaadin.calendar.ComboBoxItem;

public class ParseUtils {
	
	public static List<ComboBoxItem> getStageList(ParseObject event, Map<String, ParseObject> stageMap, LanguageEnum language){
		List<ComboBoxItem> stageItemList = new LinkedList<ComboBoxItem>();

		String placeObjectId = event.getParseObject("place").getObjectId();
		
		for (Object key : stageMap.keySet()) {
			ParseObject stage = stageMap.get(key);
			if( placeObjectId.equals(stage.getParseObject("place").getObjectId())){
				Message message = Message.findByKeyAndLanguage(stage.getString("name"), language);
				stageItemList.add(new ComboBoxItem(stage.getObjectId(), message.getValue()));
			}
		}
		
		return stageItemList;
	}

}
