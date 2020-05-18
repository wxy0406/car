import { IVehicleInformation } from 'app/shared/model/vehicle-information.model';

export interface IRentalInformation {
  id?: number;
  code?: string;
  preparedPeople?: string;
  rentDate?: string;
  rentalUnits?: string;
  builder?: string;
  rental?: number;
  leaseTime?: number;
  remarks?: string;
  vehicleInformation?: IVehicleInformation;
}

export const defaultValue: Readonly<IRentalInformation> = {};
