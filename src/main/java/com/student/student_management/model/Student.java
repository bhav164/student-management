
package com.student.student_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id")
    @NotNull(message = "Student ID is required")
    private Integer studentId;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Course is required")
    private String course;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double programmingScore;

    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("100.0")
    private Double mathematicsScore;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
        name = "student_skills",
        joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();

    public Student() {
    }

    public Student(Integer studentId, String name,
                   String email, String course,
                   Double programmingScore,
                   Double mathematicsScore,
                   List<String> skills) {

        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.course = course;
        this.programmingScore = programmingScore;
        this.mathematicsScore = mathematicsScore;
        this.skills = new ArrayList<>(skills);
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getProgrammingScore() {
        return programmingScore;
    }

    public void setProgrammingScore(Double programmingScore) {
        this.programmingScore = programmingScore;
    }

    public Double getMathematicsScore() {
        return mathematicsScore;
    }

    public void setMathematicsScore(Double mathematicsScore) {
        this.mathematicsScore = mathematicsScore;
    }

    public List<String> getSkills() {
        return new ArrayList<>(skills);
    }

    public void setSkills(List<String> skills) {
        this.skills = new ArrayList<>(skills);
    }

    // Calculate average
    public double getAverageScore() {
        return (programmingScore + mathematicsScore) / 2.0;
    }

    // Performance category
    public String getPerformanceCategory() {

        double avg = getAverageScore();

        if (avg >= 85)
            return "Excellent";
        else if (avg >= 70)
            return "Good";
        else if (avg >= 50)
            return "Satisfactory";
        else
            return "Needs Improvement";
    }

    // Stronger subject
    public String getStrongerSubject() {

        if (programmingScore > mathematicsScore)
            return "Programming";
        else if (mathematicsScore > programmingScore)
            return "Mathematics";
        else
            return "Both Equal";
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId +
                ", Name: " + name +
                ", Average: " + getAverageScore() +
                ", Category: " + getPerformanceCategory();
    }
}
