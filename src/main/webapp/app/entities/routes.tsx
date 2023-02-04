import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vehicle from './vehicle';
import VehicleOwner from './vehicle-owner';
import VehicleLicenseType from './vehicle-license-type';
import Gantry from './gantry';
import TollWhiteList from './toll-white-list';
import TollPackage from './toll-package';
import TollSubscription from './toll-subscription';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="vehicle/*" element={<Vehicle />} />
        <Route path="vehicle-owner/*" element={<VehicleOwner />} />
        <Route path="vehicle-license-type/*" element={<VehicleLicenseType />} />
        <Route path="gantry/*" element={<Gantry />} />
        <Route path="toll-white-list/*" element={<TollWhiteList />} />
        <Route path="toll-package/*" element={<TollPackage />} />
        <Route path="toll-subscription/*" element={<TollSubscription />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
