package ro.tuc.ds2020.dtos;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class DeviceDTO {

    private UUID id;
    @NotNull
    private String address;

    public DeviceDTO() {
    }

    public DeviceDTO(UUID id, String address) {
        this.id = id;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
