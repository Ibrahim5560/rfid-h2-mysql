import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './vehicle.reducer';

export const VehicleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const vehicleEntity = useAppSelector(state => state.vehicle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="vehicleDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.vehicle.detail.title">Vehicle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.id}</dd>
          <dt>
            <span id="govid">
              <Translate contentKey="rfidh2MysqlApp.vehicle.govid">Govid</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.govid}</dd>
          <dt>
            <span id="plate">
              <Translate contentKey="rfidh2MysqlApp.vehicle.plate">Plate</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.plate}</dd>
          <dt>
            <span id="rfid">
              <Translate contentKey="rfidh2MysqlApp.vehicle.rfid">Rfid</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.rfid}</dd>
          <dt>
            <span id="driver">
              <Translate contentKey="rfidh2MysqlApp.vehicle.driver">Driver</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.driver}</dd>
          <dt>
            <span id="producer">
              <Translate contentKey="rfidh2MysqlApp.vehicle.producer">Producer</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.producer}</dd>
          <dt>
            <span id="model">
              <Translate contentKey="rfidh2MysqlApp.vehicle.model">Model</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.model}</dd>
          <dt>
            <span id="year">
              <Translate contentKey="rfidh2MysqlApp.vehicle.year">Year</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.year}</dd>
          <dt>
            <span id="color">
              <Translate contentKey="rfidh2MysqlApp.vehicle.color">Color</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.color}</dd>
          <dt>
            <span id="decals">
              <Translate contentKey="rfidh2MysqlApp.vehicle.decals">Decals</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.decals}</dd>
          <dt>
            <span id="spoilers">
              <Translate contentKey="rfidh2MysqlApp.vehicle.spoilers">Spoilers</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.spoilers}</dd>
          <dt>
            <span id="glass">
              <Translate contentKey="rfidh2MysqlApp.vehicle.glass">Glass</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.glass}</dd>
          <dt>
            <span id="chassisSerial">
              <Translate contentKey="rfidh2MysqlApp.vehicle.chassisSerial">Chassis Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.chassisSerial}</dd>
          <dt>
            <span id="motorSerial">
              <Translate contentKey="rfidh2MysqlApp.vehicle.motorSerial">Motor Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.motorSerial}</dd>
          <dt>
            <span id="vehicleLicenseSerial">
              <Translate contentKey="rfidh2MysqlApp.vehicle.vehicleLicenseSerial">Vehicle License Serial</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.vehicleLicenseSerial}</dd>
          <dt>
            <span id="vehicleLicenseType">
              <Translate contentKey="rfidh2MysqlApp.vehicle.vehicleLicenseType">Vehicle License Type</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.vehicleLicenseType}</dd>
          <dt>
            <span id="vehicleType">
              <Translate contentKey="rfidh2MysqlApp.vehicle.vehicleType">Vehicle Type</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.vehicleType}</dd>
          <dt>
            <span id="wanted">
              <Translate contentKey="rfidh2MysqlApp.vehicle.wanted">Wanted</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.wanted}</dd>
          <dt>
            <span id="licenseRevoked">
              <Translate contentKey="rfidh2MysqlApp.vehicle.licenseRevoked">License Revoked</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.licenseRevoked}</dd>
          <dt>
            <span id="licenseExpired">
              <Translate contentKey="rfidh2MysqlApp.vehicle.licenseExpired">License Expired</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.licenseExpired}</dd>
          <dt>
            <span id="jurisdiction">
              <Translate contentKey="rfidh2MysqlApp.vehicle.jurisdiction">Jurisdiction</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.jurisdiction}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.vehicle.updated">Updated</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.updated ? <TextFormat value={vehicleEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="fuelType">
              <Translate contentKey="rfidh2MysqlApp.vehicle.fuelType">Fuel Type</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.fuelType}</dd>
          <dt>
            <span id="motorCc">
              <Translate contentKey="rfidh2MysqlApp.vehicle.motorCc">Motor Cc</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.motorCc}</dd>
          <dt>
            <span id="licenseIssued">
              <Translate contentKey="rfidh2MysqlApp.vehicle.licenseIssued">License Issued</Translate>
            </span>
          </dt>
          <dd>
            {vehicleEntity.licenseIssued ? <TextFormat value={vehicleEntity.licenseIssued} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="licenseExpires">
              <Translate contentKey="rfidh2MysqlApp.vehicle.licenseExpires">License Expires</Translate>
            </span>
          </dt>
          <dd>
            {vehicleEntity.licenseExpires ? <TextFormat value={vehicleEntity.licenseExpires} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="wtKg">
              <Translate contentKey="rfidh2MysqlApp.vehicle.wtKg">Wt Kg</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.wtKg}</dd>
          <dt>
            <span id="passengers">
              <Translate contentKey="rfidh2MysqlApp.vehicle.passengers">Passengers</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.passengers}</dd>
          <dt>
            <span id="tractorParts">
              <Translate contentKey="rfidh2MysqlApp.vehicle.tractorParts">Tractor Parts</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.tractorParts}</dd>
          <dt>
            <span id="extraSizePercent">
              <Translate contentKey="rfidh2MysqlApp.vehicle.extraSizePercent">Extra Size Percent</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.extraSizePercent}</dd>
          <dt>
            <span id="wtExtra">
              <Translate contentKey="rfidh2MysqlApp.vehicle.wtExtra">Wt Extra</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.wtExtra}</dd>
          <dt>
            <span id="bikeData">
              <Translate contentKey="rfidh2MysqlApp.vehicle.bikeData">Bike Data</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.bikeData}</dd>
          <dt>
            <span id="wantedBy">
              <Translate contentKey="rfidh2MysqlApp.vehicle.wantedBy">Wanted By</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.wantedBy}</dd>
          <dt>
            <span id="wantedFor">
              <Translate contentKey="rfidh2MysqlApp.vehicle.wantedFor">Wanted For</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.wantedFor}</dd>
          <dt>
            <span id="office">
              <Translate contentKey="rfidh2MysqlApp.vehicle.office">Office</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.office}</dd>
          <dt>
            <span id="statusName">
              <Translate contentKey="rfidh2MysqlApp.vehicle.statusName">Status Name</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.statusName}</dd>
          <dt>
            <span id="stolen">
              <Translate contentKey="rfidh2MysqlApp.vehicle.stolen">Stolen</Translate>
            </span>
          </dt>
          <dd>{vehicleEntity.stolen ? <TextFormat value={vehicleEntity.stolen} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
        </dl>
        <Button tag={Link} to="/vehicle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/vehicle/${vehicleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default VehicleDetail;
