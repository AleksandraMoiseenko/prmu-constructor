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
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name = "";
    @Column(name = "code")
    private String code = "";
    @Column(name = "start_date")
    private LocalDate startDate = LocalDate.now();
    @Column(name = "end_date")
    private LocalDate endDate = LocalDate.now();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "module_tutor",
        joinColumns = {@JoinColumn(name = "module_id")
        },
        inverseJoinColumns = {@JoinColumn(name = "tutor_id")}
    )
    @JsonIgnoreProperties("modules")
    private List<Tutor> tutors = new ArrayList<>();
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("module")
    private List<Topic> topics = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    @JsonIgnore
    private Course course = new Course();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Module module = (Module) o;
        return Objects.equals(id, module.id) && Objects.equals(name, module.name)
            && Objects.equals(code, module.code) && Objects.equals(startDate,
            module.startDate) && Objects.equals(endDate, module.endDate)
            && Objects.equals(tutors, module.tutors) && Objects.equals(topics,
            module.topics) && Objects.equals(course, module.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, startDate, endDate, tutors, topics, course);
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
            ", topics=" + topics +
            ", course=" + course +
            '}';
    }
}
