import vehicle from 'app/entities/vehicle/vehicle.reducer';
import vehicleOwner from 'app/entities/vehicle-owner/vehicle-owner.reducer';
import vehicleLicenseType from 'app/entities/vehicle-license-type/vehicle-license-type.reducer';
import gantry from 'app/entities/gantry/gantry.reducer';
import tollWhiteList from 'app/entities/toll-white-list/toll-white-list.reducer';
import tollPackage from 'app/entities/toll-package/toll-package.reducer';
import tollSubscription from 'app/entities/toll-subscription/toll-subscription.reducer';
import peakHourTariff from 'app/entities/peak-hour-tariff/peak-hour-tariff.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  vehicle,
  vehicleOwner,
  vehicleLicenseType,
  gantry,
  tollWhiteList,
  tollPackage,
  tollSubscription,
  peakHourTariff,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
