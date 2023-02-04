import dayjs from 'dayjs';

export interface ITollWhiteList {
  id?: number;
  name?: string | null;
  engName?: string | null;
  code?: string | null;
  active?: number;
  updated?: string;
  gantry?: number | null;
  vehicleLicenseType?: number | null;
  vehicle?: number | null;
  vehicleOwner?: number | null;
  passageTimes?: number;
}

export const defaultValue: Readonly<ITollWhiteList> = {};
