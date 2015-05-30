package crud.vaadin.view;

import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import crud.backend.entity.Band;
import crud.backend.entity.Festival;
import crud.vaadin.component.BandListComp;
import crud.vaadin.component.FestivalListComp;
import crud.vaadin.window.BandWindow;
import crud.vaadin.window.FestivalWindow;

public class MainView extends VerticalLayout implements View {
	
	private VerticalLayout content;
	
	public MainView() {
		setSizeFull();
		setSpacing(true);
		setMargin(true);
		buildMenu();
		buildContent();
	}
	
	private void buildMenu() {
		HorizontalLayout menuLayout = new HorizontalLayout();
		menuLayout.setWidth(100, Unit.PERCENTAGE);
		menuLayout.setSpacing(true);
		MenuBar menu = new MenuBar();
		menu.addItem("Fellépő", null, bandCommand());
		menu.addItem("Fesztivál", null, festivalCommand());
		menu.addItem("Koncert", null, concertCommand());

		menuLayout.addComponent(menu);
		menuLayout.setComponentAlignment(menu, Alignment.MIDDLE_LEFT);

		addComponent(menuLayout);
    }
	
	private void buildContent(){
		content = new VerticalLayout();
		content.setSizeFull();
		content.setSpacing(true);
		loadBand();
		addComponent(content);
		setExpandRatio(content, 1.0f);
		setComponentAlignment(content, Alignment.TOP_LEFT);
	}
	
	private Command bandCommand(){
		return new Command() {

		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	loadBand();
		    }
		};
	}
	
	private void loadBand(){
		content.removeAllComponents();
    	Button add = new Button("Hozzáad");
    	add.addClickListener(new ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().addWindow(new BandWindow("Fellépő Létrehozása", false));
			}
		});
		content.addComponent(add);
		BandListComp data = new BandListComp();
		data.getTable().addItemClickListener(new ItemClickListener() {

		    @Override
		    public void itemClick(ItemClickEvent event) {
		    	BeanItem<Band> item = (BeanItem) event.getItem();
		    	Band band = item.getBean();
		    	UI.getCurrent().addWindow(new BandWindow("Fellépő Módosítása", band));
		    }
		});
		content.addComponent(data);
		content.setExpandRatio(data, 1.0f);
	}
	
	private Command festivalCommand(){
		return new Command() {

		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	content.removeAllComponents();
		    	Button add = new Button("Hozzáad");
		    	add.addClickListener(new ClickListener() {
					
					@Override
					public void buttonClick(ClickEvent event) {
						UI.getCurrent().addWindow(new FestivalWindow("Fesztivál Létrehozása", false));
					}
				});
				content.addComponent(add);
				FestivalListComp data = new FestivalListComp();
				data.getTable().addItemClickListener(new ItemClickListener() {

				    @Override
				    public void itemClick(ItemClickEvent event) {
				    	BeanItem<Festival> item = (BeanItem) event.getItem();
				    	Festival festival = item.getBean();
				    	UI.getCurrent().addWindow(new FestivalWindow("Fesztivál Módosítása", festival));
				    }
				});
				content.addComponent(data);
				content.setExpandRatio(data, 1.0f);
		    }
		};
	}
	
	private Command concertCommand(){
		return new Command() {

		    @Override
		    public void menuSelected(MenuItem selectedItem) {
		    	Button b = new Button("Concert");
		    	content.removeAllComponents();
				content.addComponent(b);
		    }
		};
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

}
