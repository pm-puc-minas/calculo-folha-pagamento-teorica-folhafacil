import { HttpInterceptorFn, HttpErrorResponse  } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeyCloackService } from '../services/keycloack.service';
import { catchError, switchMap, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService = inject(KeyCloackService);
  const token = keycloakService.getToken();

  if (req.headers.has('skipAuth')) {
    const newHeaders = req.headers.delete('skipAuth');
    const newReq = req.clone({ headers: newHeaders });
    return next(newReq);
  }

  const authReq = token
    ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } })
    : req;

  return next(authReq).pipe(
    catchError((error: HttpErrorResponse) => {
      if (error.status === 401) {
        return keycloakService.refreshToken().pipe(
          switchMap((newToken) => {
            if (!newToken) {
              keycloakService.logout();
            }

            const retryReq = req.clone({
              setHeaders: { Authorization: `Bearer ${newToken}` }
            });
            return next(retryReq);
          }),
          catchError((refreshErr) => {
            console.error('Falha ao renovar o token', refreshErr);
            keycloakService.logout();
            return throwError(() => refreshErr);
          })
        );
      }

      return throwError(() => error);
    })
  );
};
