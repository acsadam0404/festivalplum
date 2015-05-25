package crud.vaadin.window;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

abstract class BaseWindow extends Window{
	
	protected VerticalLayout mainLayout;
	protected FormLayout form;
	protected HorizontalLayout buttonContainer;
	protected Button saveButton;
	protected Button deleteButton;
	

	protected BaseWindow(String title, float width, float height) {
		super(title);
		setWidth(width, Unit.PIXELS);
		setHeight(height, Unit.PIXELS);
		setModal(true);
		mainLayout = new VerticalLayout();
		setContent(mainLayout);
		center();
		initForm();
		initButton();
	}
	
	private void initForm(){
		form = new FormLayout();
		form.setSizeFull();
		buildForm();
		mainLayout.addComponent(form);
	}
	
	private void initButton(){
		buttonContainer = new HorizontalLayout();
		buttonContainer.setWidth(100, Unit.PERCENTAGE);
		buttonContainer.setHeight(100, Unit.PIXELS);
		
		saveButton = new Button("Mentés");
		saveButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				save();
			}
		});
		buttonContainer.addComponent(saveButton);
		
		deleteButton = new Button("Törlés");
		deleteButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				delete();
			}
		});
		buttonContainer.addComponent(deleteButton);
		
		mainLayout.addComponent(buttonContainer);
	}
	
	abstract protected void save();
	
	abstract protected void delete();
	
	abstract protected void buildForm();
	
}
