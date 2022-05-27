package ru.prmu.constructor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class TutorDto implements Serializable {

    public static final long SerialVersionUID = 1L;
    private Long id;
    private String username = "";
    private String password = "";
    private String firstname = "";
    private String lastname = "";
    private String institution = "";
    private String department = "";
    private String city = "";
    private String country = "";
    private Boolean mailDisplay = false;
    private String email = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TutorDto tutor = (TutorDto) o;
        return Objects.equals(id, tutor.id) && Objects.equals(username,
            tutor.username) && Objects.equals(password, tutor.password)
            && Objects.equals(firstname, tutor.firstname) && Objects.equals(
            lastname, tutor.lastname) && Objects.equals(institution, tutor.institution)
            && Objects.equals(department, tutor.department) && Objects.equals(city,
            tutor.city) && Objects.equals(country, tutor.country) && Objects.equals(
            mailDisplay, tutor.mailDisplay) && Objects.equals(email, tutor.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, lastname, institution, department,
            city,
            country, mailDisplay, email);
    }

    @Override
    public String toString() {
        return "Tutor{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", firstname='" + firstname + '\'' +
            ", lastname='" + lastname + '\'' +
            ", institution='" + institution + '\'' +
            ", department='" + department + '\'' +
            ", city='" + city + '\'' +
            ", country='" + country + '\'' +
            ", mailDisplay=" + mailDisplay +
            ", email='" + email + '\'' +
            '}';
    }
}
