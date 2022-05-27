package ru.prmu.constructor.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.entity.Tutor;

public interface TopicService {

    Topic save(Topic topic);
    void delete(Long id);
    void upload(Long topicId, MultipartFile file) throws IOException;
    Page<Topic> findByModuleId(Long moduleId, Integer page, Integer size);
    Topic findById(Long id);

}
