import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVehicleLicenseType } from 'app/shared/model/vehicle-license-type.model';
import { getEntity, updateEntity, createEntity, reset } from './vehicle-license-type.reducer';

export const VehicleLicenseTypeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vehicleLicenseTypeEntity = useAppSelector(state => state.vehicleLicenseType.entity);
  const loading = useAppSelector(state => state.vehicleLicenseType.loading);
  const updating = useAppSelector(state => state.vehicleLicenseType.updating);
  const updateSuccess = useAppSelector(state => state.vehicleLicenseType.updateSuccess);

  const handleClose = () => {
    navigate('/vehicle-license-type' + location.search);
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

    const entity = {
      ...vehicleLicenseTypeEntity,
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
        }
      : {
          ...vehicleLicenseTypeEntity,
          updated: convertDateTimeFromServer(vehicleLicenseTypeEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.vehicleLicenseType.home.createOrEditLabel" data-cy="VehicleLicenseTypeCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.home.createOrEditLabel">Create or edit a VehicleLicenseType</Translate>
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
                  id="vehicle-license-type-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.name')}
                id="vehicle-license-type-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.rank')}
                id="vehicle-license-type-rank"
                name="rank"
                data-cy="rank"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.engName')}
                id="vehicle-license-type-engName"
                name="engName"
                data-cy="engName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.code')}
                id="vehicle-license-type-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.updated')}
                id="vehicle-license-type-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.active')}
                id="vehicle-license-type-active"
                name="active"
                data-cy="active"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.vehicleLicenseType.gantryToll')}
                id="vehicle-license-type-gantryToll"
                name="gantryToll"
                data-cy="gantryToll"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vehicle-license-type" replace color="info">
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

export default VehicleLicenseTypeUpdate;
