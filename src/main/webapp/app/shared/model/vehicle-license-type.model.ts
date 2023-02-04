import dayjs from 'dayjs';

export interface IVehicleLicenseType {
  id?: number;
  name?: string | null;
  rank?: number | null;
  engName?: string | null;
  code?: string | null;
  updated?: string | null;
  active?: number;
  gantryToll?: number;
}

export const defaultValue: Readonly<IVehicleLicenseType> = {};
