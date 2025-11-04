import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { finalize } from 'rxjs';
import { ActionsService } from '../../services/actions.service';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const service = inject(ActionsService);
  const skipLoader = req.headers.has('skipLoader');

  const newReq = skipLoader
    ? req.clone({ headers: req.headers.delete('skipLoader') })
    : req;

  if (!skipLoader) service.showLoad();

  return next(newReq).pipe(
    finalize(() => {
      if (!skipLoader) service.hideLoad();
    })
  );
};
