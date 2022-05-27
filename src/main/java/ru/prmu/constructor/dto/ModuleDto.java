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
public class ModuleDto implements Serializable {
    public static final long SerialVersionUID = 1L;
    private Long id;
    private String name = "";
    private String code = "";
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate startDate = LocalDate.now();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT")
    private LocalDate endDate = LocalDate.now();
    private List<TutorDto> tutors = new ArrayList<>();
    private CourseDto course = new CourseDto();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ModuleDto module = (ModuleDto) o;
        return Objects.equals(id, module.id) && Objects.equals(name, module.name)
            && Objects.equals(code, module.code) && Objects.equals(startDate,
            module.startDate) && Objects.equals(endDate, module.endDate)
            && Objects.equals(tutors, module.tutors) && Objects.equals(course,
            module.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, startDate, endDate, tutors, course);
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", code='" + code + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", tutors=" + tutors +
            ", course=" + course +
            '}';
    }
}
