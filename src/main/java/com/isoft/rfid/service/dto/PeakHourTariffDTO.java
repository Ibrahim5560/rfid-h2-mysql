package com.isoft.rfid.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.isoft.rfid.domain.PeakHourTariff} entity.
 */
@Schema(description = "PeakHourTariff (peak_hour_tariff) entity.\n @author Ibrahim Mohamed.")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PeakHourTariffDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant peakHourFrom;

    @NotNull
    private Instant peakHourTo;

    private Integer gantry;

    @NotNull
    private Double gantryToll;

    @NotNull
    private Integer active;

    @NotNull
    private Instant updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getPeakHourFrom() {
        return peakHourFrom;
    }

    public void setPeakHourFrom(Instant peakHourFrom) {
        this.peakHourFrom = peakHourFrom;
    }

    public Instant getPeakHourTo() {
        return peakHourTo;
    }

    public void setPeakHourTo(Instant peakHourTo) {
        this.peakHourTo = peakHourTo;
    }

    public Integer getGantry() {
        return gantry;
    }

    public void setGantry(Integer gantry) {
        this.gantry = gantry;
    }

    public Double getGantryToll() {
        return gantryToll;
    }

    public void setGantryToll(Double gantryToll) {
        this.gantryToll = gantryToll;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PeakHourTariffDTO)) {
            return false;
        }

        PeakHourTariffDTO peakHourTariffDTO = (PeakHourTariffDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, peakHourTariffDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PeakHourTariffDTO{" +
            "id=" + getId() +
            ", peakHourFrom='" + getPeakHourFrom() + "'" +
            ", peakHourTo='" + getPeakHourTo() + "'" +
            ", gantry=" + getGantry() +
            ", gantryToll=" + getGantryToll() +
            ", active=" + getActive() +
            ", updated='" + getUpdated() + "'" +
            "}";
    }
}
