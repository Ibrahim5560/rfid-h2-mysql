import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPeakHourTariff } from 'app/shared/model/peak-hour-tariff.model';
import { getEntity, updateEntity, createEntity, reset } from './peak-hour-tariff.reducer';

export const PeakHourTariffUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const peakHourTariffEntity = useAppSelector(state => state.peakHourTariff.entity);
  const loading = useAppSelector(state => state.peakHourTariff.loading);
  const updating = useAppSelector(state => state.peakHourTariff.updating);
  const updateSuccess = useAppSelector(state => state.peakHourTariff.updateSuccess);

  const handleClose = () => {
    navigate('/peak-hour-tariff' + location.search);
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
    values.peakHourFrom = convertDateTimeToServer(values.peakHourFrom);
    values.peakHourTo = convertDateTimeToServer(values.peakHourTo);
    values.updated = convertDateTimeToServer(values.updated);

    const entity = {
      ...peakHourTariffEntity,
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
          peakHourFrom: displayDefaultDateTime(),
          peakHourTo: displayDefaultDateTime(),
          updated: displayDefaultDateTime(),
        }
      : {
          ...peakHourTariffEntity,
          peakHourFrom: convertDateTimeFromServer(peakHourTariffEntity.peakHourFrom),
          peakHourTo: convertDateTimeFromServer(peakHourTariffEntity.peakHourTo),
          updated: convertDateTimeFromServer(peakHourTariffEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.peakHourTariff.home.createOrEditLabel" data-cy="PeakHourTariffCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.peakHourTariff.home.createOrEditLabel">Create or edit a PeakHourTariff</Translate>
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
                  id="peak-hour-tariff-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.peakHourTariff.peakHourFrom')}
                id="peak-hour-tariff-peakHourFrom"
                name="peakHourFrom"
                data-cy="peakHourFrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.peakHourTariff.peakHourTo')}
                id="peak-hour-tariff-peakHourTo"
                name="peakHourTo"
                data-cy="peakHourTo"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.peakHourTariff.gantry')}
                id="peak-hour-tariff-gantry"
                name="gantry"
                data-cy="gantry"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.peakHourTariff.gantryToll')}
                id="peak-hour-tariff-gantryToll"
                name="gantryToll"
                data-cy="gantryToll"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.peakHourTariff.active')}
                id="peak-hour-tariff-active"
                name="active"
                data-cy="active"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.peakHourTariff.updated')}
                id="peak-hour-tariff-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/peak-hour-tariff" replace color="info">
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

export default PeakHourTariffUpdate;
