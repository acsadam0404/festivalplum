package crud.vaadin.window;

import java.util.Date;

import com.vaadin.data.Item;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import crud.backend.entity.Festival;

public class FestivalWindow extends BaseWindow {
	
	private Festival festival;
	private boolean exist = false;
	
	private TextField name;
	private CheckBox isFestival;
	private TextField email;
	private TextField phone;
	private TextField website;
	private TextField country;
	private TextField city;
	private TextField address;
	private TextField postcode;
	private CheckBox priority;
	private DateField startDate;
	private DateField endDate;
	private TextField description;
	private TextField map;
	
	public FestivalWindow(String title){
		super(title, 600, 400);
		if(!exist)
			festival = new Festival();
		//initStage();
	}
	
	public FestivalWindow(String title, Festival festival){
		this(title);
		this.festival = festival;
		exist = true;
		setFormData();
	}
	
	private void setFormData(){
		  name.setValue(festival.getName());
		  isFestival.setValue(festival.getFestival());
		  email.setValue(festival.getEmail());
		  phone.setValue(festival.getPhone());
		  website.setValue(festival.getWebsite());
		  country.setValue(festival.getCountry());
		  city.setValue(festival.getCity());
		  address.setValue(festival.getAddress());
		  postcode.setValue(festival.getPostcode());
		  priority.setValue(festival.getPriority());
		  startDate.setValue(festival.getStartDate());
		  endDate.setValue(festival.getEndDate());
		  description.setValue(festival.getDescription());
		  map.setValue(festival.getMap());
	}
	
	@Override 
	protected void buildForm(){

		name = new TextField("Név");
		form.addComponent(name);
		
		isFestival = new CheckBox("Fesztivál");
		form.addComponent(isFestival);
		
		email = new TextField("Email");
		form.addComponent(email);
		
		phone = new TextField("Telefon");
		form.addComponent(phone);
		
		website = new TextField("Weboldal");
		form.addComponent(website);
		
		country = new TextField("Ország");
		form.addComponent(country);
		
		city = new TextField("Város");
		form.addComponent(city);
		
		address = new TextField("Cím");
		form.addComponent(address);
		
		postcode = new TextField("Irányítószám");
		form.addComponent(postcode);
		
		priority = new CheckBox("Prioritás");
		form.addComponent(priority);
		
		startDate = new DateField("Kezdődik");
		form.addComponent(startDate);
		
		endDate = new DateField("Vége");
		form.addComponent(endDate);
		
		//CKEDIT
		description = new TextField("Web");
		form.addComponent(description);
		
		//CKEDIT
		map = new TextField("Térkép");
		form.addComponent(map);
		
		// IMAGE UPLOAD
		
	}

	@Override
	protected void save() {
		//SET Festivál data
		if(!exist)
			festival.create();
		festival.setName(name.getValue());
		festival.setFestival(isFestival.getValue());
		festival.setEmail(email.getValue());
		festival.setPhone(phone.getValue());
		festival.setWebsite(website.getValue());
		festival.setCountry(country.getValue());
		festival.setCity(city.getValue());
		festival.setAddress(address.getValue());
		festival.setPostcode(postcode.getValue());
		festival.setPriority(priority.getValue());
		festival.setStartDate(startDate.getValue());
		festival.setEndDate(endDate.getValue());
		festival.setDescription(description.getValue());
		festival.setMap(map.getValue());
		//festival.setImage(image);
	
		festival.save();
		close();
	}

	@Override
	protected void delete() {
		festival.delete();
		close();
	}
}
