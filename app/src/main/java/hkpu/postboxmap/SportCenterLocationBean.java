package hkpu.postboxmap;

/**
 * Created by hck on 9/4/2016.
 */
public class SportCenterLocationBean extends CommonBean {
    private String name;
    private Double latitude;
    private Double longitude;
    private String type;
    private String address;
    private String area;
    private String phoneNumber;
    private String email;
    private String webSite;
    private String weekdays;
    private String weekend;

    public SportCenterLocationBean() {
        super();
    }

    public SportCenterLocationBean(String name, Double latitude, Double longitude, String type, String address, String area, String phoneNumber, String email, String webSite, String weekdays, String weekend) {
        super(area, address, latitude, longitude);
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
        this.address = address;
        this.area = area;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.webSite = webSite;
        this.weekdays = weekdays;
        this.weekend = weekend;
    }

    @Override
    public String toString() {
        return "SportCenterLocationBean{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", webSite='" + webSite + '\'' +
                ", weekdays='" + weekdays + '\'' +
                ", weekend='" + weekend + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
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
