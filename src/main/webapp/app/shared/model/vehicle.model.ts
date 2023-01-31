import dayjs from 'dayjs';

export interface IVehicle {
  id?: number;
  govid?: string | null;
  plate?: string;
  rfid?: string;
  driver?: number;
  producer?: string | null;
  model?: string | null;
  year?: number | null;
  color?: string | null;
  decals?: string | null;
  spoilers?: string | null;
  glass?: string | null;
  chassisSerial?: string | null;
  motorSerial?: string | null;
  vehicleLicenseSerial?: string | null;
  vehicleLicenseType?: number;
  vehicleType?: number;
  wanted?: number | null;
  licenseRevoked?: number | null;
  licenseExpired?: number | null;
  jurisdiction?: number;
  updated?: string;
  fuelType?: string | null;
  motorCc?: number | null;
  licenseIssued?: string | null;
  licenseExpires?: string | null;
  wtKg?: number | null;
  passengers?: number | null;
  tractorParts?: number | null;
  extraSizePercent?: number | null;
  wtExtra?: number | null;
  bikeData?: string | null;
  wantedBy?: number | null;
  wantedFor?: string | null;
  office?: number | null;
  statusName?: string | null;
  stolen?: string | null;
  gantryToll?: number;
}

export const defaultValue: Readonly<IVehicle> = {};
