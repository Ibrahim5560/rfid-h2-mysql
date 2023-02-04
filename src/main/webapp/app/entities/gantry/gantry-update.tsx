import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGantry } from 'app/shared/model/gantry.model';
import { getEntity, updateEntity, createEntity, reset } from './gantry.reducer';

export const GantryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const gantryEntity = useAppSelector(state => state.gantry.entity);
  const loading = useAppSelector(state => state.gantry.loading);
  const updating = useAppSelector(state => state.gantry.updating);
  const updateSuccess = useAppSelector(state => state.gantry.updateSuccess);

  const handleClose = () => {
    navigate('/gantry' + location.search);
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
      ...gantryEntity,
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
          ...gantryEntity,
          updated: convertDateTimeFromServer(gantryEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.gantry.home.createOrEditLabel" data-cy="GantryCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.gantry.home.createOrEditLabel">Create or edit a Gantry</Translate>
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
                  id="gantry-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('rfidh2MysqlApp.gantry.name')} id="gantry-name" name="name" data-cy="name" type="text" />
              <ValidatedField label={translate('rfidh2MysqlApp.gantry.route')} id="gantry-route" name="route" data-cy="route" type="text" />
              <ValidatedField label={translate('rfidh2MysqlApp.gantry.lanes')} id="gantry-lanes" name="lanes" data-cy="lanes" type="text" />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.longitude')}
                id="gantry-longitude"
                name="longitude"
                data-cy="longitude"
                type="text"
              />
              <ValidatedField label={translate('rfidh2MysqlApp.gantry.lat')} id="gantry-lat" name="lat" data-cy="lat" type="text" />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.jurisdiction')}
                id="gantry-jurisdiction"
                name="jurisdiction"
                data-cy="jurisdiction"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.gantryType')}
                id="gantry-gantryType"
                name="gantryType"
                data-cy="gantryType"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.gantrySet')}
                id="gantry-gantrySet"
                name="gantrySet"
                data-cy="gantrySet"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.active')}
                id="gantry-active"
                name="active"
                data-cy="active"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.password')}
                id="gantry-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.updated')}
                id="gantry-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('rfidh2MysqlApp.gantry.ip')} id="gantry-ip" name="ip" data-cy="ip" type="text" />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.toll')}
                id="gantry-toll"
                name="toll"
                data-cy="toll"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.gantry.passageTimes')}
                id="gantry-passageTimes"
                name="passageTimes"
                data-cy="passageTimes"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gantry" replace color="info">
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

export default GantryUpdate;
