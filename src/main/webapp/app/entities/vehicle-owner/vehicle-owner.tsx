import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVehicleOwner } from 'app/shared/model/vehicle-owner.model';
import { getEntities } from './vehicle-owner.reducer';

export const VehicleOwner = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const vehicleOwnerList = useAppSelector(state => state.vehicleOwner.entities);
  const loading = useAppSelector(state => state.vehicleOwner.loading);
  const totalItems = useAppSelector(state => state.vehicleOwner.totalItems);

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
      <h2 id="vehicle-owner-heading" data-cy="VehicleOwnerHeading">
        <Translate contentKey="rfidh2MysqlApp.vehicleOwner.home.title">Vehicle Owners</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rfidh2MysqlApp.vehicleOwner.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/vehicle-owner/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rfidh2MysqlApp.vehicleOwner.home.createLabel">Create new Vehicle Owner</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vehicleOwnerList && vehicleOwnerList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('citizenSerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.citizenSerial">Citizen Serial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passportSerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.passportSerial">Passport Serial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('entitySerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.entitySerial">Entity Serial</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('fullName')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.fullName">Full Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('firstName')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.firstName">First Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('middleName')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.middleName">Middle Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('grandName')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.grandName">Grand Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('surname')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.surname">Surname</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('aka')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.aka">Aka</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dob')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.dob">Dob</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gender')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('driverLicenseSerial')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.driverLicenseSerial">Driver License Serial</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('driverLicenseType')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.driverLicenseType">Driver License Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jurisdiction')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.jurisdiction">Jurisdiction</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updated')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.updated">Updated</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('wallet')}>
                  <Translate contentKey="rfidh2MysqlApp.vehicleOwner.wallet">Wallet</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vehicleOwnerList.map((vehicleOwner, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vehicle-owner/${vehicleOwner.id}`} color="link" size="sm">
                      {vehicleOwner.id}
                    </Button>
                  </td>
                  <td>{vehicleOwner.citizenSerial}</td>
                  <td>{vehicleOwner.passportSerial}</td>
                  <td>{vehicleOwner.entitySerial}</td>
                  <td>{vehicleOwner.fullName}</td>
                  <td>{vehicleOwner.firstName}</td>
                  <td>{vehicleOwner.middleName}</td>
                  <td>{vehicleOwner.grandName}</td>
                  <td>{vehicleOwner.surname}</td>
                  <td>{vehicleOwner.aka}</td>
                  <td>{vehicleOwner.dob ? <TextFormat type="date" value={vehicleOwner.dob} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vehicleOwner.gender}</td>
                  <td>{vehicleOwner.driverLicenseSerial}</td>
                  <td>{vehicleOwner.driverLicenseType}</td>
                  <td>{vehicleOwner.jurisdiction}</td>
                  <td>{vehicleOwner.updated ? <TextFormat type="date" value={vehicleOwner.updated} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{vehicleOwner.wallet}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vehicle-owner/${vehicleOwner.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/vehicle-owner/${vehicleOwner.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/vehicle-owner/${vehicleOwner.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rfidh2MysqlApp.vehicleOwner.home.notFound">No Vehicle Owners found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={vehicleOwnerList && vehicleOwnerList.length > 0 ? '' : 'd-none'}>
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

export default VehicleOwner;
