package edu.stanford.protege.webprotege.filesub;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2024-04-30
 */
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MinioTestExtension.class)
//@ExtendWith(RabbitTestExtension.class)
//@ExtendWith(KeycloakTestExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class StorageServiceIntegrationTest {

    @Autowired
    private StorageService storageService;

    @Autowired
    private MinioClient client;

    @Test
    public void testStoreFile() throws Exception {
        // Setup a temporary file
        var tempFile = Files.createTempFile("test-file-", ".txt");
        Files.write(tempFile, "This is a test file".getBytes());

        // Test storing the file
        var fileSubmissionId = storageService.storeFile(tempFile);
        assertThat(fileSubmissionId).isNotNull();

        // Cleanup
        Files.deleteIfExists(tempFile);

        var exists = client.bucketExists(BucketExistsArgs.builder().bucket("webprotege-uploads").build());
        assertThat(exists).isTrue();
    }

    @Test
    public void testStoreFileWhenMinioIsDown() throws Exception {
        // Simulate Minio down by incorrect port or server address
        storageService = new StorageService(MinioClient.builder()
                                                       .endpoint("http://localhost:9999")
                                                       .credentials("minioadmin", "minioadmin")
                                                       .build(),
                                            new MinioProperties());

        var tempFile = Files.createTempFile("test-file-", ".txt");
        Files.write(tempFile, "Content".getBytes());

        // Expect an exception since Minio is down
        assertThatThrownBy(() -> storageService.storeFile(tempFile))
                .isInstanceOf(StorageException.class);

        // Cleanup
        Files.deleteIfExists(tempFile);
    }
}

