package hkpu.postboxmap;

/**
 * Created by hck on 1/5/2016.
 */
public class LocationDataBean extends CommonBean{
    private String markerID;
    private String name;
    private Double latitude;
    private Double longitude;
    private String category;
    private String address;
    private String area;
    private String city;
    private String phoneNumber;
    private String email;
    private String webSite;
    private String hour1;
    private String hour2;
    private String hour3;
    private String hour4;
    private String maintenanceDay;
    private String featured;
    private String type;
    private String service;

    public LocationDataBean() {
        super();
    }

    public LocationDataBean(String area, String address, Double latitude, Double longitude) {
        super(area, address, latitude, longitude);
    }

    @Override
    public String toString() {
        return "LocationDataBean{" +
                "markerID='" + markerID + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", category='" + category + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", webSite='" + webSite + '\'' +
                ", hour1='" + hour1 + '\'' +
                ", hour2='" + hour2 + '\'' +
                ", hour3='" + hour3 + '\'' +
                ", hour4='" + hour4 + '\'' +
                ", maintenanceDay='" + maintenanceDay + '\'' +
                ", featured='" + featured + '\'' +
                ", type='" + type + '\'' +
                ", service='" + service + '\'' +
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

    public String getMarkerID() {
        return markerID;
    }

    public void setMarkerID(String markerID) {
        this.markerID = markerID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHour1() {
        return hour1;
    }

    public void setHour1(String hour1) {
        this.hour1 = hour1;
    }

    public String getHour2() {
        return hour2;
    }

    public void setHour2(String hour2) {
        this.hour2 = hour2;
    }

    public String getHour3() {
        return hour3;
    }

    public void setHour3(String hour3) {
        this.hour3 = hour3;
    }

    public String getHour4() {
        return hour4;
    }

    public void setHour4(String hour4) {
        this.hour4 = hour4;
    }

    public String getMaintenanceDay() {
        return maintenanceDay;
    }

    public void setMaintenanceDay(String maintenanceDay) {
        this.maintenanceDay = maintenanceDay;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
