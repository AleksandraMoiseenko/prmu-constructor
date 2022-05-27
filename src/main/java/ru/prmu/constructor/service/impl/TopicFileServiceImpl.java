package ru.prmu.constructor.service.impl;

import com.opencsv.CSVWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.prmu.constructor.dto.TutorDto;
import ru.prmu.constructor.entity.TopicFile;
import ru.prmu.constructor.entity.Tutor;
import ru.prmu.constructor.mapper.TutorMapper;
import ru.prmu.constructor.repository.TopicFileRepository;
import ru.prmu.constructor.repository.TutorRepository;
import ru.prmu.constructor.service.TopicFileService;
import ru.prmu.constructor.service.TutorService;

@Service
@RequiredArgsConstructor
public class TopicFileServiceImpl implements TopicFileService {

    private final TopicFileRepository topicFileRepository;

    @Override
    public void delete(Long id) {
        topicFileRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<byte[]> download(Long id) throws IOException {
        TopicFile topicFile = topicFileRepository.getById(id);
        String name = topicFile.getName();
        InputStreamResource in = new InputStreamResource(
            new ByteArrayInputStream(Base64.getDecoder().decode(topicFile.getContent())));
        byte[] bytes = IOUtils.toByteArray(in.getInputStream());
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + name + "\"")
            .contentType(MediaType.parseMediaType(topicFile.getMediaType()))
            .contentLength(bytes.length)
            .body(bytes);
    }
}
