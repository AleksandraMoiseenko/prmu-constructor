package ru.prmu.constructor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.entity.TopicFile;

@Repository
public interface TopicFileRepository extends JpaRepository<TopicFile, Long> {

}
