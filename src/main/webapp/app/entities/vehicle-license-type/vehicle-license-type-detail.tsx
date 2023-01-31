import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vehicle-license-type.reducer';

export const VehicleLicenseTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vehicleLicenseTypeEntity = useAppSelector(state => state.vehicleLicenseType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vehicleLicenseTypeDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.detail.title">VehicleLicenseType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.name">Name</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.name}</dd>
          <dt>
            <span id="rank">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.rank">Rank</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.rank}</dd>
          <dt>
            <span id="engName">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.engName">Eng Name</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.engName}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.code">Code</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.code}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.updated">Updated</Translate>
            </span>
          </dt>
          <dd>
            {vehicleLicenseTypeEntity.updated ? (
              <TextFormat value={vehicleLicenseTypeEntity.updated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="active">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.active">Active</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.active}</dd>
          <dt>
            <span id="gantryToll">
              <Translate contentKey="rfidh2MysqlApp.vehicleLicenseType.gantryToll">Gantry Toll</Translate>
            </span>
          </dt>
          <dd>{vehicleLicenseTypeEntity.gantryToll}</dd>
        </dl>
        <Button tag={Link} to="/vehicle-license-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vehicle-license-type/${vehicleLicenseTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VehicleLicenseTypeDetail;
