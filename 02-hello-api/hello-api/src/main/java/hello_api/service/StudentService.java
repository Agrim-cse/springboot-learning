package hello_api.service;

import hello_api.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    public List<Student> getStudents() {
        return List.of(
                new Student(1, "Agrim", 8.21),
                new Student(2, "Rahul", 8.45),
                new Student(3, "Priya", 8.78)
        );
    }
    public Student getStudentById(int id) {
        return new Student(id, "Student " + id, 8.0);
    }
}