package com.isoft.rfid.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Gantry (gantry) entity.\n @author Ibrahim Mohamed.
 */
@Entity
@Table(name = "gantry")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gantry implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "route")
    private Integer route;

    @Column(name = "lanes")
    private Integer lanes;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "lat")
    private Double lat;

    @Column(name = "jurisdiction")
    private Integer jurisdiction;

    @Column(name = "gantry_type")
    private Integer gantryType;

    @Column(name = "gantry_set")
    private Integer gantrySet;

    @Column(name = "active")
    private Integer active;

    @Column(name = "password")
    private String password;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "ip")
    private String ip;

    @NotNull
    @Column(name = "toll", nullable = false)
    private Double toll;

    @NotNull
    @Column(name = "passage_times", nullable = false)
    private Integer passageTimes;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gantry id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Gantry name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRoute() {
        return this.route;
    }

    public Gantry route(Integer route) {
        this.setRoute(route);
        return this;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public Integer getLanes() {
        return this.lanes;
    }

    public Gantry lanes(Integer lanes) {
        this.setLanes(lanes);
        return this;
    }

    public void setLanes(Integer lanes) {
        this.lanes = lanes;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Gantry longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLat() {
        return this.lat;
    }

    public Gantry lat(Double lat) {
        this.setLat(lat);
        return this;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getJurisdiction() {
        return this.jurisdiction;
    }

    public Gantry jurisdiction(Integer jurisdiction) {
        this.setJurisdiction(jurisdiction);
        return this;
    }

    public void setJurisdiction(Integer jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public Integer getGantryType() {
        return this.gantryType;
    }

    public Gantry gantryType(Integer gantryType) {
        this.setGantryType(gantryType);
        return this;
    }

    public void setGantryType(Integer gantryType) {
        this.gantryType = gantryType;
    }

    public Integer getGantrySet() {
        return this.gantrySet;
    }

    public Gantry gantrySet(Integer gantrySet) {
        this.setGantrySet(gantrySet);
        return this;
    }

    public void setGantrySet(Integer gantrySet) {
        this.gantrySet = gantrySet;
    }

    public Integer getActive() {
        return this.active;
    }

    public Gantry active(Integer active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getPassword() {
        return this.password;
    }

    public Gantry password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public Gantry updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public String getIp() {
        return this.ip;
    }

    public Gantry ip(String ip) {
        this.setIp(ip);
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Double getToll() {
        return this.toll;
    }

    public Gantry toll(Double toll) {
        this.setToll(toll);
        return this;
    }

    public void setToll(Double toll) {
        this.toll = toll;
    }

    public Integer getPassageTimes() {
        return this.passageTimes;
    }

    public Gantry passageTimes(Integer passageTimes) {
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
        if (!(o instanceof Gantry)) {
            return false;
        }
        return id != null && id.equals(((Gantry) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Gantry{" +
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
