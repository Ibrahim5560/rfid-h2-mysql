import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVehicleOwner } from 'app/shared/model/vehicle-owner.model';
import { getEntity, updateEntity, createEntity, reset } from './vehicle-owner.reducer';

export const VehicleOwnerUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vehicleOwnerEntity = useAppSelector(state => state.vehicleOwner.entity);
  const loading = useAppSelector(state => state.vehicleOwner.loading);
  const updating = useAppSelector(state => state.vehicleOwner.updating);
  const updateSuccess = useAppSelector(state => state.vehicleOwner.updateSuccess);

  const handleClose = () => {
    navigate('/vehicle-owner' + location.search);
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
    values.dob = convertDateTimeToServer(values.dob);
    values.updated = convertDateTimeToServer(values.updated);

    const entity = {
      ...vehicleOwnerEntity,
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
          dob: displayDefaultDateTime(),
          updated: displayDefaultDateTime(),
        }
      : {
          ...vehicleOwnerEntity,
          dob: convertDateTimeFromServer(vehicleOwnerEntity.dob),
          updated: convertDateTimeFromServer(vehicleOwnerEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.vehicleOwner.home.createOrEditLabel" data-cy="VehicleOwnerCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.vehicleOwner.home.createOrEditLabel">Create or edit a VehicleOwner</Translate>
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
                  id="vehicle-owner-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.citizenSerial')}
                id="vehicle-owner-citizenSerial"
                name="citizenSerial"
                data-cy="citizenSerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.passportSerial')}
                id="vehicle-owner-passportSerial"
                name="passportSerial"
                data-cy="passportSerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.entitySerial')}
                id="vehicle-owner-entitySerial"
                name="entitySerial"
                data-cy="entitySerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.fullName')}
                id="vehicle-owner-fullName"
                name="fullName"
                data-cy="fullName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.firstName')}
                id="vehicle-owner-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.middleName')}
                id="vehicle-owner-middleName"
                name="middleName"
                data-cy="middleName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.grandName')}
                id="vehicle-owner-grandName"
                name="grandName"
                data-cy="grandName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.surname')}
                id="vehicle-owner-surname"
                name="surname"
                data-cy="surname"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.aka')}
                id="vehicle-owner-aka"
                name="aka"
                data-cy="aka"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.dob')}
                id="vehicle-owner-dob"
                name="dob"
                data-cy="dob"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.gender')}
                id="vehicle-owner-gender"
                name="gender"
                data-cy="gender"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.driverLicenseSerial')}
                id="vehicle-owner-driverLicenseSerial"
                name="driverLicenseSerial"
                data-cy="driverLicenseSerial"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.driverLicenseType')}
                id="vehicle-owner-driverLicenseType"
                name="driverLicenseType"
                data-cy="driverLicenseType"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.jurisdiction')}
                id="vehicle-owner-jurisdiction"
                name="jurisdiction"
                data-cy="jurisdiction"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.updated')}
                id="vehicle-owner-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleOwner.account')}
                id="vehicle-owner-account"
                name="account"
                data-cy="account"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vehicle-owner" replace color="info">
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

export default VehicleOwnerUpdate;
