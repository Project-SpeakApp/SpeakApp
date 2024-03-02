import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(
  keycloak: KeycloakService
) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8433' + '/auth',
        realm: 'SpeakApp',
        clientId: 'SpeakApp',
      },
      initOptions: {
        checkLoginIframe: false,
    }});
}
