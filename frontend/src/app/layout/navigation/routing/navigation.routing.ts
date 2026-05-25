import { Routes } from '@angular/router';
import { authGuard } from '../../../guards/auth-guard';

export const routes: Routes = [
  {
    path: 'sign-in',
    loadComponent: () =>
      import('../../../features/auth/containers/sign-in/sign-in.component').then(
        (m) => m.SignInComponent,
      ),
  },
  {
    path: 'sign-up',
    loadComponent: () =>
      import('../../../features/auth/containers/sign-up/sign-up.component').then(
        (m) => m.SignUpComponent,
      ),
  },
  {
    path: 'home',
    loadComponent: () =>
      import('../../../features/home/containers/home.component').then((m) => m.HomeComponent),
  },
  {
    path: 'collaborators',
    canActivate: [authGuard],
    loadComponent: () =>
      import('../../../features/collaborators/containers/collaborators.component').then((m) => m.CollaboratorsComponent),
  },
  {
    path: 'organizations',
    canActivate: [authGuard],
    loadComponent: () =>
      import('../../../features/organizations/containers/organizations.component').then(
        (m) => m.OrganizationsComponent,
      ),
  },
  {
    path: 'devices',
    canActivate: [authGuard],
    loadComponent: () =>
      import('../../../features/devices/containers/devices.component').then(
        (m) => m.DevicesComponent,
      ),
  },
  {
    path: '**',
    redirectTo: 'home',
  },
];
