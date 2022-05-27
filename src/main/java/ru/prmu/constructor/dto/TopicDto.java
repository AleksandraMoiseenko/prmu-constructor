package ru.prmu.constructor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TopicDto implements Serializable {
    public static final long SerialVersionUID = 1L;
    private Long id;
    private String description = "";
    private String name = "";
    private List<TopicFileDto> files = new ArrayList<>();
    private ModuleDto module = new ModuleDto();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicDto topic = (TopicDto) o;
        return Objects.equals(id, topic.id) && Objects.equals(description,
            topic.description) && Objects.equals(files, topic.files) && Objects.equals(name, topic.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, files, name);
    }

    @Override
    public String toString() {
        return "Topic{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", files=" + files +
            '}';
    }
}
