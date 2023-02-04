import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TollWhiteList from './toll-white-list';
import TollWhiteListDetail from './toll-white-list-detail';
import TollWhiteListUpdate from './toll-white-list-update';
import TollWhiteListDeleteDialog from './toll-white-list-delete-dialog';

const TollWhiteListRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TollWhiteList />} />
    <Route path="new" element={<TollWhiteListUpdate />} />
    <Route path=":id">
      <Route index element={<TollWhiteListDetail />} />
      <Route path="edit" element={<TollWhiteListUpdate />} />
      <Route path="delete" element={<TollWhiteListDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TollWhiteListRoutes;
