package ru.prmu.constructor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
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
import ru.prmu.constructor.dto.SubjectDto;
import ru.prmu.constructor.dto.TutorDto;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.entity.Tutor;
import ru.prmu.constructor.mapper.TutorMapper;
import ru.prmu.constructor.service.TutorService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tutors")
@Api(tags = "tutor-api")
@Slf4j
@CrossOrigin
public class TutorController {

    private final TutorService tutorService;
    private final TutorMapper tutorMapper = Mappers.getMapper(TutorMapper.class);

    @GetMapping
    @ApiOperation("Api для получения списка преподавателей")
    public List<TutorDto> findAll() {
        return tutorService.findAll().stream().map(tutorMapper::toDTO).collect(Collectors.toList());
    }

    @GetMapping("/page")
    @ApiOperation("Api для получения списка преподавателей с пагинацией")
    public Page<TutorDto> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Tutor> result = tutorService.findAll(page, size);
        return new PageImpl(result.getContent().stream().map(tutorMapper::toDTO).collect(
            Collectors.toList()),
            PageRequest.of(page, size), result.getTotalElements());
    }

    @GetMapping("/{regexp}")
    @ApiOperation("Api для поиска преподавателя по имени или фамилии")
    public List<TutorDto> findByNameOrLastName(@PathVariable String regexp) {
        return tutorService.findByNameOrLastNameIgnoreCase(regexp).stream().map(tutorMapper::toDTO)
            .collect(Collectors.toList());
    }

    @PostMapping
    @ApiOperation("Api для сохранения нового преподавателя")
    public TutorDto save(@RequestBody TutorDto tutor) {
        return tutorMapper.toDTO(tutorService.save(tutorMapper.fromDTO(tutor)));
    }

    @GetMapping("/export/course/{id}")
    @ApiOperation("Api для создания csv файла со списком преподавателей курса")
    public void exportToCSVByCourse(HttpServletResponse response, @PathVariable Long id)
        throws IOException {
        tutorService.exportCourseTutorsToCsv(id, response);
    }

    @GetMapping("/export")
    @ApiOperation("Api для создания csv файла со списком всех преподавателей")
    public void exportToCSVAll(HttpServletResponse response)
        throws IOException {
        tutorService.exportTutorsToCsv(response);

    }

    @DeleteMapping("/{id}")
    @ApiOperation("Api для удаления преподавателя")
    public void delete(@PathVariable Long id) {
        tutorService.deleteById(id);
    }

    @PutMapping
    @ApiOperation("Api для редактирования информации о преподавателе")
    public TutorDto update(@RequestBody TutorDto tutor) {
        return tutorMapper.toDTO(tutorService.save(tutorMapper.fromDTO(tutor)));
    }

    @GetMapping("/{id}")
    @ApiOperation("Api для получения информации о преподавателе по id")
    public TutorDto get(@PathVariable Long id) {
        Tutor result = tutorService.findById(id);
        return tutorMapper.toDTO(result);
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

