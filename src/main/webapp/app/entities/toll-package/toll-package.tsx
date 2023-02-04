import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITollPackage } from 'app/shared/model/toll-package.model';
import { getEntities } from './toll-package.reducer';

export const TollPackage = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const tollPackageList = useAppSelector(state => state.tollPackage.entities);
  const loading = useAppSelector(state => state.tollPackage.loading);
  const totalItems = useAppSelector(state => state.tollPackage.totalItems);

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
      <h2 id="toll-package-heading" data-cy="TollPackageHeading">
        <Translate contentKey="rfidh2MysqlApp.tollPackage.home.title">Toll Packages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rfidh2MysqlApp.tollPackage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/toll-package/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rfidh2MysqlApp.tollPackage.home.createLabel">Create new Toll Package</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tollPackageList && tollPackageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('engName')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.engName">Eng Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('code')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.code">Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('active')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.active">Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updated')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.updated">Updated</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('durationInDays')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.durationInDays">Duration In Days</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gantry')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.gantry">Gantry</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('vehicleLicenseType')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.vehicleLicenseType">Vehicle License Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passageTimes')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.passageTimes">Passage Times</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('totalFees')}>
                  <Translate contentKey="rfidh2MysqlApp.tollPackage.totalFees">Total Fees</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tollPackageList.map((tollPackage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/toll-package/${tollPackage.id}`} color="link" size="sm">
                      {tollPackage.id}
                    </Button>
                  </td>
                  <td>{tollPackage.name}</td>
                  <td>{tollPackage.engName}</td>
                  <td>{tollPackage.code}</td>
                  <td>{tollPackage.active}</td>
                  <td>{tollPackage.updated ? <TextFormat type="date" value={tollPackage.updated} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{tollPackage.durationInDays}</td>
                  <td>{tollPackage.gantry}</td>
                  <td>{tollPackage.vehicleLicenseType}</td>
                  <td>{tollPackage.passageTimes}</td>
                  <td>{tollPackage.totalFees}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/toll-package/${tollPackage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/toll-package/${tollPackage.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/toll-package/${tollPackage.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rfidh2MysqlApp.tollPackage.home.notFound">No Toll Packages found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={tollPackageList && tollPackageList.length > 0 ? '' : 'd-none'}>
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

export default TollPackage;
