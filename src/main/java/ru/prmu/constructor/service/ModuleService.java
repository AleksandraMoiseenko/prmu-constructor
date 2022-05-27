package ru.prmu.constructor.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Tutor;

public interface ModuleService {

    Module save(Module module);

    Page<Module> getByCourseId(Long courseId, Integer page, Integer size);

    void delete (Long id);

    Module findById(Long id);

}
