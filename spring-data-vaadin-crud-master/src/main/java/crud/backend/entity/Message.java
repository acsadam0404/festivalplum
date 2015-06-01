package crud.backend.entity;

import java.util.LinkedList;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import crud.vaadin.LanguageEnum;

public class Message {
	//INIT
	private String key;
	private String language;
	private String value;
	//DATA
	private ParseObject message;
	
	public String getKey() {
		return message.getString("key");
	}

	public void setKey(String key) {
		message.put("key", key);
	}

	public String getLanguage() {
		return message.getString("language");
	}

	public void setLanguage(LanguageEnum lang) {
		message.put("language", lang.toString().toLowerCase());
	}

	public String getValue() {
		return message.getString("value");
	}

	public void setValue(String value) {
		message.put("value", value);
	}

	public void setMessage(ParseObject message) {
		this.message = message;
	}

	public static Message findByKeyAndLanguage(String key, LanguageEnum lang){
		Message message = new Message();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		query.whereEqualTo("key", key);
		query.whereEqualTo("language", lang.toString().toLowerCase());
        try {
            List<ParseObject> result = query.find();
            if(result != null){
            	ParseObject messageParseObject = result.get(0);
            	message.setMessage(messageParseObject);
            }else{
            	message.create(key, lang);
            }

        }catch(ParseException e){
        	e.printStackTrace();
        }
		return message;
	}
	
	public void create(String key, LanguageEnum lang){
		setMessage(new ParseObject("Message"));
		setKey(key);
    	setLanguage(lang);
	}
	
	public void save(){
		message.saveInBackground();
	}
	
}
