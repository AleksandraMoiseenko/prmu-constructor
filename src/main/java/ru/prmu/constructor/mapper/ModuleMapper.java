
package ru.prmu.constructor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.prmu.constructor.dto.ModuleDto;
import ru.prmu.constructor.entity.Module;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ModuleMapper extends AMapper<ModuleDto, Module> {
}
