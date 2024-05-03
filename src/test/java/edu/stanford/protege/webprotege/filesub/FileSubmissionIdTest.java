package edu.stanford.protege.webprotege.filesub;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.core.ResolvableType;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2021-11-12
 */
public class FileSubmissionIdTest {

    private static final String THE_SUBMITTED_FILE_ID = "TheSubmittedFileId";

    private JacksonTester<FileSubmissionId> tester;

    @BeforeEach
    void setUp() {
        tester = new JacksonTester<>(FileSubmissionIdTest.class,
                                     ResolvableType.forClass(FileSubmissionId.class),
                                     new ObjectMapper());
    }

    @Test
    void shouldSerializeToJson() throws IOException {
        var value = new FileSubmissionId(THE_SUBMITTED_FILE_ID);
        var content = tester.write(value);
        assertThat(content.getJson()).isEqualTo("""
                                                        "TheSubmittedFileId"
                                                        """.strip());
    }

    @Test
    void shouldDeserializeFromJson() throws IOException {
        var json = """
                "TheSubmittedFileId"
                """;
        var parsed = tester.parse(json);
        assertThat(parsed.getObject()).isEqualTo(new FileSubmissionId(THE_SUBMITTED_FILE_ID));
    }
}
