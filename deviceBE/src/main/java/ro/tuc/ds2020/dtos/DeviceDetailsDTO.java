package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceDetailsDTO {

    private UUID id;
    private UUID userId;
    private String description;
    private String address;
    private Integer consumption;

    public DeviceDetailsDTO() {
    }

    public DeviceDetailsDTO(UUID userId, String description, String address, Integer consumption) {
        this.userId = userId;
        this.description = description;
        this.address = address;
        this.consumption = consumption;
    }

    public DeviceDetailsDTO(UUID id, UUID userId, String description, String address, Integer consumption) {
        this.userId = userId;
        this.id = id;
        this.description = description;
        this.address = address;
        this.consumption = consumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getConsumption() {
        return consumption;
    }

    public void setConsumption(Integer consumption) {
        this.consumption = consumption;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
