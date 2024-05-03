package edu.stanford.protege.webprotege.filesub;

import edu.stanford.protege.webprotege.common.WebProtegeCommonConfiguration;
import edu.stanford.protege.webprotege.ipc.WebProtegeIpcApplication;
import edu.stanford.protege.webprotege.jackson.WebProtegeJacksonApplication;
import io.minio.MinioClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({WebProtegeCommonConfiguration.class, WebProtegeIpcApplication.class, WebProtegeJacksonApplication.class})
public class WebProtegeFileSubmissionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProtegeFileSubmissionServiceApplication.class, args);
	}

	@Bean
	MinioClient minioClient(MinioProperties properties) {
		return MinioClient.builder()
						  .credentials(properties.getAccessKey(), properties.getSecretKey())
						  .endpoint(properties.getEndPoint())
						  .build();
	}

	@Bean
	StorageService storageService(MinioClient minioClient, MinioProperties properties) {
		return new StorageService(minioClient, properties);
	}

}
