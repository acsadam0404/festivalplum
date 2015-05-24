package crud.vaadin.window;

import com.vaadin.data.Item;
import com.vaadin.ui.TextField;

public class FestivalWindow extends BaseWindow {
	
	public FestivalWindow(String title){
		super(title, 600, 400);
		
	}
	
	public FestivalWindow(String title, Item item){
		this(title);
		
	}
	
	@Override 
	protected void buildForm(){

		TextField name = new TextField("Név");
		form.addComponent(name);
		
	}
}
