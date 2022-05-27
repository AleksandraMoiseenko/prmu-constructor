package ru.prmu.constructor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.net.URISyntaxException;
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
import ru.prmu.constructor.dto.CourseDto;
import ru.prmu.constructor.entity.Course;
import ru.prmu.constructor.mapper.CourseMapper;
import ru.prmu.constructor.service.CourseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/courses")
@Api(tags = "course-api")
@Slf4j
@CrossOrigin
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper = Mappers.getMapper(CourseMapper.class);

    @GetMapping
    @ApiOperation("Api для получения всех курсов")
    public Page<CourseDto> findAll(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Course> result = courseService.findAll(page, size);
        return new PageImpl(
            result.getContent().stream().map(courseMapper::toDTO).collect(Collectors.toList()),
            PageRequest.of(page, size), result.getTotalElements());
    }

    @GetMapping("/{id}")
    @ApiOperation("Api для получения информации о курсе по id")
    public CourseDto get(@PathVariable Long id) {
        Course result = courseService.findById(id);
        return courseMapper.toDTO(result);
    }

    @GetMapping(value = "/backup/{id}", produces = "application/zip")
    @ApiOperation("Api для скачивания архива с курсом в формате .mbz")
    public void backup(@PathVariable Long id, HttpServletResponse response)
        throws IOException, URISyntaxException, InterruptedException {
        courseService.downloadBackupZip(id, response);
    }

    @PostMapping
    @ApiOperation("Api для создания курса")
    public CourseDto save(@RequestBody CourseDto course) {
        return courseMapper.toDTO(courseService.save(courseMapper.fromDTO(course)));
    }

    @PutMapping
    @ApiOperation("Api для редактирования курса")
    public CourseDto update(@RequestBody CourseDto course) {
        return courseMapper.toDTO(courseService.save(courseMapper.fromDTO(course)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Api для удаления курса по id")
    public void delete(@PathVariable Long id) {
        courseService.deleteById(id);
    }

    @GetMapping("/subject/{id}")
    @ApiOperation("Api для получения спика курсов по дисциплине")
    public Page<CourseDto> findBySubjectId(@PathVariable Long id, @RequestParam Integer page,
        @RequestParam Integer size) {
        Page<Course> result = courseService.findBySubjectId(id, page, size);
        return new PageImpl(
            result.getContent().stream().map(courseMapper::toDTO).collect(Collectors.toList()),
            PageRequest.of(page, size), result.getTotalElements());
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
