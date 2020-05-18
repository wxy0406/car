import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './rental-information.reducer';
import { IRentalInformation } from 'app/shared/model/rental-information.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRentalInformationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class RentalInformation extends React.Component<IRentalInformationProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { rentalInformationList, match } = this.props;
    return (
      <div>
        <h2 id="rental-information-heading">
          Rental Informations
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Rental Information
          </Link>
        </h2>
        <div className="table-responsive">
          {rentalInformationList && rentalInformationList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Code</th>
                  <th>Prepared People</th>
                  <th>Rent Date</th>
                  <th>Rental Units</th>
                  <th>Builder</th>
                  <th>Rental</th>
                  <th>Lease Time</th>
                  <th>Remarks</th>
                  <th>Vehicle Information</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {rentalInformationList.map((rentalInformation, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${rentalInformation.id}`} color="link" size="sm">
                        {rentalInformation.id}
                      </Button>
                    </td>
                    <td>{rentalInformation.code}</td>
                    <td>{rentalInformation.preparedPeople}</td>
                    <td>{rentalInformation.rentDate}</td>
                    <td>{rentalInformation.rentalUnits}</td>
                    <td>{rentalInformation.builder}</td>
                    <td>{rentalInformation.rental}</td>
                    <td>{rentalInformation.leaseTime}</td>
                    <td>{rentalInformation.remarks}</td>
                    <td>
                      {rentalInformation.vehicleInformation ? (
                        <Link to={`vehicle-information/${rentalInformation.vehicleInformation.id}`}>
                          {rentalInformation.vehicleInformation.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${rentalInformation.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${rentalInformation.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${rentalInformation.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Rental Informations found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ rentalInformation }: IRootState) => ({
  rentalInformationList: rentalInformation.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RentalInformation);
