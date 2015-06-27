package crud.backend.entity;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

public class Stage {
	private String name;
	private ParseObject stage;
	private Message nameValue;
	
	public String getNameKey() {
		return stage.getString("name");
	}
	public void setNameKey(String name) {
		stage.put("name", name);
	}
	public String getName() {
		return nameValue.getValue();
	}
	public void setName(String name) {
		nameValue.setValue(name);
	}
	public ParseObject getStage() {
		return stage;
	}
	public void setStage(ParseObject stage) {
		this.stage = stage;
	}
	
	public void setNameMessage(Message nameValue){
		this.nameValue = nameValue;
	}
	
	public void delete(){
		nameValue.delete(getNameKey());
		stage.deleteInBackground();
	}
	
	public void saveName(){
		nameValue.save();
	}

}
