package crud.vaadin.window;

import com.vaadin.data.Item;
import com.vaadin.ui.TextField;

import crud.backend.entity.Band;


public class BandWindow extends BaseWindow {
	
	private Band band;
	private boolean exist = false;
	
	private TextField name;
	private TextField nationality;
	private TextField style;
	private TextField description;

	public BandWindow(String title) {
		super(title, 600, 400);
		if(!exist)
			band = new Band();
	}
	
	public BandWindow(String title, Band band){
		this(title);
		this.band = band;
		exist = true;
		setFormData();
	}
	
	private void setFormData(){
		name.setValue(band.getName());
		nationality.setValue(band.getNationality());
		style.setValue(band.getStyle());
		style.setDescription(band.getDescription());
	}
	
	@Override
	protected void buildForm() {
		
		name = new TextField("Név");
		form.addComponent(name);
		
		nationality = new TextField("Nemzetiség");
		form.addComponent(nationality);
		
		style = new TextField("Stílus");
		form.addComponent(style);
		
		//CKEDIT
		description = new TextField("Web");
		form.addComponent(description);
		
		// IMAGE UPLOAD
		
	}

	@Override
	protected void save() {
		if(!exist)
			band.create();
		
		band.setName(name.getValue());
		band.setStyle(style.getValue());
		band.setNationality(nationality.getValue());
		band.setDescription(description.getValue());
		//band.setImage(image);
		
		band.save();
		close();
	}

	@Override
	protected void delete() {
		band.delete();
		close();
	}

}
