import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGantry } from 'app/shared/model/gantry.model';
import { getEntities } from './gantry.reducer';

export const Gantry = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const gantryList = useAppSelector(state => state.gantry.entities);
  const loading = useAppSelector(state => state.gantry.loading);
  const totalItems = useAppSelector(state => state.gantry.totalItems);

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
      <h2 id="gantry-heading" data-cy="GantryHeading">
        <Translate contentKey="rfidh2MysqlApp.gantry.home.title">Gantries</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rfidh2MysqlApp.gantry.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/gantry/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rfidh2MysqlApp.gantry.home.createLabel">Create new Gantry</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gantryList && gantryList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('route')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.route">Route</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lanes')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.lanes">Lanes</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('longitude')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.longitude">Longitude</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('lat')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.lat">Lat</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('jurisdiction')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.jurisdiction">Jurisdiction</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gantryType')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.gantryType">Gantry Type</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gantrySet')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.gantrySet">Gantry Set</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('active')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.active">Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('password')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.password">Password</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updated')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.updated">Updated</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('ip')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.ip">Ip</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('toll')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.toll">Toll</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passageTimes')}>
                  <Translate contentKey="rfidh2MysqlApp.gantry.passageTimes">Passage Times</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gantryList.map((gantry, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gantry/${gantry.id}`} color="link" size="sm">
                      {gantry.id}
                    </Button>
                  </td>
                  <td>{gantry.name}</td>
                  <td>{gantry.route}</td>
                  <td>{gantry.lanes}</td>
                  <td>{gantry.longitude}</td>
                  <td>{gantry.lat}</td>
                  <td>{gantry.jurisdiction}</td>
                  <td>{gantry.gantryType}</td>
                  <td>{gantry.gantrySet}</td>
                  <td>{gantry.active}</td>
                  <td>{gantry.password}</td>
                  <td>{gantry.updated ? <TextFormat type="date" value={gantry.updated} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{gantry.ip}</td>
                  <td>{gantry.toll}</td>
                  <td>{gantry.passageTimes}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/gantry/${gantry.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/gantry/${gantry.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/gantry/${gantry.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rfidh2MysqlApp.gantry.home.notFound">No Gantries found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={gantryList && gantryList.length > 0 ? '' : 'd-none'}>
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

export default Gantry;
