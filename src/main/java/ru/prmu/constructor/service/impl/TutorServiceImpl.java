package ru.prmu.constructor.service.impl;

import com.opencsv.CSVWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.prmu.constructor.dto.TutorDto;
import ru.prmu.constructor.entity.Subject;
import ru.prmu.constructor.entity.Tutor;
import ru.prmu.constructor.mapper.TutorMapper;
import ru.prmu.constructor.repository.TutorRepository;
import ru.prmu.constructor.service.TutorService;

@Service
@RequiredArgsConstructor
public class TutorServiceImpl implements TutorService {

    private final TutorRepository tutorRepository;
    private final TutorMapper tutorMapper = Mappers.getMapper(TutorMapper.class);
    private final String[] csvHeader = {"username", "password", "firstname", "lastname", "institution",
        "department", "city", "country", "maildisplay", "email"};
    private final String HEADER_KEY = "Content-Disposition";
    private final String HEADER_VALUE = "attachment; filename=tutors.csv";
    private final String CONTENT_TYPE = "text/csv";

    @Override
    public Tutor save(Tutor tutor) {
        return tutorRepository.save(tutor);
    }

    @Override
    public List<Tutor> findAll() {
        return tutorRepository.findAll();
    }

    @Override
    public List<Tutor> findByNameOrLastNameIgnoreCase(String regexp) {
        return tutorRepository.findByNameAndLastName("%" + regexp + "%");
    }

    @Override
    public void deleteById(Long id) {
        tutorRepository.deleteById(id);
    }

    @Override
    public List<Tutor> findByCourseId(Long courseId) {
        return tutorRepository.findByCourseId(courseId);
    }

    @Override
    public void exportCourseTutorsToCsv(Long courseId, HttpServletResponse response)
        throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEADER_KEY, HEADER_VALUE);

        List<TutorDto> tutors = findByCourseId(courseId).stream().map(tutorMapper::toDTO)
            .collect(Collectors.toList());

        OutputStream stream = response.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(streamWriter);

        csvWriter.writeNext(csvHeader);
        for (String[] s : toStringArray(tutors)) {
            csvWriter.writeNext(s);
        }
        csvWriter.close();
    }

    @Override
    public void exportTutorsToCsv(HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader(HEADER_KEY, HEADER_VALUE);

        List<TutorDto> tutors = findAll().stream().map(tutorMapper::toDTO)
            .collect(Collectors.toList());

        OutputStream stream = response.getOutputStream();
        OutputStreamWriter streamWriter = new OutputStreamWriter(stream, StandardCharsets.UTF_8);
        CSVWriter csvWriter = new CSVWriter(streamWriter);

        csvWriter.writeNext(csvHeader);
        for (String[] s : toStringArray(tutors)) {
            csvWriter.writeNext(s);
        }
        csvWriter.close();
    }

    @Override
    public Tutor findById(Long id) {
        return tutorRepository.getById(id);
    }

    @Override
    public Page<Tutor> findAll(Integer page, Integer size) {
        return tutorRepository.findAll(PageRequest.of(page, size));
    }

    private List<String[]> toStringArray(List<TutorDto> emps) {
        List<String[]> records = new ArrayList<>();

        Iterator<TutorDto> it = emps.iterator();
        while (it.hasNext()) {
            TutorDto emp = it.next();
            records.add(new String[]{emp.getUsername(), emp.getPassword(), emp.getFirstname(),
                emp.getLastname(), emp.getInstitution(), emp.getDepartment(), emp.getCity(),
                emp.getCountry(),
                emp.getMailDisplay() ? "1" : "0", emp.getEmail()});
        }
        return records;
    }
}
