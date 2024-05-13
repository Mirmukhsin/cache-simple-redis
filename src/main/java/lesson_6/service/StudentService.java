package lesson_6.service;

import lesson_6.dto.StudentCreateDto;
import lesson_6.entity.Student;
import lombok.NonNull;

import java.util.List;

public interface StudentService {
    Student create(@NonNull StudentCreateDto dto);

    Student update(@NonNull Student student);

    void delete(@NonNull Long id);

    Student get(@NonNull Long id);

    List<Student> getAll();
}
