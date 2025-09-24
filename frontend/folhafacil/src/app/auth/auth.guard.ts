import { inject } from "@angular/core";
import { CanMatchFn, Router, RedirectCommand } from "@angular/router";
import { KeyCloackService } from "../services/keycloack.service";

export const RoleCanMatch: CanMatchFn = (route, segments) => {
  const router = inject(Router);
  const keycloackService = inject(KeyCloackService);

  const expectedRole = route.data?.['role'];

  if (!keycloackService.isAuthenticated()) {
    return new RedirectCommand(router.parseUrl("/login"));
  }

  if (expectedRole && !keycloackService.hasRole(expectedRole)) {
    return new RedirectCommand(router.parseUrl("/no-permission"));
  }

  return true;
};

export const AuthCanMatch: CanMatchFn = (route, segments) => {
  const router = inject(Router);
  const keycloackService = inject(KeyCloackService);

  if (keycloackService.isAuthenticated()) {
    return new RedirectCommand(router.parseUrl("/main"));
  }

  return true;
};