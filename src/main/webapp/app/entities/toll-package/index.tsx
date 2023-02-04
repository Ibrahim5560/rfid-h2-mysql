import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TollPackage from './toll-package';
import TollPackageDetail from './toll-package-detail';
import TollPackageUpdate from './toll-package-update';
import TollPackageDeleteDialog from './toll-package-delete-dialog';

const TollPackageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TollPackage />} />
    <Route path="new" element={<TollPackageUpdate />} />
    <Route path=":id">
      <Route index element={<TollPackageDetail />} />
      <Route path="edit" element={<TollPackageUpdate />} />
      <Route path="delete" element={<TollPackageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TollPackageRoutes;
