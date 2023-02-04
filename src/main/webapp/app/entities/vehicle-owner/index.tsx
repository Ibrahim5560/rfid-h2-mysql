import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import VehicleOwner from './vehicle-owner';
import VehicleOwnerDetail from './vehicle-owner-detail';
import VehicleOwnerUpdate from './vehicle-owner-update';
import VehicleOwnerDeleteDialog from './vehicle-owner-delete-dialog';

const VehicleOwnerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<VehicleOwner />} />
    <Route path="new" element={<VehicleOwnerUpdate />} />
    <Route path=":id">
      <Route index element={<VehicleOwnerDetail />} />
      <Route path="edit" element={<VehicleOwnerUpdate />} />
      <Route path="delete" element={<VehicleOwnerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VehicleOwnerRoutes;
