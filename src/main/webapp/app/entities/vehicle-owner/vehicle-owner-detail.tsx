import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vehicle-owner.reducer';

export const VehicleOwnerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vehicleOwnerEntity = useAppSelector(state => state.vehicleOwner.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vehicleOwnerDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.vehicleOwner.detail.title">VehicleOwner</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.id}</dd>
          <dt>
            <span id="citizenSerial">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.citizenSerial">Citizen Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.citizenSerial}</dd>
          <dt>
            <span id="passportSerial">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.passportSerial">Passport Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.passportSerial}</dd>
          <dt>
            <span id="entitySerial">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.entitySerial">Entity Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.entitySerial}</dd>
          <dt>
            <span id="fullName">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.fullName">Full Name</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.fullName}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.firstName}</dd>
          <dt>
            <span id="middleName">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.middleName">Middle Name</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.middleName}</dd>
          <dt>
            <span id="grandName">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.grandName">Grand Name</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.grandName}</dd>
          <dt>
            <span id="surname">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.surname">Surname</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.surname}</dd>
          <dt>
            <span id="aka">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.aka">Aka</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.aka}</dd>
          <dt>
            <span id="dob">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.dob">Dob</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.dob ? <TextFormat value={vehicleOwnerEntity.dob} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="gender">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.gender">Gender</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.gender}</dd>
          <dt>
            <span id="driverLicenseSerial">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.driverLicenseSerial">Driver License Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.driverLicenseSerial}</dd>
          <dt>
            <span id="driverLicenseType">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.driverLicenseType">Driver License Type</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.driverLicenseType}</dd>
          <dt>
            <span id="jurisdiction">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.jurisdiction">Jurisdiction</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.jurisdiction}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.updated">Updated</Translate>
            </span>
          </dt>
          <dd>
            {vehicleOwnerEntity.updated ? <TextFormat value={vehicleOwnerEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="wallet">
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.wallet">Wallet</Translate>
            </span>
          </dt>
          <dd>{vehicleOwnerEntity.wallet}</dd>
        </dl>
        <Button tag={Link} to="/vehicle-owner" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vehicle-owner/${vehicleOwnerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VehicleOwnerDetail;
