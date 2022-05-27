
package ru.prmu.constructor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.prmu.constructor.dto.SubjectDto;
import ru.prmu.constructor.entity.Subject;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper extends AMapper<SubjectDto, Subject> {
}
