package edu.stanford.protege.webprotege.filesub;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SubmitFileResponseTest {

    private JacksonTester<SubmitFileResponse> json;

    @BeforeEach
    public void setup() {
        var objectMapper = new ObjectMapper();
        // Register the JacksonTester
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    public void testSerialization() throws Exception {
        var uuid = UUID.randomUUID().toString();
        var id = new FileSubmissionId(uuid);
        var response = new SubmitFileResponse(id);

        var jsonContent = json.write(response);
        assertThat(jsonContent).extractingJsonPathStringValue("$.fileSubmissionId").asString().isEqualTo(uuid);
    }

    @Test
    public void testDeserializationAccepted() throws Exception {
        var jsonContent = """
            {
                "fileSubmissionId": "12345678-1234-1234-1234-123456789abc"
            }
            """;
        var response = json.parse(jsonContent).getObject();

        assertThat(response.fileSubmissionId()).isEqualTo(FileSubmissionId.valueOf("12345678-1234-1234-1234-123456789abc"));
    }
}
