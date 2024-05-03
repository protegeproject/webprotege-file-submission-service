package edu.stanford.protege.webprotege.filesub;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MinIOContainer;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2024-04-28
 */
public class MinioTestExtension  implements BeforeAllCallback, AfterAllCallback  {

    private static Logger logger = LoggerFactory.getLogger(MinioTestExtension.class);

    private MinIOContainer container;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        container = new MinIOContainer("minio/minio:RELEASE.2024-04-06T05-26-02Z");
        container.start();

        var mappedHttpPort = container.getMappedPort(9000);
        logger.info("MinIO port 9000 is mapped to {}", mappedHttpPort);
        System.setProperty("webprotege.minio.endPoint", "http://localhost:" + mappedHttpPort);
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        if(container != null) {
            container.stop();
        }
    }
}
