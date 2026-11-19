
package com.student.student_management.service;

import com.student.student_management.model.Student;
import com.student.student_management.model.UpdateStudentRequest;
import com.student.student_management.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    // 1. Add student
    public Student addStudent(Student student) {

        if (repository.existsById(student.getStudentId())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Student ID already exists"
            );
        }

        return repository.save(student);
    }

    // 2. Retrieve all students
    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    // 3. Search student by ID
    public Student getStudentById(Integer id) {

        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Student not found"
            ));
    }

    // 4. Update scores and skills
    public Student updateStudent(
            Integer id, UpdateStudentRequest request) {

        Student student = getStudentById(id);

        student.setProgrammingScore(request.getProgrammingScore());
        student.setMathematicsScore(request.getMathematicsScore());
        student.setSkills(request.getSkills());

        return repository.save(student);
    }

    // 5. Delete student
    public void deleteStudent(Integer id) {

        Student student = getStudentById(id);
        repository.delete(student);
    }

    // 6. Merit list in descending order
    public List<Student> getMeritList() {

        List<Student> students = repository.findAll();

        students.sort(
            Comparator.comparingDouble(Student::getAverageScore)
                      .reversed()
        );

        return students;
    }

    // Search by email
    public List<Student> searchByEmail(String email) {
        return repository.findByEmailIgnoreCase(email);
    }

    // Search by course
    public List<Student> searchByCourse(String course) {
        return repository.findByCourseIgnoreCase(course);
    }

    // Students having the same technical skills
    public Map<List<String>, List<Student>> getSameSkillsStudents() {

        return repository.findAll().stream()
            .filter(s -> !s.getSkills().isEmpty())
            .collect(Collectors.groupingBy(
                s -> s.getSkills().stream()
                    .map(String::toLowerCase)
                    .sorted()
                    .toList()
            ))
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().size() > 1)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue
            ));
    }

    // Programming improvement required,
    // but Mathematics score is at least 75
    public List<Student> getStudentsNeedingImprovement() {

        return repository.findAll().stream()
            .filter(s -> s.getProgrammingScore() < 50
                      && s.getMathematicsScore() >= 75)
            .toList();
    }
}
