package hello_api.service;

import hello_api.Student;
import org.springframework.stereotype.Service;
import hello_api.repository.StudentRepository;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }
    public Student getStudentById(int id) {
        return studentRepository.findById(id).orElse(null);
    }
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    public void deleteStudent(int id) {
        studentRepository.deleteById(id);
    }
    public Student updateStudent(int id, Student updatedStudent) {

        Student student = studentRepository.findById(id)
                .orElse(null);

        if (student == null) {
            return null;
        }

        student.setName(updatedStudent.getName());
        student.setCgpa(updatedStudent.getCgpa());

        return studentRepository.save(student);
    }
}