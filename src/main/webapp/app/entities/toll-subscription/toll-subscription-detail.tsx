import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './toll-subscription.reducer';

export const TollSubscriptionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tollSubscriptionEntity = useAppSelector(state => state.tollSubscription.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tollSubscriptionDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.tollSubscription.detail.title">TollSubscription</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tollSubscriptionEntity.id}</dd>
          <dt>
            <span id="dateTimeFrom">
              <Translate contentKey="rfidh2MysqlApp.tollSubscription.dateTimeFrom">Date Time From</Translate>
            </span>
          </dt>
          <dd>
            {tollSubscriptionEntity.dateTimeFrom ? (
              <TextFormat value={tollSubscriptionEntity.dateTimeFrom} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="dateTimeTo">
              <Translate contentKey="rfidh2MysqlApp.tollSubscription.dateTimeTo">Date Time To</Translate>
            </span>
          </dt>
          <dd>
            {tollSubscriptionEntity.dateTimeTo ? (
              <TextFormat value={tollSubscriptionEntity.dateTimeTo} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="vehicle">
              <Translate contentKey="rfidh2MysqlApp.tollSubscription.vehicle">Vehicle</Translate>
            </span>
          </dt>
          <dd>{tollSubscriptionEntity.vehicle}</dd>
          <dt>
            <span id="vehicleOwner">
              <Translate contentKey="rfidh2MysqlApp.tollSubscription.vehicleOwner">Vehicle Owner</Translate>
            </span>
          </dt>
          <dd>{tollSubscriptionEntity.vehicleOwner}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="rfidh2MysqlApp.tollSubscription.active">Active</Translate>
            </span>
          </dt>
          <dd>{tollSubscriptionEntity.active}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.tollSubscription.updated">Updated</Translate>
            </span>
          </dt>
          <dd>
            {tollSubscriptionEntity.updated ? (
              <TextFormat value={tollSubscriptionEntity.updated} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="rfidh2MysqlApp.tollSubscription.tollPackage">Toll Package</Translate>
          </dt>
          <dd>{tollSubscriptionEntity.tollPackage ? tollSubscriptionEntity.tollPackage.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/toll-subscription" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/toll-subscription/${tollSubscriptionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TollSubscriptionDetail;
