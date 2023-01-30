package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.isoft.rfid.domain.VehicleOwner} entity.
 */
@Schema(description = "VehicleOwner (vehicle_owner) entity.\n @author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleOwnerDTO implements Serializable {

    private Long id;

    private String citizenSerial;

    private String passportSerial;

    private String entitySerial;

    private String fullName;

    private String firstName;

    private String middleName;

    private String grandName;

    private String surname;

    private String aka;

    private Instant dob;

    private String gender;

    private String driverLicenseSerial;

    private Long driverLicenseType;

    private Long jurisdiction;

    private Instant updated;

    private Double account;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCitizenSerial() {
        return citizenSerial;
    }

    public void setCitizenSerial(String citizenSerial) {
        this.citizenSerial = citizenSerial;
    }

    public String getPassportSerial() {
        return passportSerial;
    }

    public void setPassportSerial(String passportSerial) {
        this.passportSerial = passportSerial;
    }

    public String getEntitySerial() {
        return entitySerial;
    }

    public void setEntitySerial(String entitySerial) {
        this.entitySerial = entitySerial;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGrandName() {
        return grandName;
    }

    public void setGrandName(String grandName) {
        this.grandName = grandName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAka() {
        return aka;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public Instant getDob() {
        return dob;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDriverLicenseSerial() {
        return driverLicenseSerial;
    }

    public void setDriverLicenseSerial(String driverLicenseSerial) {
        this.driverLicenseSerial = driverLicenseSerial;
    }

    public Long getDriverLicenseType() {
        return driverLicenseType;
    }

    public void setDriverLicenseType(Long driverLicenseType) {
        this.driverLicenseType = driverLicenseType;
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

    public Double getAccount() {
        return account;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleOwnerDTO)) {
            return false;
        }

        VehicleOwnerDTO vehicleOwnerDTO = (VehicleOwnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, vehicleOwnerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleOwnerDTO{" +
            "id=" + getId() +
            ", citizenSerial='" + getCitizenSerial() + "'" +
            ", passportSerial='" + getPassportSerial() + "'" +
            ", entitySerial='" + getEntitySerial() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", grandName='" + getGrandName() + "'" +
            ", surname='" + getSurname() + "'" +
            ", aka='" + getAka() + "'" +
            ", dob='" + getDob() + "'" +
            ", gender='" + getGender() + "'" +
            ", driverLicenseSerial='" + getDriverLicenseSerial() + "'" +
            ", driverLicenseType=" + getDriverLicenseType() +
            ", jurisdiction=" + getJurisdiction() +
            ", updated='" + getUpdated() + "'" +
            ", account=" + getAccount() +
            "}";
    }
}
