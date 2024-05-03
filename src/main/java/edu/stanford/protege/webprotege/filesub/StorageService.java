package edu.stanford.protege.webprotege.filesub;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2021-11-12
 */
public class StorageService {

    private static final Logger logger = LoggerFactory.getLogger(StorageService.class);

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;


    public StorageService(MinioClient minioClient,
                          MinioProperties minioProperties) {
        this.minioClient = minioClient;
        this.minioProperties = minioProperties;
    }

    public FileSubmissionId storeFile(Path tempFile) {
        var fileIdentifier = UUID.randomUUID().toString();
        logger.info("Storing file ({}) with an identifier of {}", getFileSizeInMB(tempFile), fileIdentifier);
        createBucketIfNecessary();
        uploadObject(tempFile, fileIdentifier);
        return new FileSubmissionId(fileIdentifier);
    }

    private String getFileSizeInMB(Path tempFile) {
        try {
            return FileUtils.byteCountToDisplaySize(Files.size(tempFile));
        } catch (IOException e) {
            return "";
        }
    }

    private void uploadObject(Path tempFile, String fileIdentifier) {
        try {
            minioClient.uploadObject(UploadObjectArgs.builder()
                                                     .bucket(minioProperties.getBucketName())
                                                     .object(fileIdentifier)
                                                     .filename(tempFile.toString())
                                                     .build());
        } catch (MinioException | NoSuchAlgorithmException | InvalidKeyException | IOException e) {
            throw new StorageException(e);
        }
    }

    private void createBucketIfNecessary() {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            }
        } catch (MinioException | IOException | NoSuchAlgorithmException | IllegalArgumentException | InvalidKeyException e) {
            throw new StorageException(e);
        }
    }

}
