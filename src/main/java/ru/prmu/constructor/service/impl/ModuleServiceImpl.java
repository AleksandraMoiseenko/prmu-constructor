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
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.repository.CourseRepository;
import ru.prmu.constructor.repository.ModuleRepository;
import ru.prmu.constructor.service.CourseService;
import ru.prmu.constructor.service.ModuleService;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

    @Override
    public Module save(Module module) {
        return moduleRepository.save(module);
    }

    @Override
    public Page<Module> getByCourseId(Long courseId, Integer page, Integer size) {
        return moduleRepository.findByCourseId(courseId, PageRequest.of(page, size));
    }

    @Override
    public void delete(Long id) {
        moduleRepository.deleteById(id);
    }

    @Override
    public Module findById(Long id) {
        return moduleRepository.getById(id);
    }
}
