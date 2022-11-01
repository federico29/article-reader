package clients;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;


public class S3Client {
    static String bucketName = System.getenv("BUCKET_NAME");
    static Regions clientRegion = Regions.US_EAST_1;

    public static String getArticleFile(String articleId) throws Exception {
        S3Object fullObject = null;
        S3ObjectInputStream objectContent = null;
        String article = "";
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .build();
            fullObject = s3Client.getObject(new GetObjectRequest(bucketName, articleId + ".html"));
            objectContent = fullObject.getObjectContent();
            article = IOUtils.toString(objectContent);
        } catch (AmazonServiceException e) {
            e.printStackTrace();
            throw new Exception();
        } finally {
            if (fullObject != null) {
                fullObject.close();
            }
            if (objectContent != null) {
                objectContent.close();
            }
        }

        return article;
    }
}
