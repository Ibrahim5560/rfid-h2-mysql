package com.isoft.rfid.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Vehicle (vehicle) entity.\n@author Ibrahim Mohamed.
 */
@Entity
@Table(name = "vehicle")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Vehicle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "govid")
    private String govid;

    @NotNull
    @Column(name = "plate", nullable = false)
    private String plate;

    @NotNull
    @Column(name = "rfid", nullable = false, unique = true)
    private String rfid;

    @NotNull
    @Column(name = "driver", nullable = false)
    private Long driver;

    @Column(name = "producer")
    private String producer;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private Long year;

    @Column(name = "color")
    private String color;

    @Column(name = "decals")
    private String decals;

    @Column(name = "spoilers")
    private String spoilers;

    @Column(name = "glass")
    private String glass;

    @Column(name = "chassis_serial")
    private String chassisSerial;

    @Column(name = "motor_serial")
    private String motorSerial;

    @Column(name = "vehicle_license_serial")
    private String vehicleLicenseSerial;

    @NotNull
    @Column(name = "vehicle_license_type", nullable = false)
    private Long vehicleLicenseType;

    @NotNull
    @Column(name = "vehicle_type", nullable = false)
    private Long vehicleType;

    @Column(name = "wanted")
    private Integer wanted;

    @Column(name = "license_revoked")
    private Integer licenseRevoked;

    @Column(name = "license_expired")
    private Integer licenseExpired;

    @NotNull
    @Column(name = "jurisdiction", nullable = false)
    private Long jurisdiction;

    @NotNull
    @Column(name = "updated", nullable = false)
    private Instant updated;

    @Column(name = "fuel_type")
    private String fuelType;

    @Column(name = "motor_cc")
    private Long motorCc;

    @Column(name = "license_issued")
    private Instant licenseIssued;

    @Column(name = "license_expires")
    private Instant licenseExpires;

    @Column(name = "wt_kg")
    private Long wtKg;

    @Column(name = "passengers")
    private Integer passengers;

    @Column(name = "tractor_parts")
    private Integer tractorParts;

    @Column(name = "extra_size_percent")
    private Integer extraSizePercent;

    @Column(name = "wt_extra")
    private Long wtExtra;

    @Column(name = "bike_data")
    private String bikeData;

    @Column(name = "wanted_by")
    private Long wantedBy;

    @Column(name = "wanted_for")
    private String wantedFor;

    @Column(name = "office")
    private Long office;

    @Column(name = "status_name")
    private String statusName;

    @Column(name = "stolen")
    private Instant stolen;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Vehicle id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGovid() {
        return this.govid;
    }

    public Vehicle govid(String govid) {
        this.setGovid(govid);
        return this;
    }

    public void setGovid(String govid) {
        this.govid = govid;
    }

    public String getPlate() {
        return this.plate;
    }

    public Vehicle plate(String plate) {
        this.setPlate(plate);
        return this;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getRfid() {
        return this.rfid;
    }

    public Vehicle rfid(String rfid) {
        this.setRfid(rfid);
        return this;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public Long getDriver() {
        return this.driver;
    }

    public Vehicle driver(Long driver) {
        this.setDriver(driver);
        return this;
    }

    public void setDriver(Long driver) {
        this.driver = driver;
    }

    public String getProducer() {
        return this.producer;
    }

    public Vehicle producer(String producer) {
        this.setProducer(producer);
        return this;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getModel() {
        return this.model;
    }

    public Vehicle model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getYear() {
        return this.year;
    }

    public Vehicle year(Long year) {
        this.setYear(year);
        return this;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public String getColor() {
        return this.color;
    }

    public Vehicle color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDecals() {
        return this.decals;
    }

    public Vehicle decals(String decals) {
        this.setDecals(decals);
        return this;
    }

    public void setDecals(String decals) {
        this.decals = decals;
    }

    public String getSpoilers() {
        return this.spoilers;
    }

    public Vehicle spoilers(String spoilers) {
        this.setSpoilers(spoilers);
        return this;
    }

    public void setSpoilers(String spoilers) {
        this.spoilers = spoilers;
    }

    public String getGlass() {
        return this.glass;
    }

    public Vehicle glass(String glass) {
        this.setGlass(glass);
        return this;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getChassisSerial() {
        return this.chassisSerial;
    }

    public Vehicle chassisSerial(String chassisSerial) {
        this.setChassisSerial(chassisSerial);
        return this;
    }

    public void setChassisSerial(String chassisSerial) {
        this.chassisSerial = chassisSerial;
    }

    public String getMotorSerial() {
        return this.motorSerial;
    }

    public Vehicle motorSerial(String motorSerial) {
        this.setMotorSerial(motorSerial);
        return this;
    }

    public void setMotorSerial(String motorSerial) {
        this.motorSerial = motorSerial;
    }

    public String getVehicleLicenseSerial() {
        return this.vehicleLicenseSerial;
    }

    public Vehicle vehicleLicenseSerial(String vehicleLicenseSerial) {
        this.setVehicleLicenseSerial(vehicleLicenseSerial);
        return this;
    }

    public void setVehicleLicenseSerial(String vehicleLicenseSerial) {
        this.vehicleLicenseSerial = vehicleLicenseSerial;
    }

    public Long getVehicleLicenseType() {
        return this.vehicleLicenseType;
    }

    public Vehicle vehicleLicenseType(Long vehicleLicenseType) {
        this.setVehicleLicenseType(vehicleLicenseType);
        return this;
    }

    public void setVehicleLicenseType(Long vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public Long getVehicleType() {
        return this.vehicleType;
    }

    public Vehicle vehicleType(Long vehicleType) {
        this.setVehicleType(vehicleType);
        return this;
    }

    public void setVehicleType(Long vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Integer getWanted() {
        return this.wanted;
    }

    public Vehicle wanted(Integer wanted) {
        this.setWanted(wanted);
        return this;
    }

    public void setWanted(Integer wanted) {
        this.wanted = wanted;
    }

    public Integer getLicenseRevoked() {
        return this.licenseRevoked;
    }

    public Vehicle licenseRevoked(Integer licenseRevoked) {
        this.setLicenseRevoked(licenseRevoked);
        return this;
    }

    public void setLicenseRevoked(Integer licenseRevoked) {
        this.licenseRevoked = licenseRevoked;
    }

    public Integer getLicenseExpired() {
        return this.licenseExpired;
    }

    public Vehicle licenseExpired(Integer licenseExpired) {
        this.setLicenseExpired(licenseExpired);
        return this;
    }

    public void setLicenseExpired(Integer licenseExpired) {
        this.licenseExpired = licenseExpired;
    }

    public Long getJurisdiction() {
        return this.jurisdiction;
    }

    public Vehicle jurisdiction(Long jurisdiction) {
        this.setJurisdiction(jurisdiction);
        return this;
    }

    public void setJurisdiction(Long jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public Vehicle updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public String getFuelType() {
        return this.fuelType;
    }

    public Vehicle fuelType(String fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public Long getMotorCc() {
        return this.motorCc;
    }

    public Vehicle motorCc(Long motorCc) {
        this.setMotorCc(motorCc);
        return this;
    }

    public void setMotorCc(Long motorCc) {
        this.motorCc = motorCc;
    }

    public Instant getLicenseIssued() {
        return this.licenseIssued;
    }

    public Vehicle licenseIssued(Instant licenseIssued) {
        this.setLicenseIssued(licenseIssued);
        return this;
    }

    public void setLicenseIssued(Instant licenseIssued) {
        this.licenseIssued = licenseIssued;
    }

    public Instant getLicenseExpires() {
        return this.licenseExpires;
    }

    public Vehicle licenseExpires(Instant licenseExpires) {
        this.setLicenseExpires(licenseExpires);
        return this;
    }

    public void setLicenseExpires(Instant licenseExpires) {
        this.licenseExpires = licenseExpires;
    }

    public Long getWtKg() {
        return this.wtKg;
    }

    public Vehicle wtKg(Long wtKg) {
        this.setWtKg(wtKg);
        return this;
    }

    public void setWtKg(Long wtKg) {
        this.wtKg = wtKg;
    }

    public Integer getPassengers() {
        return this.passengers;
    }

    public Vehicle passengers(Integer passengers) {
        this.setPassengers(passengers);
        return this;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Integer getTractorParts() {
        return this.tractorParts;
    }

    public Vehicle tractorParts(Integer tractorParts) {
        this.setTractorParts(tractorParts);
        return this;
    }

    public void setTractorParts(Integer tractorParts) {
        this.tractorParts = tractorParts;
    }

    public Integer getExtraSizePercent() {
        return this.extraSizePercent;
    }

    public Vehicle extraSizePercent(Integer extraSizePercent) {
        this.setExtraSizePercent(extraSizePercent);
        return this;
    }

    public void setExtraSizePercent(Integer extraSizePercent) {
        this.extraSizePercent = extraSizePercent;
    }

    public Long getWtExtra() {
        return this.wtExtra;
    }

    public Vehicle wtExtra(Long wtExtra) {
        this.setWtExtra(wtExtra);
        return this;
    }

    public void setWtExtra(Long wtExtra) {
        this.wtExtra = wtExtra;
    }

    public String getBikeData() {
        return this.bikeData;
    }

    public Vehicle bikeData(String bikeData) {
        this.setBikeData(bikeData);
        return this;
    }

    public void setBikeData(String bikeData) {
        this.bikeData = bikeData;
    }

    public Long getWantedBy() {
        return this.wantedBy;
    }

    public Vehicle wantedBy(Long wantedBy) {
        this.setWantedBy(wantedBy);
        return this;
    }

    public void setWantedBy(Long wantedBy) {
        this.wantedBy = wantedBy;
    }

    public String getWantedFor() {
        return this.wantedFor;
    }

    public Vehicle wantedFor(String wantedFor) {
        this.setWantedFor(wantedFor);
        return this;
    }

    public void setWantedFor(String wantedFor) {
        this.wantedFor = wantedFor;
    }

    public Long getOffice() {
        return this.office;
    }

    public Vehicle office(Long office) {
        this.setOffice(office);
        return this;
    }

    public void setOffice(Long office) {
        this.office = office;
    }

    public String getStatusName() {
        return this.statusName;
    }

    public Vehicle statusName(String statusName) {
        this.setStatusName(statusName);
        return this;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Instant getStolen() {
        return this.stolen;
    }

    public Vehicle stolen(Instant stolen) {
        this.setStolen(stolen);
        return this;
    }

    public void setStolen(Instant stolen) {
        this.stolen = stolen;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle)) {
            return false;
        }
        return id != null && id.equals(((Vehicle) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vehicle{" +
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
