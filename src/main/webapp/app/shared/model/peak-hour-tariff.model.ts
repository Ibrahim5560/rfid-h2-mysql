import dayjs from 'dayjs';

export interface IPeakHourTariff {
  id?: number;
  peakHourFrom?: string;
  peakHourTo?: string;
  gantry?: number | null;
  gantryToll?: number;
  active?: number;
  updated?: string;
}

export const defaultValue: Readonly<IPeakHourTariff> = {};
