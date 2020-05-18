import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './vehicle-information.reducer';
import { IVehicleInformation } from 'app/shared/model/vehicle-information.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVehicleInformationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class VehicleInformation extends React.Component<IVehicleInformationProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { vehicleInformationList, match } = this.props;
    return (
      <div>
        <h2 id="vehicle-information-heading">
          Vehicle Informations
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Vehicle Information
          </Link>
        </h2>
        <div className="table-responsive">
          {vehicleInformationList && vehicleInformationList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Code</th>
                  <th>Name</th>
                  <th>Number</th>
                  <th>Purchase Date</th>
                  <th>Price</th>
                  <th>Lease State</th>
                  <th>Vehicle Type</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {vehicleInformationList.map((vehicleInformation, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${vehicleInformation.id}`} color="link" size="sm">
                        {vehicleInformation.id}
                      </Button>
                    </td>
                    <td>{vehicleInformation.code}</td>
                    <td>{vehicleInformation.name}</td>
                    <td>{vehicleInformation.number}</td>
                    <td>{vehicleInformation.purchaseDate}</td>
                    <td>{vehicleInformation.price}</td>
                    <td>{vehicleInformation.leaseState}</td>
                    <td>
                      {vehicleInformation.vehicleType ? (
                        <Link to={`vehicle-type/${vehicleInformation.vehicleType.id}`}>{vehicleInformation.vehicleType.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${vehicleInformation.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${vehicleInformation.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${vehicleInformation.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Vehicle Informations found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ vehicleInformation }: IRootState) => ({
  vehicleInformationList: vehicleInformation.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VehicleInformation);
