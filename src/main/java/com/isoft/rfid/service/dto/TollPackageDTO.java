package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.TollPackage} entity.
 */
@Schema(description = "TollPackage (toll_package) entity.\n @author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollPackageDTO implements Serializable {

    private Long id;

    private String name;

    private String engName;

    private String code;

    @NotNull
    private Integer active;

    @NotNull
    private Instant updated;

    private Integer durationInDays;

    private Integer gantry;

    private Integer vehicleLicenseType;

    @NotNull
    private Integer passageTimes;

    @NotNull
    private Double totalFees;

    private TollSubscriptionDTO tollSubscription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEngName() {
        return engName;
    }

    public void setEngName(String engName) {
        this.engName = engName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Integer getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(Integer durationInDays) {
        this.durationInDays = durationInDays;
    }

    public Integer getGantry() {
        return gantry;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Integer getVehicleLicenseType() {
        return vehicleLicenseType;
    }

    public void setVehicleLicenseType(Integer vehicleLicenseType) {
        this.vehicleLicenseType = vehicleLicenseType;
    }

    public Integer getPassageTimes() {
        return passageTimes;
    }

    public void setPassageTimes(Integer passageTimes) {
        this.passageTimes = passageTimes;
    }

    public Double getTotalFees() {
        return totalFees;
    }

    public void setTotalFees(Double totalFees) {
        this.totalFees = totalFees;
    }

    public TollSubscriptionDTO getTollSubscription() {
        return tollSubscription;
    }

    public void setTollSubscription(TollSubscriptionDTO tollSubscription) {
        this.tollSubscription = tollSubscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TollPackageDTO)) {
            return false;
        }

        TollPackageDTO tollPackageDTO = (TollPackageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tollPackageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollPackageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", engName='" + getEngName() + "'" +
            ", code='" + getCode() + "'" +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            ", durationInDays=" + getDurationInDays() +
            ", gantry=" + getGantry() +
            ", vehicleLicenseType=" + getVehicleLicenseType() +
            ", passageTimes=" + getPassageTimes() +
            ", totalFees=" + getTotalFees() +
            ", tollSubscription=" + getTollSubscription() +
            "}";
    }
}
