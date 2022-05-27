package ru.prmu.constructor.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.entity.Tutor;

public interface TutorService {

    Tutor save(Tutor tutor);

    List<Tutor> findAll();

    List<Tutor> findByNameOrLastNameIgnoreCase(String regexp);

    void deleteById(Long id);

    List<Tutor> findByCourseId(Long courseId);

    void exportCourseTutorsToCsv(Long courseId, HttpServletResponse response) throws IOException;

    void exportTutorsToCsv(HttpServletResponse response) throws IOException;

    Tutor findById (Long id);

    Page<Tutor> findAll(Integer page, Integer size);

}
