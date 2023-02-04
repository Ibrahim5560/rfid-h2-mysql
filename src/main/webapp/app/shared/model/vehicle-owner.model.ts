import dayjs from 'dayjs';

export interface IVehicleOwner {
  id?: number;
  citizenSerial?: string | null;
  passportSerial?: string | null;
  entitySerial?: string | null;
  fullName?: string | null;
  firstName?: string | null;
  middleName?: string | null;
  grandName?: string | null;
  surname?: string | null;
  aka?: string | null;
  dob?: string | null;
  gender?: string | null;
  driverLicenseSerial?: string | null;
  driverLicenseType?: number | null;
  jurisdiction?: number | null;
  updated?: string | null;
  wallet?: number;
}

export const defaultValue: Readonly<IVehicleOwner> = {};
