package ru.prmu.constructor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CourseDto implements Serializable {
    public static final long SerialVersionUID = 1L;
    private Long id;
    private String name = "";
    private String code = "";
    private String description = "";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate startDate = LocalDate.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate endDate = LocalDate.now();
    private List<TutorDto> tutors = new ArrayList<>();
    private String passCriteriaDescription = "";
    private SubjectDto subject = new SubjectDto();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CourseDto course = (CourseDto) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name)
            && Objects.equals(code, course.code) && Objects.equals(description,
            course.description) && Objects.equals(startDate, course.startDate)
            && Objects.equals(endDate, course.endDate) && Objects.equals(tutors,
            course.tutors) && Objects.equals(passCriteriaDescription,
            course.passCriteriaDescription) && Objects.equals(subject, course.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, description, startDate, endDate, tutors,
            passCriteriaDescription, subject);
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", description='" + description + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", tutors=" + tutors +
            ", passCriteriaDescription='" + passCriteriaDescription + '\'' +
            ", subject=" + subject +
            '}';
    }
}
