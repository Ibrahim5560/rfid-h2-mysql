import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/vehicle">
        <Translate contentKey="global.menu.entities.vehicle" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vehicle-owner">
        <Translate contentKey="global.menu.entities.vehicleOwner" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/vehicle-license-type">
        <Translate contentKey="global.menu.entities.vehicleLicenseType" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/gantry">
        <Translate contentKey="global.menu.entities.gantry" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/toll-white-list">
        <Translate contentKey="global.menu.entities.tollWhiteList" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/toll-package">
        <Translate contentKey="global.menu.entities.tollPackage" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/toll-subscription">
        <Translate contentKey="global.menu.entities.tollSubscription" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/peak-hour-tariff">
        <Translate contentKey="global.menu.entities.peakHourTariff" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
