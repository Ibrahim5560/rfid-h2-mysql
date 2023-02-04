package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.TollSubscription} entity.
 */
@Schema(description = "TollSubscription (toll_subscription) entity.\n @author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TollSubscriptionDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dateTimeFrom;

    @NotNull
    private Instant dateTimeTo;

    private Integer vehicle;

    private Integer vehicleOwner;

    @NotNull
    private Integer active;

    @NotNull
    private Instant updated;

    private TollPackageDTO tollPackage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateTimeFrom() {
        return dateTimeFrom;
    }

    public void setDateTimeFrom(Instant dateTimeFrom) {
        this.dateTimeFrom = dateTimeFrom;
    }

    public Instant getDateTimeTo() {
        return dateTimeTo;
    }

    public void setDateTimeTo(Instant dateTimeTo) {
        this.dateTimeTo = dateTimeTo;
    }

    public Integer getVehicle() {
        return vehicle;
    }

    public void setVehicle(Integer vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleOwner() {
        return vehicleOwner;
    }

    public void setVehicleOwner(Integer vehicleOwner) {
        this.vehicleOwner = vehicleOwner;
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

    public TollPackageDTO getTollPackage() {
        return tollPackage;
    }

    public void setTollPackage(TollPackageDTO tollPackage) {
        this.tollPackage = tollPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TollSubscriptionDTO)) {
            return false;
        }

        TollSubscriptionDTO tollSubscriptionDTO = (TollSubscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tollSubscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TollSubscriptionDTO{" +
            "id=" + getId() +
            ", dateTimeFrom='" + getDateTimeFrom() + "'" +
            ", dateTimeTo='" + getDateTimeTo() + "'" +
            ", vehicle=" + getVehicle() +
            ", vehicleOwner=" + getVehicleOwner() +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            ", tollPackage=" + getTollPackage() +
            "}";
    }
}
