package hu.festivalplum.model;

/**
 * Created by viktor on 2015.03.28..
 */
public class BandObject {

    private String bandId;
    private String name;
    private String nationality;
    private String style;
    private byte[] bandImg;

    public BandObject(){
        //
    }

    public String getBandId() {
        return bandId;
    }

    public void setBandId(String bandId) {
        this.bandId = bandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public byte[] getBandImg() {
        return bandImg;
    }

    public void setBandImg(byte[] bandImg) {
        this.bandImg = bandImg;
    }
}
