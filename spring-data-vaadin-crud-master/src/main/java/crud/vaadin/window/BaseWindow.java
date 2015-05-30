package crud.vaadin.window;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

abstract class BaseWindow extends Window{
	
	protected VerticalLayout mainLayout;
	protected FormLayout form;
	protected FormLayout form2;
	protected HorizontalLayout buttonContainer;
	protected Button saveButton;
	protected Button deleteButton;
	

	protected BaseWindow(String title, float width) {
		super(title);
		setWidth(width, Unit.PIXELS);
		//setHeight(height, Unit.PIXELS);
		setHeight(100, Unit.PERCENTAGE);
		setModal(true);
		mainLayout = new VerticalLayout();
		setContent(mainLayout);
		center();
		initButton();
		initForm();
	}
	
	private void initForm(){
		form = new FormLayout();
		form.setSizeFull();
		form.setSpacing(true);
		form.setMargin(true);
		
		form2 = new FormLayout();
		form2.setSizeFull();
		form2.setSpacing(true);
		form2.setMargin(true);
		
		buildForm();
		
		GridLayout grid = new GridLayout(2, 1);
		grid.addComponent(form);
		grid.addComponent(form2);
		mainLayout.addComponent(grid);
	}
	
	private void initButton(){
		buttonContainer = new HorizontalLayout();
		buttonContainer.setWidth(200, Unit.PIXELS);
		buttonContainer.setHeight(40, Unit.PIXELS);
		
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
