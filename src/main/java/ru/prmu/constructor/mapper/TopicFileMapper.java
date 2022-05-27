
package ru.prmu.constructor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.prmu.constructor.dto.TopicFileDto;
import ru.prmu.constructor.entity.TopicFile;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TopicFileMapper extends AMapper<TopicFileDto, TopicFile> {
}
