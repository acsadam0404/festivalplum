package crud.backend.entity;

import org.parse4j.ParseObject;

public class Stage {
	private String name;
	private ParseObject stage;
	
	public String getName() {
		return stage.getString("name");
	}
	public void setName(String name) {
		stage.put("name", name);
	}
	public ParseObject getStage() {
		return stage;
	}
	public void setStage(ParseObject stage) {
		this.stage = stage;
	}

}
