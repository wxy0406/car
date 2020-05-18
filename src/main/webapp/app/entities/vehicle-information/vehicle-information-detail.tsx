import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './vehicle-information.reducer';
import { IVehicleInformation } from 'app/shared/model/vehicle-information.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehicleInformationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VehicleInformationDetail extends React.Component<IVehicleInformationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { vehicleInformationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            VehicleInformation [<b>{vehicleInformationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{vehicleInformationEntity.code}</dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{vehicleInformationEntity.name}</dd>
            <dt>
              <span id="number">Number</span>
            </dt>
            <dd>{vehicleInformationEntity.number}</dd>
            <dt>
              <span id="purchaseDate">Purchase Date</span>
            </dt>
            <dd>{vehicleInformationEntity.purchaseDate}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{vehicleInformationEntity.price}</dd>
            <dt>
              <span id="leaseState">Lease State</span>
            </dt>
            <dd>{vehicleInformationEntity.leaseState}</dd>
            <dt>Vehicle Type</dt>
            <dd>{vehicleInformationEntity.vehicleType ? vehicleInformationEntity.vehicleType.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/vehicle-information" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/vehicle-information/${vehicleInformationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ vehicleInformation }: IRootState) => ({
  vehicleInformationEntity: vehicleInformation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleInformationDetail);
