import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPeakHourTariff } from 'app/shared/model/peak-hour-tariff.model';
import { getEntities } from './peak-hour-tariff.reducer';

export const PeakHourTariff = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const peakHourTariffList = useAppSelector(state => state.peakHourTariff.entities);
  const loading = useAppSelector(state => state.peakHourTariff.loading);
  const totalItems = useAppSelector(state => state.peakHourTariff.totalItems);

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
      <h2 id="peak-hour-tariff-heading" data-cy="PeakHourTariffHeading">
        <Translate contentKey="rfidh2MysqlApp.peakHourTariff.home.title">Peak Hour Tariffs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="rfidh2MysqlApp.peakHourTariff.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/peak-hour-tariff/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="rfidh2MysqlApp.peakHourTariff.home.createLabel">Create new Peak Hour Tariff</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {peakHourTariffList && peakHourTariffList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('peakHourFrom')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.peakHourFrom">Peak Hour From</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('peakHourTo')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.peakHourTo">Peak Hour To</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gantry')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.gantry">Gantry</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gantryToll')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.gantryToll">Gantry Toll</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('active')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.active">Active</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updated')}>
                  <Translate contentKey="rfidh2MysqlApp.peakHourTariff.updated">Updated</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {peakHourTariffList.map((peakHourTariff, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/peak-hour-tariff/${peakHourTariff.id}`} color="link" size="sm">
                      {peakHourTariff.id}
                    </Button>
                  </td>
                  <td>
                    {peakHourTariff.peakHourFrom ? (
                      <TextFormat type="date" value={peakHourTariff.peakHourFrom} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {peakHourTariff.peakHourTo ? (
                      <TextFormat type="date" value={peakHourTariff.peakHourTo} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{peakHourTariff.gantry}</td>
                  <td>{peakHourTariff.gantryToll}</td>
                  <td>{peakHourTariff.active}</td>
                  <td>
                    {peakHourTariff.updated ? <TextFormat type="date" value={peakHourTariff.updated} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/peak-hour-tariff/${peakHourTariff.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/peak-hour-tariff/${peakHourTariff.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`/peak-hour-tariff/${peakHourTariff.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="rfidh2MysqlApp.peakHourTariff.home.notFound">No Peak Hour Tariffs found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={peakHourTariffList && peakHourTariffList.length > 0 ? '' : 'd-none'}>
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

export default PeakHourTariff;
