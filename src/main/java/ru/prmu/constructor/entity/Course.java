package ru.prmu.constructor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name = "";
    @Column(name = "code")
    private String code = "";
    @Column(name = "description")
    private String description = "";
    @Column(name = "start_date")
    private LocalDate startDate = LocalDate.now();
    @Column(name = "end_date")
    private LocalDate endDate = LocalDate.now();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "course_tutor",
        joinColumns = {@JoinColumn(name = "course_id")
        },
        inverseJoinColumns = {@JoinColumn(name = "tutor_id")}
    )
    @JsonIgnoreProperties("courses")
    private List<Tutor> tutors = new ArrayList<>();
    @Column(name = "pass_criteria_description")
    private String passCriteriaDescription = "";
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("course")
    private List<Module> modules = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "subject_id")
    @JsonIgnore
    private Subject subject = new Subject();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(name, course.name)
            && Objects.equals(code, course.code) && Objects.equals(description,
            course.description) && Objects.equals(startDate, course.startDate)
            && Objects.equals(endDate, course.endDate) && Objects.equals(tutors,
            course.tutors) && Objects.equals(passCriteriaDescription,
            course.passCriteriaDescription) && Objects.equals(modules, course.modules)
            && Objects.equals(subject, course.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, description, startDate, endDate, tutors,
            passCriteriaDescription, modules, subject);
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
            ", modules=" + modules +
            ", subject=" + subject +
            '}';
    }
}
