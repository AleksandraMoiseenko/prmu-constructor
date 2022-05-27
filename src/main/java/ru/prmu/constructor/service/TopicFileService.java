package ru.prmu.constructor.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

public interface TopicFileService {

    void delete (Long id);
    ResponseEntity<byte[]> download (Long id) throws IOException;

}
