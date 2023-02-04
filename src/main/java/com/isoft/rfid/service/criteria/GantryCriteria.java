package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.Gantry} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.GantryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /gantries?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GantryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter route;

    private IntegerFilter lanes;

    private DoubleFilter longitude;

    private DoubleFilter lat;

    private IntegerFilter jurisdiction;

    private IntegerFilter gantryType;

    private IntegerFilter gantrySet;

    private IntegerFilter active;

    private StringFilter password;

    private InstantFilter updated;

    private StringFilter ip;

    private DoubleFilter toll;

    private IntegerFilter passageTimes;

    private Boolean distinct;

    public GantryCriteria() {}

    public GantryCriteria(GantryCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.route = other.route == null ? null : other.route.copy();
        this.lanes = other.lanes == null ? null : other.lanes.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.lat = other.lat == null ? null : other.lat.copy();
        this.jurisdiction = other.jurisdiction == null ? null : other.jurisdiction.copy();
        this.gantryType = other.gantryType == null ? null : other.gantryType.copy();
        this.gantrySet = other.gantrySet == null ? null : other.gantrySet.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.password = other.password == null ? null : other.password.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.ip = other.ip == null ? null : other.ip.copy();
        this.toll = other.toll == null ? null : other.toll.copy();
        this.passageTimes = other.passageTimes == null ? null : other.passageTimes.copy();
        this.distinct = other.distinct;
    }

    @Override
    public GantryCriteria copy() {
        return new GantryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getRoute() {
        return route;
    }

    public IntegerFilter route() {
        if (route == null) {
            route = new IntegerFilter();
        }
        return route;
    }

    public void setRoute(IntegerFilter route) {
        this.route = route;
    }

    public IntegerFilter getLanes() {
        return lanes;
    }

    public IntegerFilter lanes() {
        if (lanes == null) {
            lanes = new IntegerFilter();
        }
        return lanes;
    }

    public void setLanes(IntegerFilter lanes) {
        this.lanes = lanes;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            longitude = new DoubleFilter();
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public DoubleFilter getLat() {
        return lat;
    }

    public DoubleFilter lat() {
        if (lat == null) {
            lat = new DoubleFilter();
        }
        return lat;
    }

    public void setLat(DoubleFilter lat) {
        this.lat = lat;
    }

    public IntegerFilter getJurisdiction() {
        return jurisdiction;
    }

    public IntegerFilter jurisdiction() {
        if (jurisdiction == null) {
            jurisdiction = new IntegerFilter();
        }
        return jurisdiction;
    }

    public void setJurisdiction(IntegerFilter jurisdiction) {
        this.jurisdiction = jurisdiction;
    }

    public IntegerFilter getGantryType() {
        return gantryType;
    }

    public IntegerFilter gantryType() {
        if (gantryType == null) {
            gantryType = new IntegerFilter();
        }
        return gantryType;
    }

    public void setGantryType(IntegerFilter gantryType) {
        this.gantryType = gantryType;
    }

    public IntegerFilter getGantrySet() {
        return gantrySet;
    }

    public IntegerFilter gantrySet() {
        if (gantrySet == null) {
            gantrySet = new IntegerFilter();
        }
        return gantrySet;
    }

    public void setGantrySet(IntegerFilter gantrySet) {
        this.gantrySet = gantrySet;
    }

    public IntegerFilter getActive() {
        return active;
    }

    public IntegerFilter active() {
        if (active == null) {
            active = new IntegerFilter();
        }
        return active;
    }

    public void setActive(IntegerFilter active) {
        this.active = active;
    }

    public StringFilter getPassword() {
        return password;
    }

    public StringFilter password() {
        if (password == null) {
            password = new StringFilter();
        }
        return password;
    }

    public void setPassword(StringFilter password) {
        this.password = password;
    }

    public InstantFilter getUpdated() {
        return updated;
    }

    public InstantFilter updated() {
        if (updated == null) {
            updated = new InstantFilter();
        }
        return updated;
    }

    public void setUpdated(InstantFilter updated) {
        this.updated = updated;
    }

    public StringFilter getIp() {
        return ip;
    }

    public StringFilter ip() {
        if (ip == null) {
            ip = new StringFilter();
        }
        return ip;
    }

    public void setIp(StringFilter ip) {
        this.ip = ip;
    }

    public DoubleFilter getToll() {
        return toll;
    }

    public DoubleFilter toll() {
        if (toll == null) {
            toll = new DoubleFilter();
        }
        return toll;
    }

    public void setToll(DoubleFilter toll) {
        this.toll = toll;
    }

    public IntegerFilter getPassageTimes() {
        return passageTimes;
    }

    public IntegerFilter passageTimes() {
        if (passageTimes == null) {
            passageTimes = new IntegerFilter();
        }
        return passageTimes;
    }

    public void setPassageTimes(IntegerFilter passageTimes) {
        this.passageTimes = passageTimes;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final GantryCriteria that = (GantryCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(route, that.route) &&
            Objects.equals(lanes, that.lanes) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(lat, that.lat) &&
            Objects.equals(jurisdiction, that.jurisdiction) &&
            Objects.equals(gantryType, that.gantryType) &&
            Objects.equals(gantrySet, that.gantrySet) &&
            Objects.equals(active, that.active) &&
            Objects.equals(password, that.password) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(ip, that.ip) &&
            Objects.equals(toll, that.toll) &&
            Objects.equals(passageTimes, that.passageTimes) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            route,
            lanes,
            longitude,
            lat,
            jurisdiction,
            gantryType,
            gantrySet,
            active,
            password,
            updated,
            ip,
            toll,
            passageTimes,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GantryCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (route != null ? "route=" + route + ", " : "") +
            (lanes != null ? "lanes=" + lanes + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (lat != null ? "lat=" + lat + ", " : "") +
            (jurisdiction != null ? "jurisdiction=" + jurisdiction + ", " : "") +
            (gantryType != null ? "gantryType=" + gantryType + ", " : "") +
            (gantrySet != null ? "gantrySet=" + gantrySet + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (password != null ? "password=" + password + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (ip != null ? "ip=" + ip + ", " : "") +
            (toll != null ? "toll=" + toll + ", " : "") +
            (passageTimes != null ? "passageTimes=" + passageTimes + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
