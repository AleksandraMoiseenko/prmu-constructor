package ru.prmu.constructor.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Data
public class TopicFileDto implements Serializable {
    public static final long SerialVersionUID = 1L;
    private Long id;
    private String link = "";
    private String name = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicFileDto topicFile = (TopicFileDto) o;
        return Objects.equals(id, topicFile.id)
            && Objects.equals(link, topicFile.link) && name.equals(topicFile.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, link, name);
    }

    @Override
    public String toString() {
        return "TopicFile{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", link='" + link + '\'' +
            '}';
    }
}
