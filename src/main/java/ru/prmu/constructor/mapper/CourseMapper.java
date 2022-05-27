
package ru.prmu.constructor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.prmu.constructor.dto.CourseDto;
import ru.prmu.constructor.entity.Course;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper extends AMapper<CourseDto, Course> {
}
