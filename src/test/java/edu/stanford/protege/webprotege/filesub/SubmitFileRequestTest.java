package edu.stanford.protege.webprotege.filesub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import static org.assertj.core.api.Assertions.assertThat;

public class SubmitFileRequestTest {

    private JacksonTester<SubmitFileRequest> tester;

    @BeforeEach
    public void setup() {
        var objectMapper = new ObjectMapper();
        // Setup JacksonTester
        JacksonTester.initFields(this, objectMapper);
    }
//
//    @Test
//    public void testSerialization() throws Exception {
//        var request = new SubmitFileRequest("example.txt", new byte[] {1, 2, 3});
//        var jsonContent = tester.write(request);
//
//        assertThat(jsonContent).extractingJsonPathStringValue("$.fileName").asString().isEqualTo("example.txt");
//        assertThat(jsonContent).extractingJsonPathValue("$.bytes").asString().isEqualTo("AQID");
//    }
//
//    @Test
//    public void testDeserialization() throws Exception {
//        var content = """
//                {"fileName": "example.txt", "bytes": "AQID"}
//                """;
//        var request = tester.parse(content).getObject();
//
//        assertThat(request.fileName()).isEqualTo("example.txt");
//        assertThat(request.bytes()).containsExactly(new byte[] {1, 2, 3});
//    }
//
//    @Test
//    public void testChannel() {
//        var request = new SubmitFileRequest("example.txt", new byte[] {1, 2, 3});
//        assertThat(request.getChannel()).isEqualTo("webprotege.files.SubmitFile");
//    }
}
