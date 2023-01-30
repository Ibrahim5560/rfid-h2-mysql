package com.isoft.rfid.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.isoft.rfid.domain.VehicleOwner} entity. This class is used
 * in {@link com.isoft.rfid.web.rest.VehicleOwnerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /vehicle-owners?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class VehicleOwnerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter citizenSerial;

    private StringFilter passportSerial;

    private StringFilter entitySerial;

    private StringFilter fullName;

    private StringFilter firstName;

    private StringFilter middleName;

    private StringFilter grandName;

    private StringFilter surname;

    private StringFilter aka;

    private InstantFilter dob;

    private StringFilter gender;

    private StringFilter driverLicenseSerial;

    private LongFilter driverLicenseType;

    private LongFilter jurisdiction;

    private InstantFilter updated;

    private DoubleFilter account;

    private Boolean distinct;

    public VehicleOwnerCriteria() {}

    public VehicleOwnerCriteria(VehicleOwnerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.citizenSerial = other.citizenSerial == null ? null : other.citizenSerial.copy();
        this.passportSerial = other.passportSerial == null ? null : other.passportSerial.copy();
        this.entitySerial = other.entitySerial == null ? null : other.entitySerial.copy();
        this.fullName = other.fullName == null ? null : other.fullName.copy();
        this.firstName = other.firstName == null ? null : other.firstName.copy();
        this.middleName = other.middleName == null ? null : other.middleName.copy();
        this.grandName = other.grandName == null ? null : other.grandName.copy();
        this.surname = other.surname == null ? null : other.surname.copy();
        this.aka = other.aka == null ? null : other.aka.copy();
        this.dob = other.dob == null ? null : other.dob.copy();
        this.gender = other.gender == null ? null : other.gender.copy();
        this.driverLicenseSerial = other.driverLicenseSerial == null ? null : other.driverLicenseSerial.copy();
        this.driverLicenseType = other.driverLicenseType == null ? null : other.driverLicenseType.copy();
        this.jurisdiction = other.jurisdiction == null ? null : other.jurisdiction.copy();
        this.updated = other.updated == null ? null : other.updated.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.distinct = other.distinct;
    }

    @Override
    public VehicleOwnerCriteria copy() {
        return new VehicleOwnerCriteria(this);
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

    public StringFilter getCitizenSerial() {
        return citizenSerial;
    }

    public StringFilter citizenSerial() {
        if (citizenSerial == null) {
            citizenSerial = new StringFilter();
        }
        return citizenSerial;
    }

    public void setCitizenSerial(StringFilter citizenSerial) {
        this.citizenSerial = citizenSerial;
    }

    public StringFilter getPassportSerial() {
        return passportSerial;
    }

    public StringFilter passportSerial() {
        if (passportSerial == null) {
            passportSerial = new StringFilter();
        }
        return passportSerial;
    }

    public void setPassportSerial(StringFilter passportSerial) {
        this.passportSerial = passportSerial;
    }

    public StringFilter getEntitySerial() {
        return entitySerial;
    }

    public StringFilter entitySerial() {
        if (entitySerial == null) {
            entitySerial = new StringFilter();
        }
        return entitySerial;
    }

    public void setEntitySerial(StringFilter entitySerial) {
        this.entitySerial = entitySerial;
    }

    public StringFilter getFullName() {
        return fullName;
    }

    public StringFilter fullName() {
        if (fullName == null) {
            fullName = new StringFilter();
        }
        return fullName;
    }

    public void setFullName(StringFilter fullName) {
        this.fullName = fullName;
    }

    public StringFilter getFirstName() {
        return firstName;
    }

    public StringFilter firstName() {
        if (firstName == null) {
            firstName = new StringFilter();
        }
        return firstName;
    }

    public void setFirstName(StringFilter firstName) {
        this.firstName = firstName;
    }

    public StringFilter getMiddleName() {
        return middleName;
    }

    public StringFilter middleName() {
        if (middleName == null) {
            middleName = new StringFilter();
        }
        return middleName;
    }

    public void setMiddleName(StringFilter middleName) {
        this.middleName = middleName;
    }

    public StringFilter getGrandName() {
        return grandName;
    }

    public StringFilter grandName() {
        if (grandName == null) {
            grandName = new StringFilter();
        }
        return grandName;
    }

    public void setGrandName(StringFilter grandName) {
        this.grandName = grandName;
    }

    public StringFilter getSurname() {
        return surname;
    }

    public StringFilter surname() {
        if (surname == null) {
            surname = new StringFilter();
        }
        return surname;
    }

    public void setSurname(StringFilter surname) {
        this.surname = surname;
    }

    public StringFilter getAka() {
        return aka;
    }

    public StringFilter aka() {
        if (aka == null) {
            aka = new StringFilter();
        }
        return aka;
    }

    public void setAka(StringFilter aka) {
        this.aka = aka;
    }

    public InstantFilter getDob() {
        return dob;
    }

    public InstantFilter dob() {
        if (dob == null) {
            dob = new InstantFilter();
        }
        return dob;
    }

    public void setDob(InstantFilter dob) {
        this.dob = dob;
    }

    public StringFilter getGender() {
        return gender;
    }

    public StringFilter gender() {
        if (gender == null) {
            gender = new StringFilter();
        }
        return gender;
    }

    public void setGender(StringFilter gender) {
        this.gender = gender;
    }

    public StringFilter getDriverLicenseSerial() {
        return driverLicenseSerial;
    }

    public StringFilter driverLicenseSerial() {
        if (driverLicenseSerial == null) {
            driverLicenseSerial = new StringFilter();
        }
        return driverLicenseSerial;
    }

    public void setDriverLicenseSerial(StringFilter driverLicenseSerial) {
        this.driverLicenseSerial = driverLicenseSerial;
    }

    public LongFilter getDriverLicenseType() {
        return driverLicenseType;
    }

    public LongFilter driverLicenseType() {
        if (driverLicenseType == null) {
            driverLicenseType = new LongFilter();
        }
        return driverLicenseType;
    }

    public void setDriverLicenseType(LongFilter driverLicenseType) {
        this.driverLicenseType = driverLicenseType;
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

    public DoubleFilter getAccount() {
        return account;
    }

    public DoubleFilter account() {
        if (account == null) {
            account = new DoubleFilter();
        }
        return account;
    }

    public void setAccount(DoubleFilter account) {
        this.account = account;
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
        final VehicleOwnerCriteria that = (VehicleOwnerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(citizenSerial, that.citizenSerial) &&
            Objects.equals(passportSerial, that.passportSerial) &&
            Objects.equals(entitySerial, that.entitySerial) &&
            Objects.equals(fullName, that.fullName) &&
            Objects.equals(firstName, that.firstName) &&
            Objects.equals(middleName, that.middleName) &&
            Objects.equals(grandName, that.grandName) &&
            Objects.equals(surname, that.surname) &&
            Objects.equals(aka, that.aka) &&
            Objects.equals(dob, that.dob) &&
            Objects.equals(gender, that.gender) &&
            Objects.equals(driverLicenseSerial, that.driverLicenseSerial) &&
            Objects.equals(driverLicenseType, that.driverLicenseType) &&
            Objects.equals(jurisdiction, that.jurisdiction) &&
            Objects.equals(updated, that.updated) &&
            Objects.equals(account, that.account) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            citizenSerial,
            passportSerial,
            entitySerial,
            fullName,
            firstName,
            middleName,
            grandName,
            surname,
            aka,
            dob,
            gender,
            driverLicenseSerial,
            driverLicenseType,
            jurisdiction,
            updated,
            account,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VehicleOwnerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (citizenSerial != null ? "citizenSerial=" + citizenSerial + ", " : "") +
            (passportSerial != null ? "passportSerial=" + passportSerial + ", " : "") +
            (entitySerial != null ? "entitySerial=" + entitySerial + ", " : "") +
            (fullName != null ? "fullName=" + fullName + ", " : "") +
            (firstName != null ? "firstName=" + firstName + ", " : "") +
            (middleName != null ? "middleName=" + middleName + ", " : "") +
            (grandName != null ? "grandName=" + grandName + ", " : "") +
            (surname != null ? "surname=" + surname + ", " : "") +
            (aka != null ? "aka=" + aka + ", " : "") +
            (dob != null ? "dob=" + dob + ", " : "") +
            (gender != null ? "gender=" + gender + ", " : "") +
            (driverLicenseSerial != null ? "driverLicenseSerial=" + driverLicenseSerial + ", " : "") +
            (driverLicenseType != null ? "driverLicenseType=" + driverLicenseType + ", " : "") +
            (jurisdiction != null ? "jurisdiction=" + jurisdiction + ", " : "") +
            (updated != null ? "updated=" + updated + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
