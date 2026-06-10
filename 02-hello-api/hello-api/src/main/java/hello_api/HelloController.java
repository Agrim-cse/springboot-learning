package hello_api;

import hello_api.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    // endpoints
}

