package crud.vaadin.calendar;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ComboBoxItem implements Serializable {
	private String id;
	private String description;

	public ComboBoxItem(final String id, final String description) {
	    this.id = id;
	    this.description = description;
	}
	
	public final void setId(final String id) {
	    this.id = id;
	}
	
	public final void setDescription(final String description) {
	    this.description = description;
	}
	
	public final String getId() {
	    return id;
	}
	
	public final String getDescription() {
	    return description;
	}
}
