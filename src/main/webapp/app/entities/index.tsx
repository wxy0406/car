import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import VehicleInformation from './vehicle-information';
import VehicleType from './vehicle-type';
import RentalInformation from './rental-information';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/vehicle-information`} component={VehicleInformation} />
      <ErrorBoundaryRoute path={`${match.url}/vehicle-type`} component={VehicleType} />
      <ErrorBoundaryRoute path={`${match.url}/rental-information`} component={RentalInformation} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
