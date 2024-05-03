package edu.stanford.protege.webprotege.filesub;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(RabbitTestExtension.class)
@ExtendWith(MongoTestExtension.class)
@ExtendWith(KeycloakTestExtension.class)
@ExtendWith(MinioTestExtension.class)
class WebProtegeFileSubmissionServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
