package pl.mloza.utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.repackaged.com.google.common.base.Throwables;
import com.google.api.services.storage.StorageScopes;

import java.io.IOException;
import java.util.Collections;

public class CredentialsProvider {
    public static Credential authorize() {
        try {
            return GoogleCredential.fromStream(CredentialsProvider.class.getClassLoader().getResourceAsStream("client-secrets.json"))
                    .createScoped(Collections.singleton(StorageScopes.CLOUD_PLATFORM));
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        return null;
    }
}