package hu.festivalplum.home;

import java.util.Date;

/**
 * Created by viktor on 2015.03.17..
 */
public class HomeObject {

    private String eventId;
    private Date startDate;
    private Date endDate;
    private byte[] placeImg;
    private String placeName;
    private String cityName;

    public HomeObject(){
        //
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public byte[] getPlaceImg() {
        return placeImg;
    }

    public void setPlaceImg(byte[] placeImg) {
        this.placeImg = placeImg;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
