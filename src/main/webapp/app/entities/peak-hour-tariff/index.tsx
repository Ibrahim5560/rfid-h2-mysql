import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PeakHourTariff from './peak-hour-tariff';
import PeakHourTariffDetail from './peak-hour-tariff-detail';
import PeakHourTariffUpdate from './peak-hour-tariff-update';
import PeakHourTariffDeleteDialog from './peak-hour-tariff-delete-dialog';

const PeakHourTariffRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PeakHourTariff />} />
    <Route path="new" element={<PeakHourTariffUpdate />} />
    <Route path=":id">
      <Route index element={<PeakHourTariffDetail />} />
      <Route path="edit" element={<PeakHourTariffUpdate />} />
      <Route path="delete" element={<PeakHourTariffDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PeakHourTariffRoutes;
