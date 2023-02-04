package com.isoft.rfid.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * TollPackage (toll_package) entity.\n @author Ibrahim Mohamed.
 */
@Entity
@Table(name = "toll_package")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollPackage implements Serializable {

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

    @Column(name = "duration_in_days")
    private Integer durationInDays;

    @Column(name = "gantry")
    private Integer gantry;

    @Column(name = "vehicle_license_type")
    private Integer vehicleLicenseType;

    @NotNull
    @Column(name = "passage_times", nullable = false)
    private Integer passageTimes;

    @NotNull
    @Column(name = "total_fees", nullable = false)
    private Double totalFees;

    @ManyToOne
    @JsonIgnoreProperties(value = { "tollPackages" }, allowSetters = true)
    private TollSubscription tollSubscription;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TollPackage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public TollPackage name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return this.engName;
    }

    public TollPackage engName(String engName) {
        this.setEngName(engName);
        return this;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getCode() {
        return this.code;
    }

    public TollPackage code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getActive() {
        return this.active;
    }

    public TollPackage active(Integer active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public TollPackage updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Integer getDurationInDays() {
        return this.durationInDays;
    }

    public TollPackage durationInDays(Integer durationInDays) {
        this.setDurationInDays(durationInDays);
        return this;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public Integer getGantry() {
        return this.gantry;
    }

    public TollPackage gantry(Integer gantry) {
        this.setGantry(gantry);
        return this;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Integer getVehicleLicenseType() {
        return this.vehicleLicenseType;
    }

    public TollPackage vehicleLicenseType(Integer vehicleLicenseType) {
        this.setVehicleLicenseType(vehicleLicenseType);
        return this;
    }

    public void setVehicleLicenseType(Integer vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public Integer getPassageTimes() {
        return this.passageTimes;
    }

    public TollPackage passageTimes(Integer passageTimes) {
        this.setPassageTimes(passageTimes);
        return this;
    }

    public void setPassageTimes(Integer passageTimes) {
        this.passageTimes = passageTimes;
    }

    public Double getTotalFees() {
        return this.totalFees;
    }

    public TollPackage totalFees(Double totalFees) {
        this.setTotalFees(totalFees);
        return this;
    }

    public void setTotalFees(Double totalFees) {
        this.totalFees = totalFees;
    }

    public TollSubscription getTollSubscription() {
        return this.tollSubscription;
    }

    public void setTollSubscription(TollSubscription tollSubscription) {
        this.tollSubscription = tollSubscription;
    }

    public TollPackage tollSubscription(TollSubscription tollSubscription) {
        this.setTollSubscription(tollSubscription);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TollPackage)) {
            return false;
        }
        return id != null && id.equals(((TollPackage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollPackage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", code='" + getCode() + "'" +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            ", durationInDays=" + getDurationInDays() +
            ", gantry=" + getGantry() +
            ", vehicleLicenseType=" + getVehicleLicenseType() +
            ", passageTimes=" + getPassageTimes() +
            ", totalFees=" + getTotalFees() +
            "}";
    }
}
