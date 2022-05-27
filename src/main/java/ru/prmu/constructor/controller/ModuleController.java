package ru.prmu.constructor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.prmu.constructor.dto.CourseDto;
import ru.prmu.constructor.dto.ModuleDto;
import ru.prmu.constructor.entity.Course;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.mapper.ModuleMapper;
import ru.prmu.constructor.service.ModuleService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/modules")
@Api(tags = "module-api")
@Slf4j
@CrossOrigin
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleMapper moduleMapper = Mappers.getMapper(ModuleMapper.class);

    @PostMapping
    @ApiOperation("Api для сохранения модуля курса")
    public ModuleDto save(@RequestBody ModuleDto tutor) {
        return moduleMapper.toDTO(moduleService.save(moduleMapper.fromDTO(tutor)));
    }

    @PutMapping
    @ApiOperation("Api для редактирования модуля курса")
    public ModuleDto update(@RequestBody ModuleDto tutor) {
        return moduleMapper.toDTO(moduleService.save(moduleMapper.fromDTO(tutor)));
    }

    @GetMapping("/course/{id}")
    @ApiOperation("Api для получения списка модулей курса с пагинацией")
    public Page<ModuleDto> findByCourseId(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size) {
        Page<Module> result = moduleService.getByCourseId(id, page, size);
        return new PageImpl(result.getContent().stream().map(moduleMapper::toDTO).collect(
            Collectors.toList()),
            PageRequest.of(page, size), result.getTotalElements());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Api для удаления модуля")
    public void delete (@PathVariable Long id) {
        moduleService.delete(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("Api для получения информации о модуле по id")
    public ModuleDto get(@PathVariable Long id) {
        Module result = moduleService.findById(id);
        return moduleMapper.toDTO(result);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleException(
        Exception exception
    ) {
        log.error("Возникло исключение {} ", exception);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Что-то пошло не так");
    }

}

