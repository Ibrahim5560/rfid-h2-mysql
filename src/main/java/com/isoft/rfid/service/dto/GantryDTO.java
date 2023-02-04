package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.Gantry} entity.
 */
@Schema(description = "Gantry (gantry) entity.\n @author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GantryDTO implements Serializable {

    private Long id;

    private String name;

    private Integer route;

    private Integer lanes;

    private Double longitude;

    private Double lat;

    private Integer jurisdiction;

    private Integer gantryType;

    private Integer gantrySet;

    private Integer active;

    private String password;

    private Instant updated;

    private String ip;

    @NotNull
    private Double toll;

    @NotNull
    private Integer passageTimes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public Integer getLanes() {
        return lanes;
    }

    public void setLanes(Integer lanes) {
        this.lanes = lanes;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(Integer jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Integer getGantryType() {
        return gantryType;
    }

    public void setGantryType(Integer gantryType) {
        this.gantryType = gantryType;
    }

    public Integer getGantrySet() {
        return gantrySet;
    }

    public void setGantrySet(Integer gantrySet) {
        this.gantrySet = gantrySet;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getToll() {
        return toll;
    }

    public void setToll(Double toll) {
        this.toll = toll;
    }

    public Integer getPassageTimes() {
        return passageTimes;
    }

    public void setPassageTimes(Integer passageTimes) {
        this.passageTimes = passageTimes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GantryDTO)) {
            return false;
        }

        GantryDTO gantryDTO = (GantryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gantryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GantryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", route=" + getRoute() +
            ", lanes=" + getLanes() +
            ", longitude=" + getLongitude() +
            ", lat=" + getLat() +
            ", jurisdiction=" + getJurisdiction() +
            ", gantryType=" + getGantryType() +
            ", gantrySet=" + getGantrySet() +
            ", active=" + getActive() +
            ", password='" + getPassword() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", ip='" + getIp() + "'" +
            ", toll=" + getToll() +
            ", passageTimes=" + getPassageTimes() +
            "}";
    }
}
