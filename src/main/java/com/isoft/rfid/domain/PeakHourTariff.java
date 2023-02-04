package com.isoft.rfid.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * PeakHourTariff (peak_hour_tariff) entity.\n @author Ibrahim Mohamed.
 */
@Entity
@Table(name = "peak_hour_tariff")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeakHourTariff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "peak_hour_from", nullable = false)
    private Instant peakHourFrom;

    @NotNull
    @Column(name = "peak_hour_to", nullable = false)
    private Instant peakHourTo;

    @Column(name = "gantry")
    private Integer gantry;

    @NotNull
    @Column(name = "gantry_toll", nullable = false)
    private Double gantryToll;

    @NotNull
    @Column(name = "active", nullable = false)
    private Integer active;

    @NotNull
    @Column(name = "updated", nullable = false)
    private Instant updated;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PeakHourTariff id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPeakHourFrom() {
        return this.peakHourFrom;
    }

    public PeakHourTariff peakHourFrom(Instant peakHourFrom) {
        this.setPeakHourFrom(peakHourFrom);
        return this;
    }

    public void setPeakHourFrom(Instant peakHourFrom) {
        this.peakHourFrom = peakHourFrom;
    }

    public Instant getPeakHourTo() {
        return this.peakHourTo;
    }

    public PeakHourTariff peakHourTo(Instant peakHourTo) {
        this.setPeakHourTo(peakHourTo);
        return this;
    }

    public void setPeakHourTo(Instant peakHourTo) {
        this.peakHourTo = peakHourTo;
    }

    public Integer getGantry() {
        return this.gantry;
    }

    public PeakHourTariff gantry(Integer gantry) {
        this.setGantry(gantry);
        return this;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Double getGantryToll() {
        return this.gantryToll;
    }

    public PeakHourTariff gantryToll(Double gantryToll) {
        this.setGantryToll(gantryToll);
        return this;
    }

    public void setGantryToll(Double gantryToll) {
        this.gantryToll = gantryToll;
    }

    public Integer getActive() {
        return this.active;
    }

    public PeakHourTariff active(Integer active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public PeakHourTariff updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeakHourTariff)) {
            return false;
        }
        return id != null && id.equals(((PeakHourTariff) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeakHourTariff{" +
            "id=" + getId() +
            ", peakHourFrom='" + getPeakHourFrom() + "'" +
            ", peakHourTo='" + getPeakHourTo() + "'" +
            ", gantry=" + getGantry() +
            ", gantryToll=" + getGantryToll() +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
