package com.speakapp;

import com.speakapp.dtos.AppUserDTO;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

public class RegisterEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;
    private final RealmProvider model;

    public RegisterEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {

        if (EventType.REGISTER.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel user = this.session.users().getUserById(realm, event.getUserId());
            AppUserDTO appUserDTO = AppUserDTO.fromUserModel(user);
            Client.postService(appUserDTO);
        }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

        if (ResourceType.USER.equals(adminEvent.getResourceType())
                && OperationType.CREATE.equals(adminEvent.getOperationType())) {
            RealmModel realm = this.model.getRealm(adminEvent.getRealmId());
            UserModel user = this.session.users().getUserById(realm, adminEvent.getResourcePath().substring(6));
            AppUserDTO appUserDTO = AppUserDTO.fromAdminUserModel(user);
            Client.postService(appUserDTO);
        }
    }

    @Override
    public void close() {}
}
