package ru.prmu.constructor.service.impl;

import com.ibm.icu.text.Transliterator;
import java.io.IOException;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.entity.TopicFile;
import ru.prmu.constructor.repository.TopicFileRepository;
import ru.prmu.constructor.repository.TopicRepository;
import ru.prmu.constructor.service.TopicService;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService {

    public static final String CYRILLIC_TO_LATIN = "Cyrillic-Latin";
    private final TopicRepository topicRepository;
    private final TopicFileRepository topicFileRepository;

    @Override
    public Topic save(Topic topic) {
        return topicRepository.save(topic);
    }

    @Override
    public void delete(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public void upload(Long topicId, MultipartFile file) throws IOException {
        Topic topic = topicRepository.getById(topicId);
        TopicFile topicFile = new TopicFile();
        topicFile.setName(transliterate(file.getOriginalFilename()));
        topicFile.setTopic(topic);
        byte[] encode = Base64.getEncoder().encode(file.getBytes());
        String result = new String(encode);
        topicFile.setContent(result);
        topicFile.setMediaType(file.getContentType());
        topicFileRepository.save(topicFile);
    }

    @Override
    public Page<Topic> findByModuleId(Long moduleId, Integer page, Integer size) {
        return topicRepository.findByModuleId(moduleId, PageRequest.of(page, size));
    }

    @Override
    public Topic findById(Long id) {
        return topicRepository.getById(id);
    }

    private static String transliterate(String message) {
        Transliterator toLatinTrans = Transliterator.getInstance(CYRILLIC_TO_LATIN);
        return toLatinTrans.transliterate(message);
    }

}
