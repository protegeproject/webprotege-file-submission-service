package edu.stanford.protege.webprotege.filesub;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public record FileSubmissionId(String id) {

    @JsonCreator
    public static FileSubmissionId valueOf(String id) {
        return new FileSubmissionId(id);
    }

    @Override
    @JsonValue
    public String id() {
        return id;
    }
}
