package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.Vehicle} entity.
 */
@Schema(description = "Vehicle (vehicle) entity.\n@author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleDTO implements Serializable {

    private Long id;

    private String govid;

    @NotNull
    private String plate;

    @NotNull
    private String rfid;

    @NotNull
    private Long driver;

    private String producer;

    private String model;

    private Long year;

    private String color;

    private String decals;

    private String spoilers;

    private String glass;

    private String chassisSerial;

    private String motorSerial;

    private String vehicleLicenseSerial;

    @NotNull
    private Long vehicleLicenseType;

    @NotNull
    private Long vehicleType;

    private Integer wanted;

    private Integer licenseRevoked;

    private Integer licenseExpired;

    @NotNull
    private Long jurisdiction;

    @NotNull
    private Instant updated;

    private String fuelType;

    private Long motorCc;

    private Instant licenseIssued;

    private Instant licenseExpires;

    private Long wtKg;

    private Integer passengers;

    private Integer tractorParts;

    private Integer extraSizePercent;

    private Long wtExtra;

    private String bikeData;

    private Long wantedBy;

    private String wantedFor;

    private Long office;

    private String statusName;

    private Instant stolen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGovid() {
        return govid;
    }

    public void setGovid(String govid) {
        this.govid = govid;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public Long getDriver() {
        return driver;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDecals() {
        return decals;
    }

    public void setDecals(String decals) {
        this.decals = decals;
    }

    public String getSpoilers() {
        return spoilers;
    }

    public void setSpoilers(String spoilers) {
        this.spoilers = spoilers;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getChassisSerial() {
        return chassisSerial;
    }

    public void setChassisSerial(String chassisSerial) {
        this.chassisSerial = chassisSerial;
    }

    public String getMotorSerial() {
        return motorSerial;
    }

    public void setMotorSerial(String motorSerial) {
        this.motorSerial = motorSerial;
    }

    public String getVehicleLicenseSerial() {
        return vehicleLicenseSerial;
    }

    public void setVehicleLicenseSerial(String vehicleLicenseSerial) {
        this.vehicleLicenseSerial = vehicleLicenseSerial;
    }

    public Long getVehicleLicenseType() {
        return vehicleLicenseType;
    }

    public void setVehicleLicenseType(Long vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public Long getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(Long vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getWanted() {
        return wanted;
    }

    public void setWanted(Integer wanted) {
        this.wanted = wanted;
    }

    public Integer getLicenseRevoked() {
        return licenseRevoked;
    }

    public void setLicenseRevoked(Integer licenseRevoked) {
        this.licenseRevoked = licenseRevoked;
    }

    public Integer getLicenseExpired() {
        return licenseExpired;
    }

    public void setLicenseExpired(Integer licenseExpired) {
        this.licenseExpired = licenseExpired;
    }

    public Long getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(Long jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Long getMotorCc() {
        return motorCc;
    }

    public void setMotorCc(Long motorCc) {
        this.motorCc = motorCc;
    }

    public Instant getLicenseIssued() {
        return licenseIssued;
    }

    public void setLicenseIssued(Instant licenseIssued) {
        this.licenseIssued = licenseIssued;
    }

    public Instant getLicenseExpires() {
        return licenseExpires;
    }

    public void setLicenseExpires(Instant licenseExpires) {
        this.licenseExpires = licenseExpires;
    }

    public Long getWtKg() {
        return wtKg;
    }

    public void setWtKg(Long wtKg) {
        this.wtKg = wtKg;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Integer getTractorParts() {
        return tractorParts;
    }

    public void setTractorParts(Integer tractorParts) {
        this.tractorParts = tractorParts;
    }

    public Integer getExtraSizePercent() {
        return extraSizePercent;
    }

    public void setExtraSizePercent(Integer extraSizePercent) {
        this.extraSizePercent = extraSizePercent;
    }

    public Long getWtExtra() {
        return wtExtra;
    }

    public void setWtExtra(Long wtExtra) {
        this.wtExtra = wtExtra;
    }

    public String getBikeData() {
        return bikeData;
    }

    public void setBikeData(String bikeData) {
        this.bikeData = bikeData;
    }

    public Long getWantedBy() {
        return wantedBy;
    }

    public void setWantedBy(Long wantedBy) {
        this.wantedBy = wantedBy;
    }

    public String getWantedFor() {
        return wantedFor;
    }

    public void setWantedFor(String wantedFor) {
        this.wantedFor = wantedFor;
    }

    public Long getOffice() {
        return office;
    }

    public void setOffice(Long office) {
        this.office = office;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Instant getStolen() {
        return stolen;
    }

    public void setStolen(Instant stolen) {
        this.stolen = stolen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleDTO)) {
            return false;
        }

        VehicleDTO vehicleDTO = (VehicleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleDTO{" +
            "id=" + getId() +
            ", govid='" + getGovid() + "'" +
            ", plate='" + getPlate() + "'" +
            ", rfid='" + getRfid() + "'" +
            ", driver=" + getDriver() +
            ", producer='" + getProducer() + "'" +
            ", model='" + getModel() + "'" +
            ", year=" + getYear() +
            ", color='" + getColor() + "'" +
            ", decals='" + getDecals() + "'" +
            ", spoilers='" + getSpoilers() + "'" +
            ", glass='" + getGlass() + "'" +
            ", chassisSerial='" + getChassisSerial() + "'" +
            ", motorSerial='" + getMotorSerial() + "'" +
            ", vehicleLicenseSerial='" + getVehicleLicenseSerial() + "'" +
            ", vehicleLicenseType=" + getVehicleLicenseType() +
            ", vehicleType=" + getVehicleType() +
            ", wanted=" + getWanted() +
            ", licenseRevoked=" + getLicenseRevoked() +
            ", licenseExpired=" + getLicenseExpired() +
            ", jurisdiction=" + getJurisdiction() +
            ", updated='" + getUpdated() + "'" +
            ", fuelType='" + getFuelType() + "'" +
            ", motorCc=" + getMotorCc() +
            ", licenseIssued='" + getLicenseIssued() + "'" +
            ", licenseExpires='" + getLicenseExpires() + "'" +
            ", wtKg=" + getWtKg() +
            ", passengers=" + getPassengers() +
            ", tractorParts=" + getTractorParts() +
            ", extraSizePercent=" + getExtraSizePercent() +
            ", wtExtra=" + getWtExtra() +
            ", bikeData='" + getBikeData() + "'" +
            ", wantedBy=" + getWantedBy() +
            ", wantedFor='" + getWantedFor() + "'" +
            ", office=" + getOffice() +
            ", statusName='" + getStatusName() + "'" +
            ", stolen='" + getStolen() + "'" +
            "}";
    }
}
