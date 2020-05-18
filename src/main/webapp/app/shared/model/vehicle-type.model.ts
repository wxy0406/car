import { IVehicleType } from 'app/shared/model/vehicle-type.model';

export interface IVehicleType {
  id?: number;
  code?: string;
  name?: string;
  vehicleType?: IVehicleType;
}

export const defaultValue: Readonly<IVehicleType> = {};
