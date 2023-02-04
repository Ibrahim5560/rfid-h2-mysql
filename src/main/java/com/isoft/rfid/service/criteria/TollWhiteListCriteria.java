package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.TollWhiteList} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.TollWhiteListResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /toll-white-lists?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollWhiteListCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter engName;

    private StringFilter code;

    private IntegerFilter active;

    private InstantFilter updated;

    private IntegerFilter gantry;

    private IntegerFilter vehicleLicenseType;

    private IntegerFilter vehicle;

    private IntegerFilter vehicleOwner;

    private IntegerFilter passageTimes;

    private Boolean distinct;

    public TollWhiteListCriteria() {}

    public TollWhiteListCriteria(TollWhiteListCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.engName = other.engName == null ? null : other.engName.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.gantry = other.gantry == null ? null : other.gantry.copy();
        this.vehicleLicenseType = other.vehicleLicenseType == null ? null : other.vehicleLicenseType.copy();
        this.vehicle = other.vehicle == null ? null : other.vehicle.copy();
        this.vehicleOwner = other.vehicleOwner == null ? null : other.vehicleOwner.copy();
        this.passageTimes = other.passageTimes == null ? null : other.passageTimes.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TollWhiteListCriteria copy() {
        return new TollWhiteListCriteria(this);
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

    public StringFilter getEngName() {
        return engName;
    }

    public StringFilter engName() {
        if (engName == null) {
            engName = new StringFilter();
        }
        return engName;
    }

    public void setEngName(StringFilter engName) {
        this.engName = engName;
    }

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public IntegerFilter getGantry() {
        return gantry;
    }

    public IntegerFilter gantry() {
        if (gantry == null) {
            gantry = new IntegerFilter();
        }
        return gantry;
    }

    public void setGantry(IntegerFilter gantry) {
        this.gantry = gantry;
    }

    public IntegerFilter getVehicleLicenseType() {
        return vehicleLicenseType;
    }

    public IntegerFilter vehicleLicenseType() {
        if (vehicleLicenseType == null) {
            vehicleLicenseType = new IntegerFilter();
        }
        return vehicleLicenseType;
    }

    public void setVehicleLicenseType(IntegerFilter vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public IntegerFilter getVehicle() {
        return vehicle;
    }

    public IntegerFilter vehicle() {
        if (vehicle == null) {
            vehicle = new IntegerFilter();
        }
        return vehicle;
    }

    public void setVehicle(IntegerFilter vehicle) {
        this.vehicle = vehicle;
    }

    public IntegerFilter getVehicleOwner() {
        return vehicleOwner;
    }

    public IntegerFilter vehicleOwner() {
        if (vehicleOwner == null) {
            vehicleOwner = new IntegerFilter();
        }
        return vehicleOwner;
    }

    public void setVehicleOwner(IntegerFilter vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
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
        final TollWhiteListCriteria that = (TollWhiteListCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(engName, that.engName) &&
            Objects.equals(code, that.code) &&
            Objects.equals(active, that.active) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(gantry, that.gantry) &&
            Objects.equals(vehicleLicenseType, that.vehicleLicenseType) &&
            Objects.equals(vehicle, that.vehicle) &&
            Objects.equals(vehicleOwner, that.vehicleOwner) &&
            Objects.equals(passageTimes, that.passageTimes) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            name,
            engName,
            code,
            active,
            updated,
            gantry,
            vehicleLicenseType,
            vehicle,
            vehicleOwner,
            passageTimes,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollWhiteListCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (engName != null ? "engName=" + engName + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (gantry != null ? "gantry=" + gantry + ", " : "") +
            (vehicleLicenseType != null ? "vehicleLicenseType=" + vehicleLicenseType + ", " : "") +
            (vehicle != null ? "vehicle=" + vehicle + ", " : "") +
            (vehicleOwner != null ? "vehicleOwner=" + vehicleOwner + ", " : "") +
            (passageTimes != null ? "passageTimes=" + passageTimes + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
