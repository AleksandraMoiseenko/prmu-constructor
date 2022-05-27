package ru.prmu.constructor.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name = "";
    @Column(name = "purpose")
    private String purpose = "";
    @Column(name = "description")
    private String description = "";
    @Column(name = "result")
    private String result = "";
    @Column(name = "authors")
    private String authors = "";
    @OneToMany(mappedBy = "subject", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Subject subject = (Subject) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name)
            && Objects.equals(purpose, subject.purpose) && Objects.equals(
            description, subject.description) && Objects.equals(result, subject.result)
            && Objects.equals(authors, subject.authors) && Objects.equals(courses,
            subject.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, purpose, description, result, authors, courses);
    }

    @Override
    public String toString() {
        return "Subject{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", purpose='" + purpose + '\'' +
            ", description='" + description + '\'' +
            ", result='" + result + '\'' +
            ", authors='" + authors + '\'' +
            ", courses=" + courses +
            '}';
    }
}
