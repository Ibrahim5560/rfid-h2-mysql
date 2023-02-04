import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TollSubscription from './toll-subscription';
import TollSubscriptionDetail from './toll-subscription-detail';
import TollSubscriptionUpdate from './toll-subscription-update';
import TollSubscriptionDeleteDialog from './toll-subscription-delete-dialog';

const TollSubscriptionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TollSubscription />} />
    <Route path="new" element={<TollSubscriptionUpdate />} />
    <Route path=":id">
      <Route index element={<TollSubscriptionDetail />} />
      <Route path="edit" element={<TollSubscriptionUpdate />} />
      <Route path="delete" element={<TollSubscriptionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TollSubscriptionRoutes;
