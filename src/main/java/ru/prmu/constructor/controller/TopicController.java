package ru.prmu.constructor.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;
import ru.prmu.constructor.dto.ModuleDto;
import ru.prmu.constructor.dto.SubjectDto;
import ru.prmu.constructor.dto.TopicDto;
import ru.prmu.constructor.entity.Module;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.entity.Topic;
import ru.prmu.constructor.mapper.TopicMapper;
import ru.prmu.constructor.service.TopicService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/topics")
@Api(tags = "topic-api")
@Slf4j
@CrossOrigin
public class TopicController {

    private final TopicService topicService;
    private final TopicMapper topicMapper = Mappers.getMapper(TopicMapper.class);

    @PostMapping
    @ApiOperation("Api для сохранения темы модуля")
    public TopicDto save(@RequestBody TopicDto topic) {
        return topicMapper.toDTO(topicService.save(topicMapper.fromDTO(topic)));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Api для удаления темы модуля")
    public void delete(@PathVariable Long id) {
        topicService.delete(id);
    }

    @PostMapping("/{id}/upload")
    @ApiOperation("Api для прикрепления файла к теме")
    public void uploadFile(@RequestParam("file") MultipartFile file, @PathVariable Long id)
        throws IOException {
        topicService.upload(id, file);
    }

    @PutMapping
    @ApiOperation("Api для редактирования темы модуля")
    public TopicDto update(@RequestBody TopicDto topic) {
        return topicMapper.toDTO(topicService.save(topicMapper.fromDTO(topic)));
    }

    @GetMapping("/module/{id}")
    @ApiOperation("Api для получения всех тем модуля")
    public Page<TopicDto> findByModuleId(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer size) {
        Page<Topic> result = topicService.findByModuleId(id, page, size);
        return new PageImpl(result.getContent().stream().map(topicMapper::toDTO).collect(
            Collectors.toList()),
            PageRequest.of(page, size), result.getTotalElements());
    }

    @GetMapping("/{id}")
    @ApiOperation("Api для получения информации о теме по id")
    public TopicDto get(@PathVariable Long id) {
        Topic result = topicService.findById(id);
        return topicMapper.toDTO(result);
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

