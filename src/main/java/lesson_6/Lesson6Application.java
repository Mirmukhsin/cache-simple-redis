package lesson_6;

import lesson_6.entity.Student;
import lesson_6.repository.StudentRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Lesson6Application {

    public static void main(String[] args) {
        SpringApplication.run(Lesson6Application.class, args);
    }

    @Bean
    public ApplicationRunner runner(StudentRepository studentRepository) {
        return args -> {
            studentRepository.saveAll(List.of(
                    new Student(1L, "Panda", 18),
                    new Student(2L, "Simple", 19),
                    new Student(3L, "Crazy", 20),
                    new Student(4L, "Easy", 21),
                    new Student(5L, "Special", 22)
            ));
        };
    }
}
