import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITollPackage } from 'app/shared/model/toll-package.model';
import { getEntity, updateEntity, createEntity, reset } from './toll-package.reducer';

export const TollPackageUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const tollPackageEntity = useAppSelector(state => state.tollPackage.entity);
  const loading = useAppSelector(state => state.tollPackage.loading);
  const updating = useAppSelector(state => state.tollPackage.updating);
  const updateSuccess = useAppSelector(state => state.tollPackage.updateSuccess);

  const handleClose = () => {
    navigate('/toll-package' + location.search);
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
      ...tollPackageEntity,
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
          ...tollPackageEntity,
          updated: convertDateTimeFromServer(tollPackageEntity.updated),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="rfidh2MysqlApp.tollPackage.home.createOrEditLabel" data-cy="TollPackageCreateUpdateHeading">
            <Translate contentKey="rfidh2MysqlApp.tollPackage.home.createOrEditLabel">Create or edit a TollPackage</Translate>
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
                  id="toll-package-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.name')}
                id="toll-package-name"
                name="name"
                data-cy="name"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.engName')}
                id="toll-package-engName"
                name="engName"
                data-cy="engName"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.code')}
                id="toll-package-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.active')}
                id="toll-package-active"
                name="active"
                data-cy="active"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.updated')}
                id="toll-package-updated"
                name="updated"
                data-cy="updated"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.durationInDays')}
                id="toll-package-durationInDays"
                name="durationInDays"
                data-cy="durationInDays"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.gantry')}
                id="toll-package-gantry"
                name="gantry"
                data-cy="gantry"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.vehicleLicenseType')}
                id="toll-package-vehicleLicenseType"
                name="vehicleLicenseType"
                data-cy="vehicleLicenseType"
                type="text"
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.passageTimes')}
                id="toll-package-passageTimes"
                name="passageTimes"
                data-cy="passageTimes"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('rfidh2MysqlApp.tollPackage.totalFees')}
                id="toll-package-totalFees"
                name="totalFees"
                data-cy="totalFees"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/toll-package" replace color="info">
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

export default TollPackageUpdate;
