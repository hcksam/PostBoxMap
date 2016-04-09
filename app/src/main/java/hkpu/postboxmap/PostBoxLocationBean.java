package hkpu.postboxmap;

/**
 * Created by hck on 9/4/2016.
 */
public class PostBoxLocationBean {
    private Long id;
    private String address;
    private Double latitude;
    private Double longitude;

    public PostBoxLocationBean() {
    }

    public PostBoxLocationBean(long id, String address, Double latitude, Double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
