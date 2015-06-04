package crud.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import pl.exsio.plupload.Plupload;
import pl.exsio.plupload.PluploadError;
import pl.exsio.plupload.PluploadFile;
import pl.exsio.plupload.handler.memory.ByteArrayChunkHandlerFactory;

public class FileUpload extends HorizontalLayout {
	
	private Plupload plUpload;
	private Label infoLabel;
	
	public FileUpload() {
		super();
		buildUpload();
	}
	
	private void buildUpload(){
		this.infoLabel = new Label();
		this.plUpload = this.createUpload(infoLabel, "Fájl feltöltése");
		
		setHeight(50, Unit.PIXELS);
		addComponent(this.plUpload);
		addComponent(this.infoLabel);
		
	}
	
	private Plupload createUpload(Label infoLabel, final String uploadText) {
		final Plupload upload = new Plupload(uploadText, FontAwesome.FILES_O);
		upload.setChunkHandlerFactory(new ByteArrayChunkHandlerFactory());

		upload.setMaxFileSize(Utils.MAX_FILE_SIZE);
		upload.setMultiSelection(false);

		upload.addFileUploadedListener(new Plupload.FileUploadedListener() {
		    @Override
		    public void onFileUploaded(PluploadFile file) {
		    	try {
					String fileName = file.getName();
					byte[] data = file.getUploadedFileAs(byte[].class);

					Path uploadPath = Paths.get(Utils.UPLOAD_FOLDER);
					Path filePath = uploadPath.resolve(fileName);
					Files.write(filePath, data);

				} catch (Exception e) {
				    Notification.show("Feltöltés NEM sikerült!");
				}
		    	
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
		    	plUpload.start();
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

}
