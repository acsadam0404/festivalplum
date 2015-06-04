package crud.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;

import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextField;

import crud.vaadin.calendar.ComboBoxItem;

public class Utils {
	
    private static int IMAGE_WITH = 100;
    private static int IMAGE_HEIGHT = 100;
    
    public static final String MAX_FILE_SIZE = "20mb";
    public static final String UPLOAD_FOLDER = "c:/tmp/";
	public static final String FIX_URL = "http://www.ittafile.hu/";
	
	public static void setValue(TextField field, String value) {
		if (value != null) {
		    field.setValue(value);
		}
    }
	
	public static void setValue(CKEditorTextField field, String value) {
		if (value != null) {
		    field.setValue(value);
		}
    }

    public static void setValue(DateField field, Date value) {
		if (value != null) {
		    field.setValue(value);
		}
    }
    
    public static void setValue(CheckBox field, Boolean value) {
		if (value != null) {
		    field.setValue(value);
		}
    }
    
    public static String getValue(TextField field){
    	return field.getValue();
    }
    
    public static String getValue(CKEditorTextField field){
    	return field.getValue();
    }
    
    public static Date getValue(DateField field){
    	return field.getValue();
    }
    
    public static Boolean getValue(CheckBox field){
    	if(field.getValue() == null){
    		return false;
    	}
    	return field.getValue();
    }
    
    public static byte[] imageResize(byte[] imageInByte) throws IOException{
    	boolean preserveAlpha = true;
    	
    	BufferedImage image = byteArryToImage(imageInByte);
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(IMAGE_WITH, IMAGE_HEIGHT, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
    	g.drawImage(image, 0, 0, IMAGE_WITH, IMAGE_HEIGHT, null); 
    	g.dispose();
    	return imageToByteArry(scaledBI);
    }
    
    private static byte[] imageToByteArry(BufferedImage image) throws IOException{
    	byte[] imageInByte;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
    }
    
    private static BufferedImage byteArryToImage(byte[] imageInByte) throws IOException{
    	InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage image = ImageIO.read(in);
		return image;
    }
    
}
