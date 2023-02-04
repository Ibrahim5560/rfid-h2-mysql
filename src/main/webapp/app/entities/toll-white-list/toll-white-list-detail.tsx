import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './toll-white-list.reducer';

export const TollWhiteListDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tollWhiteListEntity = useAppSelector(state => state.tollWhiteList.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tollWhiteListDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.tollWhiteList.detail.title">TollWhiteList</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.name">Name</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.name}</dd>
          <dt>
            <span id="engName">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.engName">Eng Name</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.engName}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.code">Code</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.code}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.active">Active</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.active}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.updated">Updated</Translate>
            </span>
          </dt>
          <dd>
            {tollWhiteListEntity.updated ? <TextFormat value={tollWhiteListEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="gantry">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.gantry">Gantry</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.gantry}</dd>
          <dt>
            <span id="vehicleLicenseType">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.vehicleLicenseType">Vehicle License Type</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.vehicleLicenseType}</dd>
          <dt>
            <span id="vehicle">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.vehicle">Vehicle</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.vehicle}</dd>
          <dt>
            <span id="vehicleOwner">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.vehicleOwner">Vehicle Owner</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.vehicleOwner}</dd>
          <dt>
            <span id="passageTimes">
              <Translate contentKey="rfidh2MysqlApp.tollWhiteList.passageTimes">Passage Times</Translate>
            </span>
          </dt>
          <dd>{tollWhiteListEntity.passageTimes}</dd>
        </dl>
        <Button tag={Link} to="/toll-white-list" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/toll-white-list/${tollWhiteListEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TollWhiteListDetail;
