import com.google.api.client.http.InputStreamContent;
import com.google.api.services.storage.model.StorageObject;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.model.*;
import org.testng.annotations.Test;

import java.util.Collections;

/**
 * Created by Michal
 * 14.10.2016.
 */
public class GoogleVisionTest extends BaseTest {
    private final static String BUCKET = "blog-test";
    private final static String PICTURE_NAME = "paragon1.jpg";

    @Test
    public void shouldRecognizeText() throws Exception {
        InputStreamContent mediaContent = new InputStreamContent("image/jpeg",
                getClass().getClassLoader().getResourceAsStream(PICTURE_NAME));

        StorageObject object = storage
                .objects()
                .insert(BUCKET, null, mediaContent)
                .setName(PICTURE_NAME)
                .execute();

        System.out.println("Adres przesłanego obrazu: " + object.getSelfLink());

        Vision visionClient = new Vision.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        Image image = new Image().setSource(new ImageSource() //1
                .setGcsImageUri("gs://" + BUCKET + "/" + PICTURE_NAME));

        Feature annotateFeature = new Feature() //2
                .setType("TEXT_DETECTION");

        AnnotateImageRequest annotateImage = new AnnotateImageRequest()  // 3
                .setFeatures(Collections.singletonList(annotateFeature))
                .setImage(image);

        BatchAnnotateImagesResponse text_detection = visionClient // 4
                .images()
                .annotate(
                        new BatchAnnotateImagesRequest()
                                .setRequests(Collections.singletonList(annotateImage)))
                .execute();

        System.out.println(text_detection.getResponses().get(0).getTextAnnotations()); //5
    }
}
