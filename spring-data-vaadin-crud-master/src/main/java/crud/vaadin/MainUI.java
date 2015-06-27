package crud.vaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import crud.vaadin.view.MainView;

import org.parse4j.Parse;

@Title("FestivalPlum")
@Theme("valo")
@SpringUI
public class MainUI extends UI {
	private static final String MAIN = "main";
	private static final String APP_ID = "EHBh2sz9yo6a99A5UMDjYBKPGWD9zpl35JhSZQG8";
	private static final String APP_REST_API_ID = "5UmFXA63snvwWjXjyofJ8QSZt2mVHRFJ36FSvPEc";

	private Navigator navigator;

	@Override
	protected void init(VaadinRequest request) {
		Parse.initialize(APP_ID, APP_REST_API_ID);
		setSizeFull();
		VerticalLayout l = new VerticalLayout();
		l.setSpacing(true);
		l.setMargin(true);
		Panel mainPanel = new Panel(l);
		mainPanel.setSizeFull();
		setContent(mainPanel);
		navigator = new Navigator(this, mainPanel);
		navigator.addView(MAIN, new MainView());
		navigator.navigateTo(MAIN);

	}

}
