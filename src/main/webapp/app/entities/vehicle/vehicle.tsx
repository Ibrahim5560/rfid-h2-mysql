import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVehicle } from 'app/shared/model/vehicle.model';
import { getEntities } from './vehicle.reducer';

export const Vehicle = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const vehicleList = useAppSelector(state => state.vehicle.entities);
  const loading = useAppSelector(state => state.vehicle.loading);
  const totalItems = useAppSelector(state => state.vehicle.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="vehicle-heading" data-cy="VehicleHeading">
        <Translate contentKey="rfidh2MysqlApp.vehicle.home.title">Vehicles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rfidh2MysqlApp.vehicle.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/vehicle/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rfidh2MysqlApp.vehicle.home.createLabel">Create new Vehicle</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vehicleList && vehicleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('govid')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.govid">Govid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('plate')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.plate">Plate</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('rfid')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.rfid">Rfid</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('driver')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.driver">Driver</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('producer')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.producer">Producer</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('model')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.model">Model</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('year')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.year">Year</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('color')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.color">Color</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('decals')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.decals">Decals</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('spoilers')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.spoilers">Spoilers</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('glass')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.glass">Glass</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('chassisSerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.chassisSerial">Chassis Serial</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('motorSerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.motorSerial">Motor Serial</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vehicleLicenseSerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.vehicleLicenseSerial">Vehicle License Serial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vehicleLicenseType')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.vehicleLicenseType">Vehicle License Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vehicleType')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.vehicleType">Vehicle Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('wanted')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.wanted">Wanted</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('licenseRevoked')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.licenseRevoked">License Revoked</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('licenseExpired')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.licenseExpired">License Expired</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jurisdiction')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.jurisdiction">Jurisdiction</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updated')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.updated">Updated</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fuelType')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.fuelType">Fuel Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('motorCc')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.motorCc">Motor Cc</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('licenseIssued')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.licenseIssued">License Issued</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('licenseExpires')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.licenseExpires">License Expires</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('wtKg')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.wtKg">Wt Kg</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passengers')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.passengers">Passengers</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('tractorParts')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.tractorParts">Tractor Parts</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('extraSizePercent')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.extraSizePercent">Extra Size Percent</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('wtExtra')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.wtExtra">Wt Extra</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('bikeData')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.bikeData">Bike Data</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('wantedBy')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.wantedBy">Wanted By</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('wantedFor')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.wantedFor">Wanted For</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('office')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.office">Office</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('statusName')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.statusName">Status Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('stolen')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.stolen">Stolen</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gantryToll')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicle.gantryToll">Gantry Toll</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vehicleList.map((vehicle, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vehicle/${vehicle.id}`} color="link" size="sm">
                      {vehicle.id}
                    </Button>
                  </td>
                  <td>{vehicle.govid}</td>
                  <td>{vehicle.plate}</td>
                  <td>{vehicle.rfid}</td>
                  <td>{vehicle.driver}</td>
                  <td>{vehicle.producer}</td>
                  <td>{vehicle.model}</td>
                  <td>{vehicle.year}</td>
                  <td>{vehicle.color}</td>
                  <td>{vehicle.decals}</td>
                  <td>{vehicle.spoilers}</td>
                  <td>{vehicle.glass}</td>
                  <td>{vehicle.chassisSerial}</td>
                  <td>{vehicle.motorSerial}</td>
                  <td>{vehicle.vehicleLicenseSerial}</td>
                  <td>{vehicle.vehicleLicenseType}</td>
                  <td>{vehicle.vehicleType}</td>
                  <td>{vehicle.wanted}</td>
                  <td>{vehicle.licenseRevoked}</td>
                  <td>{vehicle.licenseExpired}</td>
                  <td>{vehicle.jurisdiction}</td>
                  <td>{vehicle.updated ? <TextFormat type="date" value={vehicle.updated} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vehicle.fuelType}</td>
                  <td>{vehicle.motorCc}</td>
                  <td>
                    {vehicle.licenseIssued ? <TextFormat type="date" value={vehicle.licenseIssued} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {vehicle.licenseExpires ? <TextFormat type="date" value={vehicle.licenseExpires} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>{vehicle.wtKg}</td>
                  <td>{vehicle.passengers}</td>
                  <td>{vehicle.tractorParts}</td>
                  <td>{vehicle.extraSizePercent}</td>
                  <td>{vehicle.wtExtra}</td>
                  <td>{vehicle.bikeData}</td>
                  <td>{vehicle.wantedBy}</td>
                  <td>{vehicle.wantedFor}</td>
                  <td>{vehicle.office}</td>
                  <td>{vehicle.statusName}</td>
                  <td>{vehicle.stolen ? <TextFormat type="date" value={vehicle.stolen} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vehicle.gantryToll}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vehicle/${vehicle.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vehicle/${vehicle.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vehicle/${vehicle.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="rfidh2MysqlApp.vehicle.home.notFound">No Vehicles found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={vehicleList && vehicleList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Vehicle;
