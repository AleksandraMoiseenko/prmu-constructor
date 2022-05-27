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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "tutor")
public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username = "";
    @Column(name = "password")
    private String password = "";
    @Column(name = "firstname")
    private String firstname = "";
    @Column(name = "lastname")
    private String lastname = "";
    @Column(name = "institution")
    private String institution = "";
    @Column(name = "department")
    private String department = "";
    @Column(name = "city")
    private String city = "";
    @Column(name = "country")
    private String country = "";
    @Column(name = "maildisplay")
    private Boolean mailDisplay = false;
    @Column(name = "email")
    private String email = "";
    @ManyToMany(mappedBy = "tutors", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Module> modules = new ArrayList<>();
    @ManyToMany(mappedBy = "tutors", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Course> courses = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tutor tutor = (Tutor) o;
        return Objects.equals(id, tutor.id) && Objects.equals(username,
            tutor.username) && Objects.equals(password, tutor.password)
            && Objects.equals(firstname, tutor.firstname) && Objects.equals(
            lastname, tutor.lastname) && Objects.equals(institution, tutor.institution)
            && Objects.equals(department, tutor.department) && Objects.equals(city,
            tutor.city) && Objects.equals(country, tutor.country) && Objects.equals(
            mailDisplay, tutor.mailDisplay) && Objects.equals(email, tutor.email)
            && Objects.equals(modules, tutor.modules) && Objects.equals(courses,
            tutor.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstname, lastname, institution, department,
            city,
            country, mailDisplay, email, modules, courses);
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
            ", modules=" + modules +
            ", courses=" + courses +
            '}';
    }
}
