import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITollWhiteList } from 'app/shared/model/toll-white-list.model';
import { getEntity, updateEntity, createEntity, reset } from './toll-white-list.reducer';

export const TollWhiteListUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tollWhiteListEntity = useAppSelector(state => state.tollWhiteList.entity);
  const loading = useAppSelector(state => state.tollWhiteList.loading);
  const updating = useAppSelector(state => state.tollWhiteList.updating);
  const updateSuccess = useAppSelector(state => state.tollWhiteList.updateSuccess);

  const handleClose = () => {
    navigate('/toll-white-list' + location.search);
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
      ...tollWhiteListEntity,
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
          ...tollWhiteListEntity,
          updated: convertDateTimeFromServer(tollWhiteListEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.tollWhiteList.home.createOrEditLabel" data-cy="TollWhiteListCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.tollWhiteList.home.createOrEditLabel">Create or edit a TollWhiteList</Translate>
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
                  id="toll-white-list-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.name')}
                id="toll-white-list-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.engName')}
                id="toll-white-list-engName"
                name="engName"
                data-cy="engName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.code')}
                id="toll-white-list-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.active')}
                id="toll-white-list-active"
                name="active"
                data-cy="active"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.updated')}
                id="toll-white-list-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.gantry')}
                id="toll-white-list-gantry"
                name="gantry"
                data-cy="gantry"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.vehicleLicenseType')}
                id="toll-white-list-vehicleLicenseType"
                name="vehicleLicenseType"
                data-cy="vehicleLicenseType"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.vehicle')}
                id="toll-white-list-vehicle"
                name="vehicle"
                data-cy="vehicle"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.vehicleOwner')}
                id="toll-white-list-vehicleOwner"
                name="vehicleOwner"
                data-cy="vehicleOwner"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollWhiteList.passageTimes')}
                id="toll-white-list-passageTimes"
                name="passageTimes"
                data-cy="passageTimes"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/toll-white-list" replace color="info">
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

export default TollWhiteListUpdate;
