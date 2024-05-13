package lesson_6.service;

import lesson_6.dto.StudentCreateDto;
import lesson_6.entity.Student;
import lesson_6.mapper.StudentMapper;
import lesson_6.repository.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    @Override
    public Student create(@NonNull StudentCreateDto dto) {
        Student student = studentMapper.toEntity(dto);
        return studentRepository.save(student);
    }

    @Override
    @CachePut(value = "students", key = "#updatedStudent.id")
    public Student update(@NonNull Student updatedStudent) {
        Student student = get(updatedStudent.getId());
        student.setName(updatedStudent.getName());
        student.setAge(updatedStudent.getAge());
        return studentRepository.save(student);
    }

    @Override
    @CacheEvict(value = "students", key = "#id")
    public void delete(@NonNull Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @SneakyThrows
    @Cacheable(value = "students", key = "#id")
    public Student get(@NonNull Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
        TimeUnit.SECONDS.sleep(5);
        return student;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}
