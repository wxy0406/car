import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVehicleType } from 'app/shared/model/vehicle-type.model';
import { getEntities as getVehicleTypes } from 'app/entities/vehicle-type/vehicle-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vehicle-information.reducer';
import { IVehicleInformation } from 'app/shared/model/vehicle-information.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVehicleInformationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVehicleInformationUpdateState {
  isNew: boolean;
  vehicleTypeId: string;
}

export class VehicleInformationUpdate extends React.Component<IVehicleInformationUpdateProps, IVehicleInformationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      vehicleTypeId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getVehicleTypes();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { vehicleInformationEntity } = this.props;
      const entity = {
        ...vehicleInformationEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/vehicle-information');
  };

  render() {
    const { vehicleInformationEntity, vehicleTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carApp.vehicleInformation.home.createOrEditLabel">Create or edit a VehicleInformation</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vehicleInformationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="vehicle-information-id">ID</Label>
                    <AvInput id="vehicle-information-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="vehicle-information-code">
                    Code
                  </Label>
                  <AvField
                    id="vehicle-information-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="vehicle-information-name">
                    Name
                  </Label>
                  <AvField
                    id="vehicle-information-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="numberLabel" for="vehicle-information-number">
                    Number
                  </Label>
                  <AvField
                    id="vehicle-information-number"
                    type="text"
                    name="number"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="purchaseDateLabel" for="vehicle-information-purchaseDate">
                    Purchase Date
                  </Label>
                  <AvField
                    id="vehicle-information-purchaseDate"
                    type="text"
                    name="purchaseDate"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="vehicle-information-price">
                    Price
                  </Label>
                  <AvField
                    id="vehicle-information-price"
                    type="string"
                    className="form-control"
                    name="price"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      min: { value: 0, errorMessage: 'This field should be at least 0.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="leaseStateLabel" for="vehicle-information-leaseState">
                    Lease State
                  </Label>
                  <AvInput
                    id="vehicle-information-leaseState"
                    type="select"
                    className="form-control"
                    name="leaseState"
                    value={(!isNew && vehicleInformationEntity.leaseState) || 'YES'}
                  >
                    <option value="YES">YES</option>
                    <option value="NO">NO</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="vehicle-information-vehicleType">Vehicle Type</Label>
                  <AvInput id="vehicle-information-vehicleType" type="select" className="form-control" name="vehicleType.id">
                    <option value="" key="0" />
                    {vehicleTypes
                      ? vehicleTypes.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/vehicle-information" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  vehicleTypes: storeState.vehicleType.entities,
  vehicleInformationEntity: storeState.vehicleInformation.entity,
  loading: storeState.vehicleInformation.loading,
  updating: storeState.vehicleInformation.updating,
  updateSuccess: storeState.vehicleInformation.updateSuccess
});

const mapDispatchToProps = {
  getVehicleTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleInformationUpdate);
