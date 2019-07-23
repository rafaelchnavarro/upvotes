import { Route } from '@angular/router';

import { JhiMetricsMonitoringComponent } from './up-votes.component';

export const upVotesRoute: Route = {
  path: 'jhi-up-votes',
  component: JhiMetricsMonitoringComponent,
  data: {
    pageTitle: 'up-votes.title'
  }
};
