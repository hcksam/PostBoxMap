package hkpu.postboxmap;

/**
 * Created by hck on 1/5/2016.
 */
public class CommonBean {
    private String area;
    private String address;
    private Double latitude;
    private Double longitude;

    public CommonBean() {
    }

    public CommonBean(String area, String address, Double latitude, Double longitude) {
        this.area = area;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "CommonBean{" +
                "area='" + area + '\'' +
                ", address='" + address + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
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
}
