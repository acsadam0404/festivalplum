package hu.festivalplum.model;

import java.util.Date;

/**
 * Created by viktor on 2015.03.18..
 */
public class FestivalObject {

    private String concertId;
    private String bandName;
    private byte[] image;
    private String StageName;
    private Date startDate;
    private Date toDate;
    private boolean favorite;

    public FestivalObject(){
        //
    }

    public String getBandName() {
        return bandName;
    }

    public void setBandName(String bandName) {
        this.bandName = bandName;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStageName() {
        return StageName;
    }

    public void setStageName(String stageName) {
        StageName = stageName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getConcertId() {
        return concertId;
    }

    public void setConcertId(String concertId) {
        this.concertId = concertId;
    }
}
