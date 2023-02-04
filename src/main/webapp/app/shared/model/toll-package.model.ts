import dayjs from 'dayjs';
import { ITollSubscription } from 'app/shared/model/toll-subscription.model';

export interface ITollPackage {
  id?: number;
  name?: string | null;
  engName?: string | null;
  code?: string | null;
  active?: number;
  updated?: string;
  durationInDays?: number | null;
  gantry?: number | null;
  vehicleLicenseType?: number | null;
  passageTimes?: number;
  totalFees?: number;
  tollSubscription?: ITollSubscription | null;
}

export const defaultValue: Readonly<ITollPackage> = {};
