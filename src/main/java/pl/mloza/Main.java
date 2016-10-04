package pl.mloza;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.StorageScopes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

/**
 * Created by Michal
 * 04.10.2016.
 */
public class Main {
    public static void main(String[] args) {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            Credential credential = GoogleCredential.fromStream(Main.class.getClassLoader().getResourceAsStream("client-secrets.json"))
                    .createScoped(Collections.singleton(StorageScopes.CLOUD_PLATFORM));
            JsonFactory jsonFactory = new JacksonFactory();

            Storage storage = new Storage.Builder(httpTransport, jsonFactory, credential).setApplicationName("Test project").build();
            storage.buckets().list("blog-test").execute().getItems().forEach(i -> System.out.println(i.getName()));
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }


    }
}
