import { KeycloakService } from "keycloak-angular";

export function initializeKeycloak(
  keycloak: KeycloakService
) {
  return () =>
    keycloak.init({
      config: {
        url: 'https://localhost:8443',
        realm: 'SpeakApp',
        clientId: 'SpeakAppClient',
      },
      initOptions: {
        checkLoginIframe: false,
        pkceMethod: 'S256',
      },
    });
}
