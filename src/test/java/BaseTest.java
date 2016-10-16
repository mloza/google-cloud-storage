import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import org.testng.annotations.BeforeClass;
import pl.mloza.utils.CredentialsProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Michal
 * 14.10.2016.
 */
public class BaseTest {
    protected static final String APPLICATION_NAME = "Test Application";

    protected HttpTransport httpTransport;
    protected Credential credential;
    protected JsonFactory jsonFactory;
    protected Storage storage;

    @BeforeClass
    public void setUp() throws Exception {
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            credential = CredentialsProvider.authorize();
            jsonFactory = new JacksonFactory();
            storage = new Storage.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}
