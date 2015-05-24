package crud.vaadin.window;

import com.vaadin.ui.TextField;

public class ConcertWindow extends BaseWindow {
	
	public ConcertWindow(String title){
		super(title, 200, 300);
		
	}
	
	@Override 
	protected void buildForm(){

		TextField name = new TextField("Név");
		form.addComponent(name);
		
	}

}
