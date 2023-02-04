package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.TollSubscription} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.TollSubscriptionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /toll-subscriptions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollSubscriptionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dateTimeFrom;

    private InstantFilter dateTimeTo;

    private IntegerFilter vehicle;

    private IntegerFilter vehicleOwner;

    private IntegerFilter active;

    private InstantFilter updated;

    private LongFilter tollPackageId;

    private Boolean distinct;

    public TollSubscriptionCriteria() {}

    public TollSubscriptionCriteria(TollSubscriptionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dateTimeFrom = other.dateTimeFrom == null ? null : other.dateTimeFrom.copy();
        this.dateTimeTo = other.dateTimeTo == null ? null : other.dateTimeTo.copy();
        this.vehicle = other.vehicle == null ? null : other.vehicle.copy();
        this.vehicleOwner = other.vehicleOwner == null ? null : other.vehicleOwner.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.tollPackageId = other.tollPackageId == null ? null : other.tollPackageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TollSubscriptionCriteria copy() {
        return new TollSubscriptionCriteria(this);
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

    public InstantFilter getDateTimeFrom() {
        return dateTimeFrom;
    }

    public InstantFilter dateTimeFrom() {
        if (dateTimeFrom == null) {
            dateTimeFrom = new InstantFilter();
        }
        return dateTimeFrom;
    }

    public void setDateTimeFrom(InstantFilter dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public InstantFilter getDateTimeTo() {
        return dateTimeTo;
    }

    public InstantFilter dateTimeTo() {
        if (dateTimeTo == null) {
            dateTimeTo = new InstantFilter();
        }
        return dateTimeTo;
    }

    public void setDateTimeTo(InstantFilter dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
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

    public LongFilter getTollPackageId() {
        return tollPackageId;
    }

    public LongFilter tollPackageId() {
        if (tollPackageId == null) {
            tollPackageId = new LongFilter();
        }
        return tollPackageId;
    }

    public void setTollPackageId(LongFilter tollPackageId) {
        this.tollPackageId = tollPackageId;
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
        final TollSubscriptionCriteria that = (TollSubscriptionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dateTimeFrom, that.dateTimeFrom) &&
            Objects.equals(dateTimeTo, that.dateTimeTo) &&
            Objects.equals(vehicle, that.vehicle) &&
            Objects.equals(vehicleOwner, that.vehicleOwner) &&
            Objects.equals(active, that.active) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(tollPackageId, that.tollPackageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTimeFrom, dateTimeTo, vehicle, vehicleOwner, active, updated, tollPackageId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollSubscriptionCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dateTimeFrom != null ? "dateTimeFrom=" + dateTimeFrom + ", " : "") +
            (dateTimeTo != null ? "dateTimeTo=" + dateTimeTo + ", " : "") +
            (vehicle != null ? "vehicle=" + vehicle + ", " : "") +
            (vehicleOwner != null ? "vehicleOwner=" + vehicleOwner + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (tollPackageId != null ? "tollPackageId=" + tollPackageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
