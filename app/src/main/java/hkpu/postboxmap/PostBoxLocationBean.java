package hkpu.postboxmap;

/**
 * Created by hck on 9/4/2016.
 */
public class PostBoxLocationBean {
    private String area;
    private String address;
    private Double latitude;
    private Double longitude;
    private String weekdays;
    private String weekend;

    public PostBoxLocationBean() {
    }

    public PostBoxLocationBean(String area, String address, Double latitude, Double longitude, String weekdays, String weekend) {
        this.area = area;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weekdays = weekdays;
        this.weekend = weekend;
    }

    @Override
    public String toString() {
        return "PostBoxLocationBean{" +
                "area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", weekdays='" + weekdays + '\'' +
                ", weekend='" + weekend + '\'' +
                '}';
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(String weekdays) {
        this.weekdays = weekdays;
    }

    public String getWeekend() {
        return weekend;
    }

    public void setWeekend(String weekend) {
        this.weekend = weekend;
    }
}
