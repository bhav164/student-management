
package com.student.student_management.controller;

import com.student.student_management.model.Student;
import com.student.student_management.model.UpdateStudentRequest;
import com.student.student_management.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // 1. Add student
    @PostMapping
    public Student addStudent(@Valid @RequestBody Student student) {
        return service.addStudent(student);
    }

    // 2. Retrieve all students
    @GetMapping
    public List<Student> getAllStudents() {
        return service.getAllStudents();
    }

    // 3. Search student by ID
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Integer id) {
        return service.getStudentById(id);
    }

    // 4. Update scores and skills
    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateStudentRequest request) {

        return service.updateStudent(id, request);
    }

    // 5. Delete student
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Integer id) {

        service.deleteStudent(id);
        return "Student deleted successfully";
    }

    // 6. Display merit list
    @GetMapping("/merit-list")
    public List<Student> getMeritList() {
        return service.getMeritList();
    }

    // Search by email
    @GetMapping("/search/email")
    public List<Student> searchByEmail(
            @RequestParam String email) {

        return service.searchByEmail(email);
    }

    // Search by course
    @GetMapping("/search/course")
    public List<Student> searchByCourse(
            @RequestParam String course) {

        return service.searchByCourse(course);
    }

    // Students having same technical skills
    @GetMapping("/same-skills")
    public Map<List<String>, List<Student>> getSameSkills() {
        return service.getSameSkillsStudents();
    }

    // Students needing programming improvement
    @GetMapping("/needs-improvement")
    public List<Student> getNeedsImprovement() {
        return service.getStudentsNeedingImprovement();
    }
}
