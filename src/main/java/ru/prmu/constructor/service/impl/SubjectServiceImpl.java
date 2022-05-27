package ru.prmu.constructor.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.prmu.constructor.entity.Course;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.repository.CourseRepository;
import ru.prmu.constructor.repository.SubjectRepository;
import ru.prmu.constructor.service.CourseService;
import ru.prmu.constructor.service.SubjectService;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Page<Subject> findAll(Integer page, Integer size) {
        return subjectRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }
}
