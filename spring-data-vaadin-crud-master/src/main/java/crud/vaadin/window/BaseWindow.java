package crud.vaadin.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Window;

abstract class BaseWindow extends Window{
	
	protected FormLayout form;
	protected Button saveButton;
	

	protected BaseWindow(String title, float width, float height) {
		super(title);
		setWidth(width, Unit.PIXELS);
		setHeight(height, Unit.PIXELS);
		setModal(true);
		center();
		initForm();
	}
	
	private void initForm(){
		form = new FormLayout();
		form.setSizeFull();
		buildForm();
		setContent(form);
	}
	
	abstract protected void buildForm();
	
}
