package edu.stanford.protege.webprotege.filesub;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2024-04-13
 */
public class KeycloakTestExtension implements BeforeAllCallback, AfterAllCallback {

    private KeycloakContainer keycloakContainer;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        keycloakContainer = new KeycloakContainer("quay.io/keycloak/keycloak:24.0").withRealmImportFile(
                "/keycloak/webprotege-realm--without--front-end-url.json");
        keycloakContainer.start();

        var url = keycloakContainer.getAuthServerUrl();

        System.setProperty("keycloak-issuer-url", url + "/realms/webprotege");

//        System.setProperty("spring.security.oauth2.client.registration.keycloak.redirectUri", keycloakContainer.getAuthServerUrl() + "/realms/webprotege");
        System.setProperty("spring.security.oauth2.client.provider.keycloak.issuer-uri",
                           url + "/realms/webprotege");
//        System.setProperty("spring.security.oauth2.client.provider.keycloak.tokenUri", keycloakContainer.getAuthServerUrl() + "/realms/webprotege/protocol/openid-connect/token");
        System.setProperty("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                           url + "/realms/webprotege");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        keycloakContainer.stop();
    }
}
