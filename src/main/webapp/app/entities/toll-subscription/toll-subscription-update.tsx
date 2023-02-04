import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITollPackage } from 'app/shared/model/toll-package.model';
import { getEntities as getTollPackages } from 'app/entities/toll-package/toll-package.reducer';
import { ITollSubscription } from 'app/shared/model/toll-subscription.model';
import { getEntity, updateEntity, createEntity, reset } from './toll-subscription.reducer';

export const TollSubscriptionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tollPackages = useAppSelector(state => state.tollPackage.entities);
  const tollSubscriptionEntity = useAppSelector(state => state.tollSubscription.entity);
  const loading = useAppSelector(state => state.tollSubscription.loading);
  const updating = useAppSelector(state => state.tollSubscription.updating);
  const updateSuccess = useAppSelector(state => state.tollSubscription.updateSuccess);

  const handleClose = () => {
    navigate('/toll-subscription' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTollPackages({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.dateTimeFrom = convertDateTimeToServer(values.dateTimeFrom);
    values.dateTimeTo = convertDateTimeToServer(values.dateTimeTo);
    values.updated = convertDateTimeToServer(values.updated);

    const entity = {
      ...tollSubscriptionEntity,
      ...values,
      tollPackage: tollPackages.find(it => it.id.toString() === values.tollPackage.toString()),
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
          dateTimeFrom: displayDefaultDateTime(),
          dateTimeTo: displayDefaultDateTime(),
          updated: displayDefaultDateTime(),
        }
      : {
          ...tollSubscriptionEntity,
          dateTimeFrom: convertDateTimeFromServer(tollSubscriptionEntity.dateTimeFrom),
          dateTimeTo: convertDateTimeFromServer(tollSubscriptionEntity.dateTimeTo),
          updated: convertDateTimeFromServer(tollSubscriptionEntity.updated),
          tollPackage: tollSubscriptionEntity?.tollPackage?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.tollSubscription.home.createOrEditLabel" data-cy="TollSubscriptionCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.tollSubscription.home.createOrEditLabel">Create or edit a TollSubscription</Translate>
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
                  id="toll-subscription-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollSubscription.dateTimeFrom')}
                id="toll-subscription-dateTimeFrom"
                name="dateTimeFrom"
                data-cy="dateTimeFrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollSubscription.dateTimeTo')}
                id="toll-subscription-dateTimeTo"
                name="dateTimeTo"
                data-cy="dateTimeTo"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollSubscription.vehicle')}
                id="toll-subscription-vehicle"
                name="vehicle"
                data-cy="vehicle"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollSubscription.vehicleOwner')}
                id="toll-subscription-vehicleOwner"
                name="vehicleOwner"
                data-cy="vehicleOwner"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollSubscription.active')}
                id="toll-subscription-active"
                name="active"
                data-cy="active"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollSubscription.updated')}
                id="toll-subscription-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="toll-subscription-tollPackage"
                name="tollPackage"
                data-cy="tollPackage"
                label={translate('rfidh2MysqlApp.tollSubscription.tollPackage')}
                type="select"
              >
                <option value="" key="0" />
                {tollPackages
                  ? tollPackages.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/toll-subscription" replace color="info">
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

export default TollSubscriptionUpdate;
