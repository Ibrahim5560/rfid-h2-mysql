import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './gantry.reducer';

export const GantryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const gantryEntity = useAppSelector(state => state.gantry.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="gantryDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.gantry.detail.title">Gantry</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="rfidh2MysqlApp.gantry.name">Name</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.name}</dd>
          <dt>
            <span id="route">
              <Translate contentKey="rfidh2MysqlApp.gantry.route">Route</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.route}</dd>
          <dt>
            <span id="lanes">
              <Translate contentKey="rfidh2MysqlApp.gantry.lanes">Lanes</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.lanes}</dd>
          <dt>
            <span id="longitude">
              <Translate contentKey="rfidh2MysqlApp.gantry.longitude">Longitude</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.longitude}</dd>
          <dt>
            <span id="lat">
              <Translate contentKey="rfidh2MysqlApp.gantry.lat">Lat</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.lat}</dd>
          <dt>
            <span id="jurisdiction">
              <Translate contentKey="rfidh2MysqlApp.gantry.jurisdiction">Jurisdiction</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.jurisdiction}</dd>
          <dt>
            <span id="gantryType">
              <Translate contentKey="rfidh2MysqlApp.gantry.gantryType">Gantry Type</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.gantryType}</dd>
          <dt>
            <span id="gantrySet">
              <Translate contentKey="rfidh2MysqlApp.gantry.gantrySet">Gantry Set</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.gantrySet}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="rfidh2MysqlApp.gantry.active">Active</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.active}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="rfidh2MysqlApp.gantry.password">Password</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.password}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.gantry.updated">Updated</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.updated ? <TextFormat value={gantryEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="ip">
              <Translate contentKey="rfidh2MysqlApp.gantry.ip">Ip</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.ip}</dd>
          <dt>
            <span id="toll">
              <Translate contentKey="rfidh2MysqlApp.gantry.toll">Toll</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.toll}</dd>
          <dt>
            <span id="passageTimes">
              <Translate contentKey="rfidh2MysqlApp.gantry.passageTimes">Passage Times</Translate>
            </span>
          </dt>
          <dd>{gantryEntity.passageTimes}</dd>
        </dl>
        <Button tag={Link} to="/gantry" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/gantry/${gantryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GantryDetail;
