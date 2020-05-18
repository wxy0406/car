import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VehicleInformation from './vehicle-information';
import VehicleInformationDetail from './vehicle-information-detail';
import VehicleInformationUpdate from './vehicle-information-update';
import VehicleInformationDeleteDialog from './vehicle-information-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={VehicleInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={VehicleInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={VehicleInformationDetail} />
      <ErrorBoundaryRoute path={match.url} component={VehicleInformation} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={VehicleInformationDeleteDialog} />
  </>
);

export default Routes;
