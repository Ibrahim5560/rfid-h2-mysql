package com.isoft.rfid.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * VehicleOwner (vehicle_owner) entity.\n @author Ibrahim Mohamed.
 */
@Entity
@Table(name = "vehicle_owner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleOwner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "citizen_serial")
    private String citizenSerial;

    @Column(name = "passport_serial")
    private String passportSerial;

    @Column(name = "entity_serial")
    private String entitySerial;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "grand_name")
    private String grandName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "aka")
    private String aka;

    @Column(name = "dob")
    private Instant dob;

    @Column(name = "gender")
    private String gender;

    @Column(name = "driver_license_serial")
    private String driverLicenseSerial;

    @Column(name = "driver_license_type")
    private Long driverLicenseType;

    @Column(name = "jurisdiction")
    private Long jurisdiction;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "account")
    private Double account;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public VehicleOwner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCitizenSerial() {
        return this.citizenSerial;
    }

    public VehicleOwner citizenSerial(String citizenSerial) {
        this.setCitizenSerial(citizenSerial);
        return this;
    }

    public void setCitizenSerial(String citizenSerial) {
        this.citizenSerial = citizenSerial;
    }

    public String getPassportSerial() {
        return this.passportSerial;
    }

    public VehicleOwner passportSerial(String passportSerial) {
        this.setPassportSerial(passportSerial);
        return this;
    }

    public void setPassportSerial(String passportSerial) {
        this.passportSerial = passportSerial;
    }

    public String getEntitySerial() {
        return this.entitySerial;
    }

    public VehicleOwner entitySerial(String entitySerial) {
        this.setEntitySerial(entitySerial);
        return this;
    }

    public void setEntitySerial(String entitySerial) {
        this.entitySerial = entitySerial;
    }

    public String getFullName() {
        return this.fullName;
    }

    public VehicleOwner fullName(String fullName) {
        this.setFullName(fullName);
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public VehicleOwner firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public VehicleOwner middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getGrandName() {
        return this.grandName;
    }

    public VehicleOwner grandName(String grandName) {
        this.setGrandName(grandName);
        return this;
    }

    public void setGrandName(String grandName) {
        this.grandName = grandName;
    }

    public String getSurname() {
        return this.surname;
    }

    public VehicleOwner surname(String surname) {
        this.setSurname(surname);
        return this;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAka() {
        return this.aka;
    }

    public VehicleOwner aka(String aka) {
        this.setAka(aka);
        return this;
    }

    public void setAka(String aka) {
        this.aka = aka;
    }

    public Instant getDob() {
        return this.dob;
    }

    public VehicleOwner dob(Instant dob) {
        this.setDob(dob);
        return this;
    }

    public void setDob(Instant dob) {
        this.dob = dob;
    }

    public String getGender() {
        return this.gender;
    }

    public VehicleOwner gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDriverLicenseSerial() {
        return this.driverLicenseSerial;
    }

    public VehicleOwner driverLicenseSerial(String driverLicenseSerial) {
        this.setDriverLicenseSerial(driverLicenseSerial);
        return this;
    }

    public void setDriverLicenseSerial(String driverLicenseSerial) {
        this.driverLicenseSerial = driverLicenseSerial;
    }

    public Long getDriverLicenseType() {
        return this.driverLicenseType;
    }

    public VehicleOwner driverLicenseType(Long driverLicenseType) {
        this.setDriverLicenseType(driverLicenseType);
        return this;
    }

    public void setDriverLicenseType(Long driverLicenseType) {
        this.driverLicenseType = driverLicenseType;
    }

    public Long getJurisdiction() {
        return this.jurisdiction;
    }

    public VehicleOwner jurisdiction(Long jurisdiction) {
        this.setJurisdiction(jurisdiction);
        return this;
    }

    public void setJurisdiction(Long jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public VehicleOwner updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Double getAccount() {
        return this.account;
    }

    public VehicleOwner account(Double account) {
        this.setAccount(account);
        return this;
    }

    public void setAccount(Double account) {
        this.account = account;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VehicleOwner)) {
            return false;
        }
        return id != null && id.equals(((VehicleOwner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleOwner{" +
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
