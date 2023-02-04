import dayjs from 'dayjs';
import { ITollPackage } from 'app/shared/model/toll-package.model';

export interface ITollSubscription {
  id?: number;
  dateTimeFrom?: string;
  dateTimeTo?: string;
  vehicle?: number | null;
  vehicleOwner?: number | null;
  active?: number;
  updated?: string;
  tollPackage?: ITollPackage | null;
}

export const defaultValue: Readonly<ITollSubscription> = {};
