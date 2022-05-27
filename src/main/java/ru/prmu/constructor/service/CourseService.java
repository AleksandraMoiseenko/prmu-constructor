package ru.prmu.constructor.service;

import java.io.IOException;
import java.net.URISyntaxException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import ru.prmu.constructor.entity.Course;

public interface CourseService {

    void downloadBackupZip(Long id, HttpServletResponse response)
        throws IOException, URISyntaxException, InterruptedException;

    Page<Course> findAll(Integer page, Integer size);

    void deleteById(Long id);

    Course save (Course course);

    Course findById(Long id);

    Page<Course> findBySubjectId(Long subjectId, Integer page, Integer size);

}
