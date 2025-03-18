//package com.example.Task.config;
//
//import org.keycloak.OAuth2Constants;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class KeycloakAdminClient {
//
//    @Value("${keycloak.auth-server-url}")
//    private String authServerUrl;
//
//    @Value("${keycloak.client-id}")
//    private String clientId;
//
//    @Value("${keycloak.admin-username}")
//    private String adminUsername;
//
//    @Value("${keycloak.admin-password}")
//    private String adminPassword;
//
//    public Keycloak getKeycloakInstance() {
//        return KeycloakBuilder.builder()
//                .serverUrl(authServerUrl)
//                .realm("master")
//                .clientId(clientId)
//                .username(adminUsername)
//                .password(adminPassword)
//                .grantType(OAuth2Constants.PASSWORD)
//                .build();
//    }
//}
