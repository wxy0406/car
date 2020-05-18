import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rental-information.reducer';
import { IRentalInformation } from 'app/shared/model/rental-information.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRentalInformationDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RentalInformationDetail extends React.Component<IRentalInformationDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { rentalInformationEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            RentalInformation [<b>{rentalInformationEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">Code</span>
            </dt>
            <dd>{rentalInformationEntity.code}</dd>
            <dt>
              <span id="preparedPeople">Prepared People</span>
            </dt>
            <dd>{rentalInformationEntity.preparedPeople}</dd>
            <dt>
              <span id="rentDate">Rent Date</span>
            </dt>
            <dd>{rentalInformationEntity.rentDate}</dd>
            <dt>
              <span id="rentalUnits">Rental Units</span>
            </dt>
            <dd>{rentalInformationEntity.rentalUnits}</dd>
            <dt>
              <span id="builder">Builder</span>
            </dt>
            <dd>{rentalInformationEntity.builder}</dd>
            <dt>
              <span id="rental">Rental</span>
            </dt>
            <dd>{rentalInformationEntity.rental}</dd>
            <dt>
              <span id="leaseTime">Lease Time</span>
            </dt>
            <dd>{rentalInformationEntity.leaseTime}</dd>
            <dt>
              <span id="remarks">Remarks</span>
            </dt>
            <dd>{rentalInformationEntity.remarks}</dd>
            <dt>Vehicle Information</dt>
            <dd>{rentalInformationEntity.vehicleInformation ? rentalInformationEntity.vehicleInformation.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/rental-information" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/rental-information/${rentalInformationEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ rentalInformation }: IRootState) => ({
  rentalInformationEntity: rentalInformation.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RentalInformationDetail);
