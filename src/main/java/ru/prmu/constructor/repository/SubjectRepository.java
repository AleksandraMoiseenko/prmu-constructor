package ru.prmu.constructor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prmu.constructor.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
