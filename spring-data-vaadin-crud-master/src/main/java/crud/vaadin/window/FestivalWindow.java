package crud.vaadin.window;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.handler.memory.ByteArrayChunkHandlerFactory;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import crud.backend.entity.Festival;
import crud.utils.Utils;
import crud.vaadin.component.StageListComp;

public class FestivalWindow extends BaseWindow {
	
	private static final String MAX_FILE_SITE = "20mb";
	
	private Festival festival;
	private boolean exist = false;
	private StageListComp stageData;
	
	private Plupload plUpload;
	private Label infoLabel;
	
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
	
	public FestivalWindow(String title, boolean exist){
		super(title, 600, 400);
		this.exist = exist;
		if(!exist){
			festival = new Festival();
			buildUpload();
			initStage();
		}
	}
	
	public FestivalWindow(String title, Festival festival){
		this(title, true);
		this.festival = festival;
		setFormData();
		buildUpload();
		initStage();
	}
	
	private void buildUpload(){
		this.infoLabel = new Label();
		this.plUpload = this.createUpload();
		
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.addComponent(this.plUpload);
		horizontalLayout.addComponent(this.infoLabel);
		mainLayout.addComponent(horizontalLayout);
	}
	
	private Plupload createUpload() {
		final Plupload upload = new Plupload("Feltöltés", FontAwesome.FILES_O);
		upload.setChunkHandlerFactory(new ByteArrayChunkHandlerFactory());

		upload.setMaxFileSize(MAX_FILE_SITE);
		upload.setMultiSelection(false);

		upload.addFileUploadedListener(new Plupload.FileUploadedListener() {
		    @Override
		    public void onFileUploaded(PluploadFile file) {
		    	saveDoc(file);
		    }
		});

		upload.addUploadProgressListener(new Plupload.UploadProgressListener() {
		    @Override
		    public void onUploadProgress(PluploadFile file) {
			infoLabel.setValue(file.getName() + " feltöltése folyamatban:" + file.getPercent() + "%");
		    }
		});

		upload.addFilesAddedListener(new Plupload.FilesAddedListener() {
		    @Override
		    public void onFilesAdded(PluploadFile[] files) {
			infoLabel.setValue(files[0].getName());
		    }
		});

		upload.addUploadCompleteListener(new Plupload.UploadCompleteListener() {
		    @Override
		    public void onUploadComplete() {
			infoLabel.setValue("Feltöltés befejeződött.");
		    }
		});

		upload.addErrorListener(new Plupload.ErrorListener() {
		    @Override
		    public void onError(PluploadError error) {
			Notification.show("Hiba történt a feltöltés során: " + error.getMessage() + " (" + error.getType() + ")", Notification.Type.ERROR_MESSAGE);
		    }
		});

		upload.setChunkSize("2mb");

		upload.setWidthUndefined();

		return upload;
	}
	
	private void saveDoc(PluploadFile file) {
		try {
		    byte[] data = file.getUploadedFileAs(byte[].class);
		    festival.setImage(file.getName(), data);
		    saveData();
		} catch (Exception e) {
		    Notification.show("Feltöltés NEM sikerült!");
		}
	}
	
	private void setFormData(){
		Utils.setValue(name, festival.getName());
		Utils.setValue(isFestival, festival.getFestival());
		Utils.setValue(email, festival.getEmail());
		Utils.setValue(phone, festival.getPhone());
		Utils.setValue(website, festival.getWebsite());
		Utils.setValue(country, festival.getCountry());
		Utils.setValue(city, festival.getCity());
		Utils.setValue(address, festival.getAddress());
		Utils.setValue(postcode, festival.getPostcode());
		Utils.setValue(priority, festival.getPriority());
		Utils.setValue(startDate, festival.getStartDate());
		Utils.setValue(endDate, festival.getEndDate());
		Utils.setValue(description, festival.getDescription());
		Utils.setValue(map, festival.getMap());
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

	}
	
	private void initStage(){
		HorizontalLayout addStageForm = new HorizontalLayout();
		addStageForm.setWidth(100, Unit.PERCENTAGE);
		addStageForm.setHeight(100, Unit.PIXELS);
		TextField stageName = new TextField("Színpad neve");
		addStageForm.addComponent(stageName);
		Button addStageButton = new Button("Hozzáadás");
		addStageForm.addComponent(addStageButton);
		
		addStageButton.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				if(stageName.getValue() != null && !"".equals(stageName.getValue())){
					festival.addStage(stageName.getValue());
					stageData.refresh(festival.getStageList());
				}
			}
		});
		
		mainLayout.addComponent(addStageForm);
		
		stageData = new StageListComp(festival.getStageList());
		stageData.setWidth(100, Unit.PERCENTAGE);
		stageData.setHeight(300, Unit.PIXELS);
		mainLayout.addComponent(stageData);
	}

	@Override
	protected void save() {
		if(!exist)
			festival.create();
		
		if ((this.plUpload.getQueuedFiles() != null) && (this.plUpload.getQueuedFiles().length > 0)) {
			this.plUpload.start();
		}else{
			saveData();
		}

	}
	
	private void saveData(){
		festival.setName(Utils.getValue(name));
		festival.setFestival(Utils.getValue(isFestival));
		festival.setEmail(Utils.getValue(email));
		festival.setPhone(Utils.getValue(phone));
		festival.setWebsite(Utils.getValue(website));
		festival.setCountry(Utils.getValue(country));
		festival.setCity(Utils.getValue(city));
		festival.setAddress(Utils.getValue(address));
		festival.setPostcode(Utils.getValue(postcode));
		festival.setPriority(Utils.getValue(priority));
		festival.setStartDate(Utils.getValue(startDate));
		festival.setEndDate(Utils.getValue(endDate));
		festival.setDescription(Utils.getValue(description));
		festival.setMap(Utils.getValue(map));
		
		festival.save();
		close();
	}

	@Override
	protected void delete() {
		festival.delete();
		close();
	}
}
