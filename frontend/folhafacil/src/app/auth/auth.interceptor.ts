import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeyCloackService } from '../services/keycloack.service';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService = inject(KeyCloackService);
  const token = keycloakService.getToken();

  console.log("")

  if (req.headers.has('skipAuth')) {
    const newHeaders = req.headers.delete('skipAuth');
    const newReq = req.clone({ headers: newHeaders });
    return next(newReq);
  }

  if (token) {
    const cloned = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`
      }
    });
    return next(cloned);
  }

  return next(req);
};
