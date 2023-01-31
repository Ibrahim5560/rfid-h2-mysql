import vehicle from 'app/entities/vehicle/vehicle.reducer';
import vehicleOwner from 'app/entities/vehicle-owner/vehicle-owner.reducer';
import vehicleLicenseType from 'app/entities/vehicle-license-type/vehicle-license-type.reducer';
import gantry from 'app/entities/gantry/gantry.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  vehicle,
  vehicleOwner,
  vehicleLicenseType,
  gantry,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
