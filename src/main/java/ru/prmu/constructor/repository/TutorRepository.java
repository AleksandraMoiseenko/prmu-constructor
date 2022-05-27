package ru.prmu.constructor.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.prmu.constructor.entity.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

    @Query("select e from Tutor e where LOWER(firstname) LIKE LOWER(:regexp) or LOWER(lastname) LIKE LOWER(:regexp)")
    List<Tutor> findByNameAndLastName(String regexp);

    @Query("select e from Tutor e join e.courses c where c.id = :courseId")
    List<Tutor> findByCourseId(Long courseId);

}
