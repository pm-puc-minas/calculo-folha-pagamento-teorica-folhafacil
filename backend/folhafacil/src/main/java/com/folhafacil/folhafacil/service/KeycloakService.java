package com.folhafacil.folhafacil.service;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KeycloakService {

    private final Keycloak keycloak;
    private final String realm = "folha-facil-realm";

    public KeycloakService() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://localhost:8081")
                .realm(realm)
                .clientId("folha-facil-app")
                .clientSecret("xjLWnPHFsMmc61h6ZiBRKZDmuA4yzZTe")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .build();
    }

    public void criarUsuario(String username, String email, String primeiroNome, String ultimoNome, String senha, String nomeGrupo) {
        UsersResource usersResource = keycloak.realm(realm).users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(primeiroNome);
        user.setLastName(ultimoNome);
        user.setEnabled(true);

        var response = usersResource.create(user);
        if (response.getStatus() == 201) {
            System.out.println("Usuário criado com sucesso!");

            String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(senha);
            credential.setTemporary(false);

            usersResource.get(userId).resetPassword(credential);

            if (nomeGrupo != null && !nomeGrupo.isBlank()) {
                adicionarUsuarioAoGrupo(userId, nomeGrupo);
            }
        } else {
            System.out.println("Erro ao criar usuário: " + response.getStatus());
        }
    }

    private void adicionarUsuarioAoGrupo(String userId, String nomeGrupo) {
        String nomeGrupoLimpo = nomeGrupo.trim();

        List<GroupRepresentation> grupos = keycloak.realm(realm).groups().groups();

        GroupRepresentation grupo = grupos.stream()
                .filter(g -> g.getName().equalsIgnoreCase(nomeGrupoLimpo))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Grupo não encontrado: " + nomeGrupoLimpo));

        keycloak.realm(realm).users().get(userId).joinGroup(grupo.getId());
    }
}
