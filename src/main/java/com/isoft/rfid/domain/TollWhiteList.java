package com.isoft.rfid.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * TollWhiteList (toll_white_list) entity.\n @author Ibrahim Mohamed.
 */
@Entity
@Table(name = "toll_white_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollWhiteList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "eng_name")
    private String engName;

    @Column(name = "code")
    private String code;

    @NotNull
    @Column(name = "active", nullable = false)
    private Integer active;

    @NotNull
    @Column(name = "updated", nullable = false)
    private Instant updated;

    @Column(name = "gantry")
    private Integer gantry;

    @Column(name = "vehicle_license_type")
    private Integer vehicleLicenseType;

    @Column(name = "vehicle")
    private Integer vehicle;

    @Column(name = "vehicle_owner")
    private Integer vehicleOwner;

    @NotNull
    @Column(name = "passage_times", nullable = false)
    private Integer passageTimes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TollWhiteList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TollWhiteList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return this.engName;
    }

    public TollWhiteList engName(String engName) {
        this.setEngName(engName);
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getCode() {
        return this.code;
    }

    public TollWhiteList code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getActive() {
        return this.active;
    }

    public TollWhiteList active(Integer active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public TollWhiteList updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Integer getGantry() {
        return this.gantry;
    }

    public TollWhiteList gantry(Integer gantry) {
        this.setGantry(gantry);
        return this;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Integer getVehicleLicenseType() {
        return this.vehicleLicenseType;
    }

    public TollWhiteList vehicleLicenseType(Integer vehicleLicenseType) {
        this.setVehicleLicenseType(vehicleLicenseType);
        return this;
    }

    public void setVehicleLicenseType(Integer vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public Integer getVehicle() {
        return this.vehicle;
    }

    public TollWhiteList vehicle(Integer vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleOwner() {
        return this.vehicleOwner;
    }

    public TollWhiteList vehicleOwner(Integer vehicleOwner) {
        this.setVehicleOwner(vehicleOwner);
        return this;
    }

    public void setVehicleOwner(Integer vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }

    public Integer getPassageTimes() {
        return this.passageTimes;
    }

    public TollWhiteList passageTimes(Integer passageTimes) {
        this.setPassageTimes(passageTimes);
        return this;
    }

    public void setPassageTimes(Integer passageTimes) {
        this.passageTimes = passageTimes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TollWhiteList)) {
            return false;
        }
        return id != null && id.equals(((TollWhiteList) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollWhiteList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", code='" + getCode() + "'" +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            ", gantry=" + getGantry() +
            ", vehicleLicenseType=" + getVehicleLicenseType() +
            ", vehicle=" + getVehicle() +
            ", vehicleOwner=" + getVehicleOwner() +
            ", passageTimes=" + getPassageTimes() +
            "}";
    }
}
