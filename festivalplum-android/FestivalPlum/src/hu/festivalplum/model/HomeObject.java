package hu.festivalplum.model;

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
    private String placeMap;
    private String placeInfo;
    private String cityName;
    private Boolean festival;
    private Boolean highPriority;

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

    public Boolean isFestival() {
        return festival;
    }

    public void setFestival(Boolean isFestival) {
        this.festival = isFestival;
    }

    public String getPlaceMap() {
        return placeMap;
    }

    public void setPlaceMap(String placeMap) {
        this.placeMap = placeMap;
    }

    public String getPlaceInfo() {
        return placeInfo;
    }

    public void setPlaceInfo(String placeInfo) {
        this.placeInfo = placeInfo;
    }

    public Boolean getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(Boolean highPriority) {
        this.highPriority = highPriority;
    }
}
