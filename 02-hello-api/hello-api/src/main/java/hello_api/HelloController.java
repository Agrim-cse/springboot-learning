package hello_api;

import hello_api.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelloController {

    private final StudentService studentService;

    public HelloController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/students/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/greet")
    public String greet(@RequestParam(defaultValue = "User") String name) {
        return "Hello " + name;
    }
    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
    @DeleteMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return "Student deleted";
    }
    @PutMapping("/students/{id}")
    public Student updateStudent(
            @PathVariable int id,
            @RequestBody Student student) {

        return studentService.updateStudent(id, student);
    }
    // endpoints
}

