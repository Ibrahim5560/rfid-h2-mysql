import dayjs from 'dayjs';

export interface IGantry {
  id?: number;
  name?: string | null;
  route?: number | null;
  lanes?: number | null;
  longitude?: number | null;
  lat?: number | null;
  jurisdiction?: number | null;
  gantryType?: number | null;
  gantrySet?: number | null;
  active?: number | null;
  password?: string | null;
  updated?: string | null;
  ip?: string | null;
  toll?: number;
  passageTimes?: number;
}

export const defaultValue: Readonly<IGantry> = {};
