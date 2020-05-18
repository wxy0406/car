import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVehicleInformation } from 'app/shared/model/vehicle-information.model';
import { getEntities as getVehicleInformations } from 'app/entities/vehicle-information/vehicle-information.reducer';
import { getEntity, updateEntity, createEntity, reset } from './rental-information.reducer';
import { IRentalInformation } from 'app/shared/model/rental-information.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRentalInformationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRentalInformationUpdateState {
  isNew: boolean;
  vehicleInformationId: string;
}

export class RentalInformationUpdate extends React.Component<IRentalInformationUpdateProps, IRentalInformationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      vehicleInformationId: '0',
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

    this.props.getVehicleInformations();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { rentalInformationEntity } = this.props;
      const entity = {
        ...rentalInformationEntity,
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
    this.props.history.push('/entity/rental-information');
  };

  render() {
    const { rentalInformationEntity, vehicleInformations, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carApp.rentalInformation.home.createOrEditLabel">Create or edit a RentalInformation</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : rentalInformationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="rental-information-id">ID</Label>
                    <AvInput id="rental-information-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="rental-information-code">
                    Code
                  </Label>
                  <AvField
                    id="rental-information-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="preparedPeopleLabel" for="rental-information-preparedPeople">
                    Prepared People
                  </Label>
                  <AvField
                    id="rental-information-preparedPeople"
                    type="text"
                    name="preparedPeople"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="rentDateLabel" for="rental-information-rentDate">
                    Rent Date
                  </Label>
                  <AvField
                    id="rental-information-rentDate"
                    type="text"
                    name="rentDate"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="rentalUnitsLabel" for="rental-information-rentalUnits">
                    Rental Units
                  </Label>
                  <AvField
                    id="rental-information-rentalUnits"
                    type="text"
                    name="rentalUnits"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="builderLabel" for="rental-information-builder">
                    Builder
                  </Label>
                  <AvField
                    id="rental-information-builder"
                    type="text"
                    name="builder"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="rentalLabel" for="rental-information-rental">
                    Rental
                  </Label>
                  <AvField
                    id="rental-information-rental"
                    type="string"
                    className="form-control"
                    name="rental"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      min: { value: 0, errorMessage: 'This field should be at least 0.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="leaseTimeLabel" for="rental-information-leaseTime">
                    Lease Time
                  </Label>
                  <AvField
                    id="rental-information-leaseTime"
                    type="string"
                    className="form-control"
                    name="leaseTime"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' },
                      min: { value: 0, errorMessage: 'This field should be at least 0.' },
                      number: { value: true, errorMessage: 'This field should be a number.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="remarksLabel" for="rental-information-remarks">
                    Remarks
                  </Label>
                  <AvField id="rental-information-remarks" type="text" name="remarks" />
                </AvGroup>
                <AvGroup>
                  <Label for="rental-information-vehicleInformation">Vehicle Information</Label>
                  <AvInput id="rental-information-vehicleInformation" type="select" className="form-control" name="vehicleInformation.id">
                    <option value="" key="0" />
                    {vehicleInformations
                      ? vehicleInformations.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/rental-information" replace color="info">
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
  vehicleInformations: storeState.vehicleInformation.entities,
  rentalInformationEntity: storeState.rentalInformation.entity,
  loading: storeState.rentalInformation.loading,
  updating: storeState.rentalInformation.updating,
  updateSuccess: storeState.rentalInformation.updateSuccess
});

const mapDispatchToProps = {
  getVehicleInformations,
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
)(RentalInformationUpdate);
