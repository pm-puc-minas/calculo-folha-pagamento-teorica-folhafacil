import { inject } from "@angular/core";
import { CanMatchFn, Router, RedirectCommand } from "@angular/router";
import { KeyCloakService } from "../services/keycloak.service";

export const RoleCanMatch: CanMatchFn = (route, segments) => {
  const router = inject(Router);
  const keyCloakService = inject(KeyCloakService);

  const expectedRole = route.data?.['role'];

  if (!keyCloakService.isAuthenticated()) {
    return new RedirectCommand(router.parseUrl("/login"));
  }

  if (expectedRole && !keyCloakService.hasRole(expectedRole)) {
    return new RedirectCommand(router.parseUrl("/no-permission"));
  }

  return true;
};

export const AuthCanMatch: CanMatchFn = (route, segments) => {
  const router = inject(Router);
  const keyCloakService = inject(KeyCloakService);

  if (keyCloakService.isAuthenticated()) {
    return new RedirectCommand(router.parseUrl("/main"));
  }

  return true;
};