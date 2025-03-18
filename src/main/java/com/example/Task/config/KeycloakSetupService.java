//package com.example.Task.config;
//
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.resource.RealmResource;
//import org.keycloak.admin.client.resource.UsersResource;
//import org.keycloak.representations.idm.CredentialRepresentation;
//import org.keycloak.representations.idm.RealmRepresentation;
//import org.keycloak.representations.idm.UserRepresentation;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//import java.util.List;
//
//@Component
//public class KeycloakSetupService {
//
//    private final KeycloakAdminClient keycloakAdminClient;
//
//    @Value("${keycloak.realm-name}")
//    private String realmName;
//
//    @Value("${keycloak.user.first-name}")
//    private String firstName;
//
//    @Value("${keycloak.user.email}")
//    private String email;
//
//    @Value("${keycloak.user.username}")
//    private String username;
//
//    @Value("${keycloak.user.default-password}")
//    private String defaultPassword;
//
//    public KeycloakSetupService(KeycloakAdminClient keycloakAdminClient) {
//        this.keycloakAdminClient = keycloakAdminClient;
//    }
//
//    @EventListener(ApplicationReadyEvent.class)
//    public void setupKeycloak() {
//        Keycloak keycloak = keycloakAdminClient.getKeycloakInstance();
//
//        // Step 1: Check if the realm exists
//        List<RealmRepresentation> existingRealms = keycloak.realms().findAll();
//        boolean realmExists = existingRealms.stream().anyMatch(r -> r.getRealm().equals(realmName));
//
//        if (!realmExists) {
//            RealmRepresentation realm = new RealmRepresentation();
//            realm.setRealm(realmName);
//            realm.setEnabled(true);
//            keycloak.realms().create(realm);
//            System.out.println("Realm Created: " + realmName);
//        }
//
//        RealmResource realmResource = keycloak.realm(realmName);
//        UsersResource usersResource = realmResource.users();
//
//        // Step 2: Check if the user exists
//        List<UserRepresentation> users = usersResource.search(username, true);
//        if (users.isEmpty()) {
//            UserRepresentation user = new UserRepresentation();
//            user.setUsername(username);
//            user.setFirstName(firstName);
//            user.setEmail(email);
//            user.setEnabled(true);
//            user.setEmailVerified(false);
//            user.setRequiredActions(Collections.singletonList("UPDATE_PASSWORD"));
//
//            usersResource.create(user);
//            System.out.println("User Created: " + username);
//        }
//
//        // Step 3: Reset Password
//        UserRepresentation user = usersResource.search(username, true).get(0);
//        String userId = user.getId();
//
//        CredentialRepresentation passwordCredential = new CredentialRepresentation();
//        passwordCredential.setTemporary(true); // Force user to change password on first login
//        passwordCredential.setType(CredentialRepresentation.PASSWORD);
//        passwordCredential.setValue(defaultPassword);
//
//        usersResource.get(userId).resetPassword(passwordCredential);
//        System.out.println("Password Updated for User: " + username);
//    }
//}
