package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.VehicleLicenseType} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.VehicleLicenseTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-license-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleLicenseTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private IntegerFilter rank;

    private StringFilter engName;

    private StringFilter code;

    private InstantFilter updated;

    private IntegerFilter active;

    private DoubleFilter gantryToll;

    private Boolean distinct;

    public VehicleLicenseTypeCriteria() {}

    public VehicleLicenseTypeCriteria(VehicleLicenseTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.rank = other.rank == null ? null : other.rank.copy();
        this.engName = other.engName == null ? null : other.engName.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.active = other.active == null ? null : other.active.copy();
        this.gantryToll = other.gantryToll == null ? null : other.gantryToll.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VehicleLicenseTypeCriteria copy() {
        return new VehicleLicenseTypeCriteria(this);
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

    public IntegerFilter getRank() {
        return rank;
    }

    public IntegerFilter rank() {
        if (rank == null) {
            rank = new IntegerFilter();
        }
        return rank;
    }

    public void setRank(IntegerFilter rank) {
        this.rank = rank;
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
        final VehicleLicenseTypeCriteria that = (VehicleLicenseTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(rank, that.rank) &&
            Objects.equals(engName, that.engName) &&
            Objects.equals(code, that.code) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(active, that.active) &&
            Objects.equals(gantryToll, that.gantryToll) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rank, engName, code, updated, active, gantryToll, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleLicenseTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (rank != null ? "rank=" + rank + ", " : "") +
            (engName != null ? "engName=" + engName + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (active != null ? "active=" + active + ", " : "") +
            (gantryToll != null ? "gantryToll=" + gantryToll + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
