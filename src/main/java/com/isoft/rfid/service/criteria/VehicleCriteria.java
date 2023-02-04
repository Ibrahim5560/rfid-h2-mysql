package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.Vehicle} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.VehicleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter govid;

    private StringFilter plate;

    private StringFilter rfid;

    private LongFilter driver;

    private StringFilter producer;

    private StringFilter model;

    private LongFilter year;

    private StringFilter color;

    private StringFilter decals;

    private StringFilter spoilers;

    private StringFilter glass;

    private StringFilter chassisSerial;

    private StringFilter motorSerial;

    private StringFilter vehicleLicenseSerial;

    private LongFilter vehicleLicenseType;

    private LongFilter vehicleType;

    private IntegerFilter wanted;

    private IntegerFilter licenseRevoked;

    private IntegerFilter licenseExpired;

    private LongFilter jurisdiction;

    private InstantFilter updated;

    private StringFilter fuelType;

    private LongFilter motorCc;

    private InstantFilter licenseIssued;

    private InstantFilter licenseExpires;

    private LongFilter wtKg;

    private IntegerFilter passengers;

    private IntegerFilter tractorParts;

    private IntegerFilter extraSizePercent;

    private LongFilter wtExtra;

    private StringFilter bikeData;

    private LongFilter wantedBy;

    private StringFilter wantedFor;

    private LongFilter office;

    private StringFilter statusName;

    private InstantFilter stolen;

    private DoubleFilter gantryToll;

    private Boolean distinct;

    public VehicleCriteria() {}

    public VehicleCriteria(VehicleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.govid = other.govid == null ? null : other.govid.copy();
        this.plate = other.plate == null ? null : other.plate.copy();
        this.rfid = other.rfid == null ? null : other.rfid.copy();
        this.driver = other.driver == null ? null : other.driver.copy();
        this.producer = other.producer == null ? null : other.producer.copy();
        this.model = other.model == null ? null : other.model.copy();
        this.year = other.year == null ? null : other.year.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.decals = other.decals == null ? null : other.decals.copy();
        this.spoilers = other.spoilers == null ? null : other.spoilers.copy();
        this.glass = other.glass == null ? null : other.glass.copy();
        this.chassisSerial = other.chassisSerial == null ? null : other.chassisSerial.copy();
        this.motorSerial = other.motorSerial == null ? null : other.motorSerial.copy();
        this.vehicleLicenseSerial = other.vehicleLicenseSerial == null ? null : other.vehicleLicenseSerial.copy();
        this.vehicleLicenseType = other.vehicleLicenseType == null ? null : other.vehicleLicenseType.copy();
        this.vehicleType = other.vehicleType == null ? null : other.vehicleType.copy();
        this.wanted = other.wanted == null ? null : other.wanted.copy();
        this.licenseRevoked = other.licenseRevoked == null ? null : other.licenseRevoked.copy();
        this.licenseExpired = other.licenseExpired == null ? null : other.licenseExpired.copy();
        this.jurisdiction = other.jurisdiction == null ? null : other.jurisdiction.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.fuelType = other.fuelType == null ? null : other.fuelType.copy();
        this.motorCc = other.motorCc == null ? null : other.motorCc.copy();
        this.licenseIssued = other.licenseIssued == null ? null : other.licenseIssued.copy();
        this.licenseExpires = other.licenseExpires == null ? null : other.licenseExpires.copy();
        this.wtKg = other.wtKg == null ? null : other.wtKg.copy();
        this.passengers = other.passengers == null ? null : other.passengers.copy();
        this.tractorParts = other.tractorParts == null ? null : other.tractorParts.copy();
        this.extraSizePercent = other.extraSizePercent == null ? null : other.extraSizePercent.copy();
        this.wtExtra = other.wtExtra == null ? null : other.wtExtra.copy();
        this.bikeData = other.bikeData == null ? null : other.bikeData.copy();
        this.wantedBy = other.wantedBy == null ? null : other.wantedBy.copy();
        this.wantedFor = other.wantedFor == null ? null : other.wantedFor.copy();
        this.office = other.office == null ? null : other.office.copy();
        this.statusName = other.statusName == null ? null : other.statusName.copy();
        this.stolen = other.stolen == null ? null : other.stolen.copy();
        this.gantryToll = other.gantryToll == null ? null : other.gantryToll.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VehicleCriteria copy() {
        return new VehicleCriteria(this);
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

    public StringFilter getGovid() {
        return govid;
    }

    public StringFilter govid() {
        if (govid == null) {
            govid = new StringFilter();
        }
        return govid;
    }

    public void setGovid(StringFilter govid) {
        this.govid = govid;
    }

    public StringFilter getPlate() {
        return plate;
    }

    public StringFilter plate() {
        if (plate == null) {
            plate = new StringFilter();
        }
        return plate;
    }

    public void setPlate(StringFilter plate) {
        this.plate = plate;
    }

    public StringFilter getRfid() {
        return rfid;
    }

    public StringFilter rfid() {
        if (rfid == null) {
            rfid = new StringFilter();
        }
        return rfid;
    }

    public void setRfid(StringFilter rfid) {
        this.rfid = rfid;
    }

    public LongFilter getDriver() {
        return driver;
    }

    public LongFilter driver() {
        if (driver == null) {
            driver = new LongFilter();
        }
        return driver;
    }

    public void setDriver(LongFilter driver) {
        this.driver = driver;
    }

    public StringFilter getProducer() {
        return producer;
    }

    public StringFilter producer() {
        if (producer == null) {
            producer = new StringFilter();
        }
        return producer;
    }

    public void setProducer(StringFilter producer) {
        this.producer = producer;
    }

    public StringFilter getModel() {
        return model;
    }

    public StringFilter model() {
        if (model == null) {
            model = new StringFilter();
        }
        return model;
    }

    public void setModel(StringFilter model) {
        this.model = model;
    }

    public LongFilter getYear() {
        return year;
    }

    public LongFilter year() {
        if (year == null) {
            year = new LongFilter();
        }
        return year;
    }

    public void setYear(LongFilter year) {
        this.year = year;
    }

    public StringFilter getColor() {
        return color;
    }

    public StringFilter color() {
        if (color == null) {
            color = new StringFilter();
        }
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public StringFilter getDecals() {
        return decals;
    }

    public StringFilter decals() {
        if (decals == null) {
            decals = new StringFilter();
        }
        return decals;
    }

    public void setDecals(StringFilter decals) {
        this.decals = decals;
    }

    public StringFilter getSpoilers() {
        return spoilers;
    }

    public StringFilter spoilers() {
        if (spoilers == null) {
            spoilers = new StringFilter();
        }
        return spoilers;
    }

    public void setSpoilers(StringFilter spoilers) {
        this.spoilers = spoilers;
    }

    public StringFilter getGlass() {
        return glass;
    }

    public StringFilter glass() {
        if (glass == null) {
            glass = new StringFilter();
        }
        return glass;
    }

    public void setGlass(StringFilter glass) {
        this.glass = glass;
    }

    public StringFilter getChassisSerial() {
        return chassisSerial;
    }

    public StringFilter chassisSerial() {
        if (chassisSerial == null) {
            chassisSerial = new StringFilter();
        }
        return chassisSerial;
    }

    public void setChassisSerial(StringFilter chassisSerial) {
        this.chassisSerial = chassisSerial;
    }

    public StringFilter getMotorSerial() {
        return motorSerial;
    }

    public StringFilter motorSerial() {
        if (motorSerial == null) {
            motorSerial = new StringFilter();
        }
        return motorSerial;
    }

    public void setMotorSerial(StringFilter motorSerial) {
        this.motorSerial = motorSerial;
    }

    public StringFilter getVehicleLicenseSerial() {
        return vehicleLicenseSerial;
    }

    public StringFilter vehicleLicenseSerial() {
        if (vehicleLicenseSerial == null) {
            vehicleLicenseSerial = new StringFilter();
        }
        return vehicleLicenseSerial;
    }

    public void setVehicleLicenseSerial(StringFilter vehicleLicenseSerial) {
        this.vehicleLicenseSerial = vehicleLicenseSerial;
    }

    public LongFilter getVehicleLicenseType() {
        return vehicleLicenseType;
    }

    public LongFilter vehicleLicenseType() {
        if (vehicleLicenseType == null) {
            vehicleLicenseType = new LongFilter();
        }
        return vehicleLicenseType;
    }

    public void setVehicleLicenseType(LongFilter vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public LongFilter getVehicleType() {
        return vehicleType;
    }

    public LongFilter vehicleType() {
        if (vehicleType == null) {
            vehicleType = new LongFilter();
        }
        return vehicleType;
    }

    public void setVehicleType(LongFilter vehicleType) {
        this.vehicleType = vehicleType;
    }

    public IntegerFilter getWanted() {
        return wanted;
    }

    public IntegerFilter wanted() {
        if (wanted == null) {
            wanted = new IntegerFilter();
        }
        return wanted;
    }

    public void setWanted(IntegerFilter wanted) {
        this.wanted = wanted;
    }

    public IntegerFilter getLicenseRevoked() {
        return licenseRevoked;
    }

    public IntegerFilter licenseRevoked() {
        if (licenseRevoked == null) {
            licenseRevoked = new IntegerFilter();
        }
        return licenseRevoked;
    }

    public void setLicenseRevoked(IntegerFilter licenseRevoked) {
        this.licenseRevoked = licenseRevoked;
    }

    public IntegerFilter getLicenseExpired() {
        return licenseExpired;
    }

    public IntegerFilter licenseExpired() {
        if (licenseExpired == null) {
            licenseExpired = new IntegerFilter();
        }
        return licenseExpired;
    }

    public void setLicenseExpired(IntegerFilter licenseExpired) {
        this.licenseExpired = licenseExpired;
    }

    public LongFilter getJurisdiction() {
        return jurisdiction;
    }

    public LongFilter jurisdiction() {
        if (jurisdiction == null) {
            jurisdiction = new LongFilter();
        }
        return jurisdiction;
    }

    public void setJurisdiction(LongFilter jurisdiction) {
        this.jurisdiction = jurisdiction;
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

    public StringFilter getFuelType() {
        return fuelType;
    }

    public StringFilter fuelType() {
        if (fuelType == null) {
            fuelType = new StringFilter();
        }
        return fuelType;
    }

    public void setFuelType(StringFilter fuelType) {
        this.fuelType = fuelType;
    }

    public LongFilter getMotorCc() {
        return motorCc;
    }

    public LongFilter motorCc() {
        if (motorCc == null) {
            motorCc = new LongFilter();
        }
        return motorCc;
    }

    public void setMotorCc(LongFilter motorCc) {
        this.motorCc = motorCc;
    }

    public InstantFilter getLicenseIssued() {
        return licenseIssued;
    }

    public InstantFilter licenseIssued() {
        if (licenseIssued == null) {
            licenseIssued = new InstantFilter();
        }
        return licenseIssued;
    }

    public void setLicenseIssued(InstantFilter licenseIssued) {
        this.licenseIssued = licenseIssued;
    }

    public InstantFilter getLicenseExpires() {
        return licenseExpires;
    }

    public InstantFilter licenseExpires() {
        if (licenseExpires == null) {
            licenseExpires = new InstantFilter();
        }
        return licenseExpires;
    }

    public void setLicenseExpires(InstantFilter licenseExpires) {
        this.licenseExpires = licenseExpires;
    }

    public LongFilter getWtKg() {
        return wtKg;
    }

    public LongFilter wtKg() {
        if (wtKg == null) {
            wtKg = new LongFilter();
        }
        return wtKg;
    }

    public void setWtKg(LongFilter wtKg) {
        this.wtKg = wtKg;
    }

    public IntegerFilter getPassengers() {
        return passengers;
    }

    public IntegerFilter passengers() {
        if (passengers == null) {
            passengers = new IntegerFilter();
        }
        return passengers;
    }

    public void setPassengers(IntegerFilter passengers) {
        this.passengers = passengers;
    }

    public IntegerFilter getTractorParts() {
        return tractorParts;
    }

    public IntegerFilter tractorParts() {
        if (tractorParts == null) {
            tractorParts = new IntegerFilter();
        }
        return tractorParts;
    }

    public void setTractorParts(IntegerFilter tractorParts) {
        this.tractorParts = tractorParts;
    }

    public IntegerFilter getExtraSizePercent() {
        return extraSizePercent;
    }

    public IntegerFilter extraSizePercent() {
        if (extraSizePercent == null) {
            extraSizePercent = new IntegerFilter();
        }
        return extraSizePercent;
    }

    public void setExtraSizePercent(IntegerFilter extraSizePercent) {
        this.extraSizePercent = extraSizePercent;
    }

    public LongFilter getWtExtra() {
        return wtExtra;
    }

    public LongFilter wtExtra() {
        if (wtExtra == null) {
            wtExtra = new LongFilter();
        }
        return wtExtra;
    }

    public void setWtExtra(LongFilter wtExtra) {
        this.wtExtra = wtExtra;
    }

    public StringFilter getBikeData() {
        return bikeData;
    }

    public StringFilter bikeData() {
        if (bikeData == null) {
            bikeData = new StringFilter();
        }
        return bikeData;
    }

    public void setBikeData(StringFilter bikeData) {
        this.bikeData = bikeData;
    }

    public LongFilter getWantedBy() {
        return wantedBy;
    }

    public LongFilter wantedBy() {
        if (wantedBy == null) {
            wantedBy = new LongFilter();
        }
        return wantedBy;
    }

    public void setWantedBy(LongFilter wantedBy) {
        this.wantedBy = wantedBy;
    }

    public StringFilter getWantedFor() {
        return wantedFor;
    }

    public StringFilter wantedFor() {
        if (wantedFor == null) {
            wantedFor = new StringFilter();
        }
        return wantedFor;
    }

    public void setWantedFor(StringFilter wantedFor) {
        this.wantedFor = wantedFor;
    }

    public LongFilter getOffice() {
        return office;
    }

    public LongFilter office() {
        if (office == null) {
            office = new LongFilter();
        }
        return office;
    }

    public void setOffice(LongFilter office) {
        this.office = office;
    }

    public StringFilter getStatusName() {
        return statusName;
    }

    public StringFilter statusName() {
        if (statusName == null) {
            statusName = new StringFilter();
        }
        return statusName;
    }

    public void setStatusName(StringFilter statusName) {
        this.statusName = statusName;
    }

    public InstantFilter getStolen() {
        return stolen;
    }

    public InstantFilter stolen() {
        if (stolen == null) {
            stolen = new InstantFilter();
        }
        return stolen;
    }

    public void setStolen(InstantFilter stolen) {
        this.stolen = stolen;
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
        final VehicleCriteria that = (VehicleCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(govid, that.govid) &&
            Objects.equals(plate, that.plate) &&
            Objects.equals(rfid, that.rfid) &&
            Objects.equals(driver, that.driver) &&
            Objects.equals(producer, that.producer) &&
            Objects.equals(model, that.model) &&
            Objects.equals(year, that.year) &&
            Objects.equals(color, that.color) &&
            Objects.equals(decals, that.decals) &&
            Objects.equals(spoilers, that.spoilers) &&
            Objects.equals(glass, that.glass) &&
            Objects.equals(chassisSerial, that.chassisSerial) &&
            Objects.equals(motorSerial, that.motorSerial) &&
            Objects.equals(vehicleLicenseSerial, that.vehicleLicenseSerial) &&
            Objects.equals(vehicleLicenseType, that.vehicleLicenseType) &&
            Objects.equals(vehicleType, that.vehicleType) &&
            Objects.equals(wanted, that.wanted) &&
            Objects.equals(licenseRevoked, that.licenseRevoked) &&
            Objects.equals(licenseExpired, that.licenseExpired) &&
            Objects.equals(jurisdiction, that.jurisdiction) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(fuelType, that.fuelType) &&
            Objects.equals(motorCc, that.motorCc) &&
            Objects.equals(licenseIssued, that.licenseIssued) &&
            Objects.equals(licenseExpires, that.licenseExpires) &&
            Objects.equals(wtKg, that.wtKg) &&
            Objects.equals(passengers, that.passengers) &&
            Objects.equals(tractorParts, that.tractorParts) &&
            Objects.equals(extraSizePercent, that.extraSizePercent) &&
            Objects.equals(wtExtra, that.wtExtra) &&
            Objects.equals(bikeData, that.bikeData) &&
            Objects.equals(wantedBy, that.wantedBy) &&
            Objects.equals(wantedFor, that.wantedFor) &&
            Objects.equals(office, that.office) &&
            Objects.equals(statusName, that.statusName) &&
            Objects.equals(stolen, that.stolen) &&
            Objects.equals(gantryToll, that.gantryToll) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            govid,
            plate,
            rfid,
            driver,
            producer,
            model,
            year,
            color,
            decals,
            spoilers,
            glass,
            chassisSerial,
            motorSerial,
            vehicleLicenseSerial,
            vehicleLicenseType,
            vehicleType,
            wanted,
            licenseRevoked,
            licenseExpired,
            jurisdiction,
            updated,
            fuelType,
            motorCc,
            licenseIssued,
            licenseExpires,
            wtKg,
            passengers,
            tractorParts,
            extraSizePercent,
            wtExtra,
            bikeData,
            wantedBy,
            wantedFor,
            office,
            statusName,
            stolen,
            gantryToll,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (govid != null ? "govid=" + govid + ", " : "") +
            (plate != null ? "plate=" + plate + ", " : "") +
            (rfid != null ? "rfid=" + rfid + ", " : "") +
            (driver != null ? "driver=" + driver + ", " : "") +
            (producer != null ? "producer=" + producer + ", " : "") +
            (model != null ? "model=" + model + ", " : "") +
            (year != null ? "year=" + year + ", " : "") +
            (color != null ? "color=" + color + ", " : "") +
            (decals != null ? "decals=" + decals + ", " : "") +
            (spoilers != null ? "spoilers=" + spoilers + ", " : "") +
            (glass != null ? "glass=" + glass + ", " : "") +
            (chassisSerial != null ? "chassisSerial=" + chassisSerial + ", " : "") +
            (motorSerial != null ? "motorSerial=" + motorSerial + ", " : "") +
            (vehicleLicenseSerial != null ? "vehicleLicenseSerial=" + vehicleLicenseSerial + ", " : "") +
            (vehicleLicenseType != null ? "vehicleLicenseType=" + vehicleLicenseType + ", " : "") +
            (vehicleType != null ? "vehicleType=" + vehicleType + ", " : "") +
            (wanted != null ? "wanted=" + wanted + ", " : "") +
            (licenseRevoked != null ? "licenseRevoked=" + licenseRevoked + ", " : "") +
            (licenseExpired != null ? "licenseExpired=" + licenseExpired + ", " : "") +
            (jurisdiction != null ? "jurisdiction=" + jurisdiction + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (fuelType != null ? "fuelType=" + fuelType + ", " : "") +
            (motorCc != null ? "motorCc=" + motorCc + ", " : "") +
            (licenseIssued != null ? "licenseIssued=" + licenseIssued + ", " : "") +
            (licenseExpires != null ? "licenseExpires=" + licenseExpires + ", " : "") +
            (wtKg != null ? "wtKg=" + wtKg + ", " : "") +
            (passengers != null ? "passengers=" + passengers + ", " : "") +
            (tractorParts != null ? "tractorParts=" + tractorParts + ", " : "") +
            (extraSizePercent != null ? "extraSizePercent=" + extraSizePercent + ", " : "") +
            (wtExtra != null ? "wtExtra=" + wtExtra + ", " : "") +
            (bikeData != null ? "bikeData=" + bikeData + ", " : "") +
            (wantedBy != null ? "wantedBy=" + wantedBy + ", " : "") +
            (wantedFor != null ? "wantedFor=" + wantedFor + ", " : "") +
            (office != null ? "office=" + office + ", " : "") +
            (statusName != null ? "statusName=" + statusName + ", " : "") +
            (stolen != null ? "stolen=" + stolen + ", " : "") +
            (gantryToll != null ? "gantryToll=" + gantryToll + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
