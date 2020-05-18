import { IVehicleType } from 'app/shared/model/vehicle-type.model';

export const enum LeaseState {
  YES = 'YES',
  NO = 'NO'
}

export interface IVehicleInformation {
  id?: number;
  code?: string;
  name?: string;
  number?: string;
  purchaseDate?: string;
  price?: number;
  leaseState?: LeaseState;
  vehicleType?: IVehicleType;
}

export const defaultValue: Readonly<IVehicleInformation> = {};
