import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVehicle } from 'app/shared/model/vehicle.model';
import { getEntity, updateEntity, createEntity, reset } from './vehicle.reducer';

export const VehicleUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vehicleEntity = useAppSelector(state => state.vehicle.entity);
  const loading = useAppSelector(state => state.vehicle.loading);
  const updating = useAppSelector(state => state.vehicle.updating);
  const updateSuccess = useAppSelector(state => state.vehicle.updateSuccess);

  const handleClose = () => {
    navigate('/vehicle' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.updated = convertDateTimeToServer(values.updated);
    values.licenseIssued = convertDateTimeToServer(values.licenseIssued);
    values.licenseExpires = convertDateTimeToServer(values.licenseExpires);
    values.stolen = convertDateTimeToServer(values.stolen);

    const entity = {
      ...vehicleEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          updated: displayDefaultDateTime(),
          licenseIssued: displayDefaultDateTime(),
          licenseExpires: displayDefaultDateTime(),
          stolen: displayDefaultDateTime(),
        }
      : {
          ...vehicleEntity,
          updated: convertDateTimeFromServer(vehicleEntity.updated),
          licenseIssued: convertDateTimeFromServer(vehicleEntity.licenseIssued),
          licenseExpires: convertDateTimeFromServer(vehicleEntity.licenseExpires),
          stolen: convertDateTimeFromServer(vehicleEntity.stolen),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.vehicle.home.createOrEditLabel" data-cy="VehicleCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.vehicle.home.createOrEditLabel">Create or edit a Vehicle</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="vehicle-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.govid')}
                id="vehicle-govid"
                name="govid"
                data-cy="govid"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.plate')}
                id="vehicle-plate"
                name="plate"
                data-cy="plate"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.rfid')}
                id="vehicle-rfid"
                name="rfid"
                data-cy="rfid"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.driver')}
                id="vehicle-driver"
                name="driver"
                data-cy="driver"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.producer')}
                id="vehicle-producer"
                name="producer"
                data-cy="producer"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.model')}
                id="vehicle-model"
                name="model"
                data-cy="model"
                type="text"
              />
              <ValidatedField label={translate('rfidh2MysqlApp.vehicle.year')} id="vehicle-year" name="year" data-cy="year" type="text" />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.color')}
                id="vehicle-color"
                name="color"
                data-cy="color"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.decals')}
                id="vehicle-decals"
                name="decals"
                data-cy="decals"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.spoilers')}
                id="vehicle-spoilers"
                name="spoilers"
                data-cy="spoilers"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.glass')}
                id="vehicle-glass"
                name="glass"
                data-cy="glass"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.chassisSerial')}
                id="vehicle-chassisSerial"
                name="chassisSerial"
                data-cy="chassisSerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.motorSerial')}
                id="vehicle-motorSerial"
                name="motorSerial"
                data-cy="motorSerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.vehicleLicenseSerial')}
                id="vehicle-vehicleLicenseSerial"
                name="vehicleLicenseSerial"
                data-cy="vehicleLicenseSerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.vehicleLicenseType')}
                id="vehicle-vehicleLicenseType"
                name="vehicleLicenseType"
                data-cy="vehicleLicenseType"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.vehicleType')}
                id="vehicle-vehicleType"
                name="vehicleType"
                data-cy="vehicleType"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.wanted')}
                id="vehicle-wanted"
                name="wanted"
                data-cy="wanted"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.licenseRevoked')}
                id="vehicle-licenseRevoked"
                name="licenseRevoked"
                data-cy="licenseRevoked"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.licenseExpired')}
                id="vehicle-licenseExpired"
                name="licenseExpired"
                data-cy="licenseExpired"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.jurisdiction')}
                id="vehicle-jurisdiction"
                name="jurisdiction"
                data-cy="jurisdiction"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.updated')}
                id="vehicle-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.fuelType')}
                id="vehicle-fuelType"
                name="fuelType"
                data-cy="fuelType"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.motorCc')}
                id="vehicle-motorCc"
                name="motorCc"
                data-cy="motorCc"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.licenseIssued')}
                id="vehicle-licenseIssued"
                name="licenseIssued"
                data-cy="licenseIssued"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.licenseExpires')}
                id="vehicle-licenseExpires"
                name="licenseExpires"
                data-cy="licenseExpires"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('rfidh2MysqlApp.vehicle.wtKg')} id="vehicle-wtKg" name="wtKg" data-cy="wtKg" type="text" />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.passengers')}
                id="vehicle-passengers"
                name="passengers"
                data-cy="passengers"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.tractorParts')}
                id="vehicle-tractorParts"
                name="tractorParts"
                data-cy="tractorParts"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.extraSizePercent')}
                id="vehicle-extraSizePercent"
                name="extraSizePercent"
                data-cy="extraSizePercent"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.wtExtra')}
                id="vehicle-wtExtra"
                name="wtExtra"
                data-cy="wtExtra"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.bikeData')}
                id="vehicle-bikeData"
                name="bikeData"
                data-cy="bikeData"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.wantedBy')}
                id="vehicle-wantedBy"
                name="wantedBy"
                data-cy="wantedBy"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.wantedFor')}
                id="vehicle-wantedFor"
                name="wantedFor"
                data-cy="wantedFor"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.office')}
                id="vehicle-office"
                name="office"
                data-cy="office"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.statusName')}
                id="vehicle-statusName"
                name="statusName"
                data-cy="statusName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.stolen')}
                id="vehicle-stolen"
                name="stolen"
                data-cy="stolen"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicle.gantryToll')}
                id="vehicle-gantryToll"
                name="gantryToll"
                data-cy="gantryToll"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vehicle" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default VehicleUpdate;
