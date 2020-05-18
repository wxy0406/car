import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IRentalInformation, defaultValue } from 'app/shared/model/rental-information.model';

export const ACTION_TYPES = {
  FETCH_RENTALINFORMATION_LIST: 'rentalInformation/FETCH_RENTALINFORMATION_LIST',
  FETCH_RENTALINFORMATION: 'rentalInformation/FETCH_RENTALINFORMATION',
  CREATE_RENTALINFORMATION: 'rentalInformation/CREATE_RENTALINFORMATION',
  UPDATE_RENTALINFORMATION: 'rentalInformation/UPDATE_RENTALINFORMATION',
  DELETE_RENTALINFORMATION: 'rentalInformation/DELETE_RENTALINFORMATION',
  RESET: 'rentalInformation/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IRentalInformation>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type RentalInformationState = Readonly<typeof initialState>;

// Reducer

export default (state: RentalInformationState = initialState, action): RentalInformationState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_RENTALINFORMATION_LIST):
    case REQUEST(ACTION_TYPES.FETCH_RENTALINFORMATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_RENTALINFORMATION):
    case REQUEST(ACTION_TYPES.UPDATE_RENTALINFORMATION):
    case REQUEST(ACTION_TYPES.DELETE_RENTALINFORMATION):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_RENTALINFORMATION_LIST):
    case FAILURE(ACTION_TYPES.FETCH_RENTALINFORMATION):
    case FAILURE(ACTION_TYPES.CREATE_RENTALINFORMATION):
    case FAILURE(ACTION_TYPES.UPDATE_RENTALINFORMATION):
    case FAILURE(ACTION_TYPES.DELETE_RENTALINFORMATION):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_RENTALINFORMATION_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_RENTALINFORMATION):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_RENTALINFORMATION):
    case SUCCESS(ACTION_TYPES.UPDATE_RENTALINFORMATION):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_RENTALINFORMATION):
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

const apiUrl = 'api/rental-informations';

// Actions

export const getEntities: ICrudGetAllAction<IRentalInformation> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_RENTALINFORMATION_LIST,
  payload: axios.get<IRentalInformation>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IRentalInformation> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_RENTALINFORMATION,
    payload: axios.get<IRentalInformation>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IRentalInformation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_RENTALINFORMATION,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IRentalInformation> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_RENTALINFORMATION,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IRentalInformation> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_RENTALINFORMATION,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
