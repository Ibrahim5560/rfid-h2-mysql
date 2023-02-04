package com.isoft.rfid.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * TollSubscription (toll_subscription) entity.\n @author Ibrahim Mohamed.
 */
@Entity
@Table(name = "toll_subscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "date_time_from", nullable = false)
    private Instant dateTimeFrom;

    @NotNull
    @Column(name = "date_time_to", nullable = false)
    private Instant dateTimeTo;

    @Column(name = "vehicle")
    private Integer vehicle;

    @Column(name = "vehicle_owner")
    private Integer vehicleOwner;

    @NotNull
    @Column(name = "active", nullable = false)
    private Integer active;

    @NotNull
    @Column(name = "updated", nullable = false)
    private Instant updated;

    @OneToMany(mappedBy = "tollSubscription")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tollSubscription" }, allowSetters = true)
    private Set<TollPackage> tollPackages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TollSubscription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTimeFrom() {
        return this.dateTimeFrom;
    }

    public TollSubscription dateTimeFrom(Instant dateTimeFrom) {
        this.setDateTimeFrom(dateTimeFrom);
        return this;
    }

    public void setDateTimeFrom(Instant dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Instant getDateTimeTo() {
        return this.dateTimeTo;
    }

    public TollSubscription dateTimeTo(Instant dateTimeTo) {
        this.setDateTimeTo(dateTimeTo);
        return this;
    }

    public void setDateTimeTo(Instant dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public Integer getVehicle() {
        return this.vehicle;
    }

    public TollSubscription vehicle(Integer vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleOwner() {
        return this.vehicleOwner;
    }

    public TollSubscription vehicleOwner(Integer vehicleOwner) {
        this.setVehicleOwner(vehicleOwner);
        return this;
    }

    public void setVehicleOwner(Integer vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
    }

    public Integer getActive() {
        return this.active;
    }

    public TollSubscription active(Integer active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public TollSubscription updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Set<TollPackage> getTollPackages() {
        return this.tollPackages;
    }

    public void setTollPackages(Set<TollPackage> tollPackages) {
        if (this.tollPackages != null) {
            this.tollPackages.forEach(i -> i.setTollSubscription(null));
        }
        if (tollPackages != null) {
            tollPackages.forEach(i -> i.setTollSubscription(this));
        }
        this.tollPackages = tollPackages;
    }

    public TollSubscription tollPackages(Set<TollPackage> tollPackages) {
        this.setTollPackages(tollPackages);
        return this;
    }

    public TollSubscription addTollPackage(TollPackage tollPackage) {
        this.tollPackages.add(tollPackage);
        tollPackage.setTollSubscription(this);
        return this;
    }

    public TollSubscription removeTollPackage(TollPackage tollPackage) {
        this.tollPackages.remove(tollPackage);
        tollPackage.setTollSubscription(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TollSubscription)) {
            return false;
        }
        return id != null && id.equals(((TollSubscription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollSubscription{" +
            "id=" + getId() +
            ", dateTimeFrom='" + getDateTimeFrom() + "'" +
            ", dateTimeTo='" + getDateTimeTo() + "'" +
            ", vehicle=" + getVehicle() +
            ", vehicleOwner=" + getVehicleOwner() +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
