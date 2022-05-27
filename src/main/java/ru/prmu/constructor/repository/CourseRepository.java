package ru.prmu.constructor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prmu.constructor.entity.Course;
import ru.prmu.constructor.entity.Module;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findBySubjectId(Long subjectId, Pageable pageable);

}
