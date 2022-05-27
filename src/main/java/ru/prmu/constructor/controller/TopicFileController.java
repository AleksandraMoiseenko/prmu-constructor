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
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.prmu.constructor.dto.TutorDto;
import ru.prmu.constructor.mapper.TutorMapper;
import ru.prmu.constructor.service.TopicFileService;
import ru.prmu.constructor.service.TutorService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/files")
@Api(tags = "file-api")
@Slf4j
@CrossOrigin
public class TopicFileController {

    private final TopicFileService topicFileService;

    @DeleteMapping("/{id}")
    @ApiOperation("Api для удаления файла у темы")
    public void delete(@PathVariable Long id) {
        topicFileService.delete(id);
    }

    @GetMapping("/download/{id}")
    @ApiOperation("Api для скачивания файла темы")
    public ResponseEntity<byte[]> download(@PathVariable Long id) throws IOException {
         return topicFileService.download(id);
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

