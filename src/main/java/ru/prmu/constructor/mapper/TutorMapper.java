
package ru.prmu.constructor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.prmu.constructor.dto.TutorDto;
import ru.prmu.constructor.entity.Tutor;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TutorMapper extends AMapper<TutorDto, Tutor> {
}
