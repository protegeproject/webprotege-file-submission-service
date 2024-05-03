package edu.stanford.protege.webprotege.filesub;

import edu.stanford.protege.webprotege.authorization.ActionId;
import edu.stanford.protege.webprotege.authorization.ApplicationResource;
import edu.stanford.protege.webprotege.authorization.Resource;
import edu.stanford.protege.webprotege.ipc.AuthorizedCommandHandler;
import edu.stanford.protege.webprotege.ipc.CommandHandler;
import edu.stanford.protege.webprotege.ipc.ExecutionContext;
import edu.stanford.protege.webprotege.ipc.WebProtegeHandler;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2024-04-26
 */
@WebProtegeHandler
public class SubmitFileHandler implements CommandHandler<SubmitFileRequest, SubmitFileResponse> {

    private final StorageService storageService;

    public SubmitFileHandler(StorageService storageService) {
        this.storageService = storageService;
    }

    @NotNull
    @Override
    public String getChannelName() {
        return SubmitFileRequest.CHANNEL;
    }

    @Override
    public Class<SubmitFileRequest> getRequestClass() {
        return SubmitFileRequest.class;
    }

    @Override
    public Mono<SubmitFileResponse> handleRequest(SubmitFileRequest request, ExecutionContext executionContext) {
        try {
            var tempFile = Files.createTempFile("webprotege-file-submission", null);
            var fileContentsInBase64 = request.fileContents();
            var bytes = Base64.getDecoder().decode(fileContentsInBase64);
            Files.write(tempFile, bytes);
            var fileSubmissionId = storageService.storeFile(tempFile);
            return Mono.just(new SubmitFileResponse(fileSubmissionId));
        } catch (IOException e) {
            return Mono.error(e);
        }
    }

//    @NotNull
//    @Override
//    public Resource getTargetResource(SubmitFileRequest request) {
//        return ApplicationResource.get();
//    }

//    @NotNull
//    @Override
//    public Collection<ActionId> getRequiredCapabilities() {
//        return Collections.singleton(ActionId.valueOf("SubmitFile"));
//    }
}
