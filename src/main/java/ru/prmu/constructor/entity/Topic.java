package ru.prmu.constructor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "topic")
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description = "";
    @Column(name = "name")
    private String name = "";
    @OneToMany(mappedBy = "topic", fetch = FetchType.LAZY)
    private List<TopicFile> files = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    @JsonIgnore
    private Module module = new Module();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Topic topic = (Topic) o;
        return Objects.equals(id, topic.id) && Objects.equals(description,
            topic.description) && Objects.equals(files, topic.files)
            && Objects.equals(module, topic.module);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, files, module);
    }

    @Override
    public String toString() {
        return "Topic{" +
            "id=" + id +
            ", description='" + description + '\'' +
            ", files=" + files +
            ", module=" + module +
            '}';
    }
}
