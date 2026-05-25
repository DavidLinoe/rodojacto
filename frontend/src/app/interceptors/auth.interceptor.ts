import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { TokenService } from '../services/token.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const tokenService = inject(TokenService);
  const token = tokenService.getToken();

  if (!token) {
    return next(req);
  }

  const decoded = tokenService.decodeToken(token);
  const organizationId = decoded?.organizationId;

  const shouldInjectOrganizationId =
    !!organizationId &&
    (req.method === 'POST' || req.method === 'PUT') &&
    req.body !== null &&
    typeof req.body === 'object' &&
    !(req.body instanceof FormData) &&
    !Array.isArray(req.body) &&
    !('organizationId' in (req.body as Record<string, unknown>));

  const body = shouldInjectOrganizationId
    ? { ...(req.body as Record<string, unknown>), organizationId }
    : req.body;

  const authReq = req.clone({
    body,
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(authReq);
};
