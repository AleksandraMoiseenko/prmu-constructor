package ru.prmu.constructor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.http.MediaType;

@Entity
@Setter
@Getter
@Table(name = "topic_file")
public class TopicFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "content")
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String content = "";
    @Column(name = "media_type")
    private String mediaType = "";
    @Column(name = "link")
    private String link = "";
    @Column(name = "name")
    private String name = "";
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    @JsonIgnore
    private Topic topic = new Topic();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TopicFile topicFile = (TopicFile) o;
        return Objects.equals(id, topicFile.id) && Objects.equals(content,
            topicFile.content) && Objects.equals(mediaType, topicFile.mediaType)
            && Objects.equals(link, topicFile.link) && Objects.equals(topic,
            topicFile.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, mediaType, link, topic);
    }

    @Override
    public String toString() {
        return "TopicFile{" +
            "id=" + id +
            ", content='" + content + '\'' +
            ", mediaType='" + mediaType + '\'' +
            ", link='" + link + '\'' +
            ", topic=" + topic +
            '}';
    }
}
