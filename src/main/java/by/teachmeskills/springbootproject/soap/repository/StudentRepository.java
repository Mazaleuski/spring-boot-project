package by.teachmeskills.springbootproject.soap.repository;

import by.teachmeskills.springbootproject.soap.model.Sex;
import by.teachmeskills.springbootproject.soap.model.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StudentRepository {
    private static final Map<String, Student> students = new HashMap<>();

    @PostConstruct
    public void initData() {
        Student ivan = new Student();
        ivan.setName("Ivan");
        ivan.setSurname("Ivanov");
        ivan.setAge(20);
        ivan.setGroup(123);
        ivan.setSex(Sex.MALE);
        students.put("Ivan", ivan);

        Student sally = new Student();
        sally.setName("Sally");
        sally.setSurname("Sill");
        sally.setAge(21);
        sally.setGroup(123);
        sally.setSex(Sex.FEMALE);
        students.put("Sally", sally);

        Student petr = new Student();
        petr.setName("Petr");
        petr.setSurname("Petrov");
        petr.setAge(22);
        petr.setGroup(123);
        petr.setSex(Sex.MALE);
        students.put("Petr", petr);
    }

    public Student findStudent(String name) {
        return students.get(name);
    }
}
