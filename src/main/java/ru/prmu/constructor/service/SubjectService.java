package ru.prmu.constructor.service;

import java.util.List;
import org.springframework.data.domain.Page;
import ru.prmu.constructor.entity.Subject;

public interface SubjectService {

    Subject save(Subject subject);

    Subject findById(Long id);

    List<Subject> findAll();

    Page<Subject> findAll(Integer page, Integer size);

    void delete (Long id);

}
