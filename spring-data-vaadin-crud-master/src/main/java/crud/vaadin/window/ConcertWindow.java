package crud.vaadin.window;

import com.vaadin.ui.TextField;

public class ConcertWindow extends BaseWindow {
	
	public ConcertWindow(String title){
		super(title, 800);
		
	}
	
	@Override 
	protected void buildForm(){

		TextField name = new TextField("Név");
		form.addComponent(name);
		
	}

	@Override
	protected void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void delete() {
		// TODO Auto-generated method stub
		
	}

}
