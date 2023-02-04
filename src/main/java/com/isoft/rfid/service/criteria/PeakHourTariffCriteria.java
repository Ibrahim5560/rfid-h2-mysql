package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.PeakHourTariff} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.PeakHourTariffResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /peak-hour-tariffs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeakHourTariffCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter peakHourFrom;

    private InstantFilter peakHourTo;

    private IntegerFilter gantry;

    private DoubleFilter gantryToll;

    private IntegerFilter active;

    private InstantFilter updated;

    private Boolean distinct;

    public PeakHourTariffCriteria() {}

    public PeakHourTariffCriteria(PeakHourTariffCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.peakHourFrom = other.peakHourFrom == null ? null : other.peakHourFrom.copy();
        this.peakHourTo = other.peakHourTo == null ? null : other.peakHourTo.copy();
        this.gantry = other.gantry == null ? null : other.gantry.copy();
        this.gantryToll = other.gantryToll == null ? null : other.gantryToll.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.distinct = other.distinct;
    }

    @Override
    public PeakHourTariffCriteria copy() {
        return new PeakHourTariffCriteria(this);
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

    public InstantFilter getPeakHourFrom() {
        return peakHourFrom;
    }

    public InstantFilter peakHourFrom() {
        if (peakHourFrom == null) {
            peakHourFrom = new InstantFilter();
        }
        return peakHourFrom;
    }

    public void setPeakHourFrom(InstantFilter peakHourFrom) {
        this.peakHourFrom = peakHourFrom;
    }

    public InstantFilter getPeakHourTo() {
        return peakHourTo;
    }

    public InstantFilter peakHourTo() {
        if (peakHourTo == null) {
            peakHourTo = new InstantFilter();
        }
        return peakHourTo;
    }

    public void setPeakHourTo(InstantFilter peakHourTo) {
        this.peakHourTo = peakHourTo;
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

    public DoubleFilter getGantryToll() {
        return gantryToll;
    }

    public DoubleFilter gantryToll() {
        if (gantryToll == null) {
            gantryToll = new DoubleFilter();
        }
        return gantryToll;
    }

    public void setGantryToll(DoubleFilter gantryToll) {
        this.gantryToll = gantryToll;
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
        final PeakHourTariffCriteria that = (PeakHourTariffCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(peakHourFrom, that.peakHourFrom) &&
            Objects.equals(peakHourTo, that.peakHourTo) &&
            Objects.equals(gantry, that.gantry) &&
            Objects.equals(gantryToll, that.gantryToll) &&
            Objects.equals(active, that.active) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, peakHourFrom, peakHourTo, gantry, gantryToll, active, updated, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeakHourTariffCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (peakHourFrom != null ? "peakHourFrom=" + peakHourFrom + ", " : "") +
            (peakHourTo != null ? "peakHourTo=" + peakHourTo + ", " : "") +
            (gantry != null ? "gantry=" + gantry + ", " : "") +
            (gantryToll != null ? "gantryToll=" + gantryToll + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
