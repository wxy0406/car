import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getVehicleTypes } from 'app/entities/vehicle-type/vehicle-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './vehicle-type.reducer';
import { IVehicleType } from 'app/shared/model/vehicle-type.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IVehicleTypeUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IVehicleTypeUpdateState {
  isNew: boolean;
  vehicleTypeId: string;
}

export class VehicleTypeUpdate extends React.Component<IVehicleTypeUpdateProps, IVehicleTypeUpdateState> {
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
      const { vehicleTypeEntity } = this.props;
      const entity = {
        ...vehicleTypeEntity,
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
    this.props.history.push('/entity/vehicle-type');
  };

  render() {
    const { vehicleTypeEntity, vehicleTypes, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="carApp.vehicleType.home.createOrEditLabel">Create or edit a VehicleType</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : vehicleTypeEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="vehicle-type-id">ID</Label>
                    <AvInput id="vehicle-type-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="codeLabel" for="vehicle-type-code">
                    Code
                  </Label>
                  <AvField
                    id="vehicle-type-code"
                    type="text"
                    name="code"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="vehicle-type-name">
                    Name
                  </Label>
                  <AvField
                    id="vehicle-type-name"
                    type="text"
                    name="name"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="vehicle-type-vehicleType">Vehicle Type</Label>
                  <AvInput id="vehicle-type-vehicleType" type="select" className="form-control" name="vehicleType.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/vehicle-type" replace color="info">
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
  vehicleTypeEntity: storeState.vehicleType.entity,
  loading: storeState.vehicleType.loading,
  updating: storeState.vehicleType.updating,
  updateSuccess: storeState.vehicleType.updateSuccess
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
)(VehicleTypeUpdate);
