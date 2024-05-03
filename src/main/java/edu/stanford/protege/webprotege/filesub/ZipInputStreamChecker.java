package edu.stanford.protege.webprotege.filesub;

import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 2024-05-02
 */
@Component
public class ZipInputStreamChecker {

    private static final char ZIP_FILE_MAGIC_NUMBER_BYTE_0 = 'P';

    private static final char ZIP_FILE_MAGIC_NUMBER_BYTE_1 = 'K';

    public ZipInputStreamChecker() {
    }

    public boolean isZipFile(Path file) throws IOException {
        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(file))) {
            return isZipInputStream(bufferedInputStream);
        }
    }

    private boolean isZipInputStream(BufferedInputStream in) throws IOException {
        in.mark(2);
        char ch0 = (char) in.read();
        char ch1 = (char) in.read();
        in.reset();
        return ch0 == ZIP_FILE_MAGIC_NUMBER_BYTE_0 && ch1 == ZIP_FILE_MAGIC_NUMBER_BYTE_1;
    }
}

