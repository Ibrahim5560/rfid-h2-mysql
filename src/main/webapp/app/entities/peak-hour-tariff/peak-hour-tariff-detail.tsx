import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './peak-hour-tariff.reducer';

export const PeakHourTariffDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const peakHourTariffEntity = useAppSelector(state => state.peakHourTariff.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="peakHourTariffDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.peakHourTariff.detail.title">PeakHourTariff</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{peakHourTariffEntity.id}</dd>
          <dt>
            <span id="peakHourFrom">
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.peakHourFrom">Peak Hour From</Translate>
            </span>
          </dt>
          <dd>
            {peakHourTariffEntity.peakHourFrom ? (
              <TextFormat value={peakHourTariffEntity.peakHourFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="peakHourTo">
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.peakHourTo">Peak Hour To</Translate>
            </span>
          </dt>
          <dd>
            {peakHourTariffEntity.peakHourTo ? (
              <TextFormat value={peakHourTariffEntity.peakHourTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="gantry">
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.gantry">Gantry</Translate>
            </span>
          </dt>
          <dd>{peakHourTariffEntity.gantry}</dd>
          <dt>
            <span id="gantryToll">
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.gantryToll">Gantry Toll</Translate>
            </span>
          </dt>
          <dd>{peakHourTariffEntity.gantryToll}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.active">Active</Translate>
            </span>
          </dt>
          <dd>{peakHourTariffEntity.active}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.updated">Updated</Translate>
            </span>
          </dt>
          <dd>
            {peakHourTariffEntity.updated ? <TextFormat value={peakHourTariffEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/peak-hour-tariff" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/peak-hour-tariff/${peakHourTariffEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PeakHourTariffDetail;
