package ru.prmu.constructor.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Tutor;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

    Page<Module> findByCourseId(Long courseId, Pageable pageable);

}
