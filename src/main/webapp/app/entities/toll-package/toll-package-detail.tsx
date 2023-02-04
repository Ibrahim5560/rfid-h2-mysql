import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './toll-package.reducer';

export const TollPackageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tollPackageEntity = useAppSelector(state => state.tollPackage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tollPackageDetailsHeading">
          <Translate contentKey="rfidh2MysqlApp.tollPackage.detail.title">TollPackage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.name">Name</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.name}</dd>
          <dt>
            <span id="engName">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.engName">Eng Name</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.engName}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.code">Code</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.code}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.active">Active</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.active}</dd>
          <dt>
            <span id="updated">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.updated">Updated</Translate>
            </span>
          </dt>
          <dd>
            {tollPackageEntity.updated ? <TextFormat value={tollPackageEntity.updated} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="durationInDays">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.durationInDays">Duration In Days</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.durationInDays}</dd>
          <dt>
            <span id="gantry">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.gantry">Gantry</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.gantry}</dd>
          <dt>
            <span id="vehicleLicenseType">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.vehicleLicenseType">Vehicle License Type</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.vehicleLicenseType}</dd>
          <dt>
            <span id="passageTimes">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.passageTimes">Passage Times</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.passageTimes}</dd>
          <dt>
            <span id="totalFees">
              <Translate contentKey="rfidh2MysqlApp.tollPackage.totalFees">Total Fees</Translate>
            </span>
          </dt>
          <dd>{tollPackageEntity.totalFees}</dd>
          <dt>
            <Translate contentKey="rfidh2MysqlApp.tollPackage.tollSubscription">Toll Subscription</Translate>
          </dt>
          <dd>{tollPackageEntity.tollSubscription ? tollPackageEntity.tollSubscription.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/toll-package" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/toll-package/${tollPackageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TollPackageDetail;
