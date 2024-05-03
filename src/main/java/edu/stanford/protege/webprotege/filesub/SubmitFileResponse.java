package edu.stanford.protege.webprotege.filesub;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import edu.stanford.protege.webprotege.common.Response;

import static edu.stanford.protege.webprotege.filesub.SubmitFileRequest.CHANNEL;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2024-04-26
 */
@JsonTypeName(CHANNEL)
public record SubmitFileResponse(@JsonProperty("fileSubmissionId") FileSubmissionId fileSubmissionId) implements Response {

}
