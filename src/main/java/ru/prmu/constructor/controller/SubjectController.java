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
import ru.prmu.constructor.dto.ModuleDto;
import ru.prmu.constructor.dto.SubjectDto;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.mapper.SubjectMapper;
import ru.prmu.constructor.service.SubjectService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/subjects")
@Api(tags = "subject-api")
@Slf4j
@CrossOrigin
public class SubjectController {

    private final SubjectService subjectService;
    private final SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);

    @PostMapping
    @ApiOperation("Api для сохранения дисциплины")
    public SubjectDto save(@RequestBody SubjectDto tutor) {
        return subjectMapper.toDTO(subjectService.save(subjectMapper.fromDTO(tutor)));
    }

    @PutMapping
    @ApiOperation("Api для редактирования дисциплины")
    public SubjectDto update(@RequestBody SubjectDto tutor) {
        return subjectMapper.toDTO(subjectService.save(subjectMapper.fromDTO(tutor)));
    }

    @GetMapping
    @ApiOperation("Api для получения списка дисциплин с пагинацией")
    public Page<SubjectDto> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Subject> result = subjectService.findAll(page, size);
        return new PageImpl(result.getContent().stream().map(subjectMapper::toDTO).collect(
            Collectors.toList()),
            PageRequest.of(page, size), result.getTotalElements());
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Api для удаления дисциплины")
    public void delete(@PathVariable Long id) {
        subjectService.delete(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("Api для получения информации о дисциплине по id")
    public SubjectDto get(@PathVariable Long id) {
        Subject result = subjectService.findById(id);
        return subjectMapper.toDTO(result);
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

