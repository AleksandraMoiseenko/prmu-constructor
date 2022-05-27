package ru.prmu.constructor.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SubjectDto implements Serializable {
    public static final long SerialVersionUID = 1L;
    private Long id;
    private String name = "";
    private String purpose = "";
    private String description = "";
    private String result = "";
    private String authors = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SubjectDto subject = (SubjectDto) o;
        return Objects.equals(id, subject.id) && Objects.equals(name, subject.name)
            && Objects.equals(purpose, subject.purpose) && Objects.equals(
            description, subject.description) && Objects.equals(result, subject.result)
            && Objects.equals(authors, subject.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, purpose, description, result, authors);
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
            '}';
    }
}
