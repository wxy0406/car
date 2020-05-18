import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RentalInformation from './rental-information';
import RentalInformationDetail from './rental-information-detail';
import RentalInformationUpdate from './rental-information-update';
import RentalInformationDeleteDialog from './rental-information-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RentalInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RentalInformationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RentalInformationDetail} />
      <ErrorBoundaryRoute path={match.url} component={RentalInformation} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RentalInformationDeleteDialog} />
  </>
);

export default Routes;
