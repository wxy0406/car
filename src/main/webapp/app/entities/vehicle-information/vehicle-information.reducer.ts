import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVehicleInformation, defaultValue } from 'app/shared/model/vehicle-information.model';

export const ACTION_TYPES = {
  FETCH_VEHICLEINFORMATION_LIST: 'vehicleInformation/FETCH_VEHICLEINFORMATION_LIST',
  FETCH_VEHICLEINFORMATION: 'vehicleInformation/FETCH_VEHICLEINFORMATION',
  CREATE_VEHICLEINFORMATION: 'vehicleInformation/CREATE_VEHICLEINFORMATION',
  UPDATE_VEHICLEINFORMATION: 'vehicleInformation/UPDATE_VEHICLEINFORMATION',
  DELETE_VEHICLEINFORMATION: 'vehicleInformation/DELETE_VEHICLEINFORMATION',
  RESET: 'vehicleInformation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVehicleInformation>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type VehicleInformationState = Readonly<typeof initialState>;

// Reducer

export default (state: VehicleInformationState = initialState, action): VehicleInformationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VEHICLEINFORMATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VEHICLEINFORMATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VEHICLEINFORMATION):
    case REQUEST(ACTION_TYPES.UPDATE_VEHICLEINFORMATION):
    case REQUEST(ACTION_TYPES.DELETE_VEHICLEINFORMATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VEHICLEINFORMATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VEHICLEINFORMATION):
    case FAILURE(ACTION_TYPES.CREATE_VEHICLEINFORMATION):
    case FAILURE(ACTION_TYPES.UPDATE_VEHICLEINFORMATION):
    case FAILURE(ACTION_TYPES.DELETE_VEHICLEINFORMATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICLEINFORMATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICLEINFORMATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VEHICLEINFORMATION):
    case SUCCESS(ACTION_TYPES.UPDATE_VEHICLEINFORMATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VEHICLEINFORMATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/vehicle-informations';

// Actions

export const getEntities: ICrudGetAllAction<IVehicleInformation> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_VEHICLEINFORMATION_LIST,
  payload: axios.get<IVehicleInformation>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IVehicleInformation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VEHICLEINFORMATION,
    payload: axios.get<IVehicleInformation>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVehicleInformation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VEHICLEINFORMATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVehicleInformation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VEHICLEINFORMATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVehicleInformation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VEHICLEINFORMATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
