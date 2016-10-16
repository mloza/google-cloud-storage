import com.google.api.client.http.InputStreamContent;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Michal
 * 04.10.2016.
 */
public class GoogleCloudStorageTest extends BaseTest {
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
