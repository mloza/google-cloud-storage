import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.storage.Storage;
import com.google.api.services.storage.model.StorageObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.mloza.utils.CredentialsProvider;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by Michal
 * 04.10.2016.
 */
public class GoogleCloudStorageTest {
    private static final String APPLICATION_NAME = "Test Application";

    private HttpTransport httpTransport;
    private Credential credential;
    private JsonFactory jsonFactory;
    private Storage storage;

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

    @Test
    public void listBuckets() throws Exception {
        storage.buckets()
                .list("test-project")
                .execute()
                .getItems()
                .forEach(i -> System.out.println(i.getName()));
    }

    @Test
    public void listBucket() throws Exception {
        storage.objects()
                .list("blog-test")
                .execute()
                .getItems()
                .forEach(i -> System.out.println(i.getName()));
    }

    @Test
    public void listBucketWithPrefix() throws Exception {
        storage.objects()
                .list("blog-test")
                .setPrefix("fol")
                .execute()
                .getItems()
                .forEach(i -> System.out.println(i.getName()));
    }

    @Test
    public void getObjectFromBucket() throws Exception {
        storage.objects()
                .get("blog-test", "kot-z-serem.jpg")
                .executeMediaAndDownloadTo(new FileOutputStream("kot-z-serem.jpg"));
    }

    @Test
    public void uploadObjectToBucket() throws Exception {
        InputStreamContent mediaContent = new InputStreamContent(null, new FileInputStream("kot-z-serem-x.jpg"));
        storage.objects()
                .insert("blog-test", null, mediaContent)
                .setName("kot-z-serem-x.jpg")
                .execute();

        listBucket();
    }

    @Test
    public void deleteObjectToBucket() throws Exception {
        storage.objects()
                .delete("blog-test", "kot-z-serem-x.jpg")
                .execute();

        listBucket();
    }
}
