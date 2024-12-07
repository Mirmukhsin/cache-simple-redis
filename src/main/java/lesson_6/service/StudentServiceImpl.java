package lesson_6.service;

import lesson_6.dto.StudentCreateDto;
import lesson_6.entity.Student;
import lesson_6.mapper.StudentMapper;
import lesson_6.repository.StudentRepository;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final Cache cache;

    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, CacheManager cacheManager) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.cache = cacheManager.getCache("students");
    }


    @Override
    public Student create(@NonNull StudentCreateDto dto) {
        Student student = studentMapper.toEntity(dto);
        return studentRepository.save(student);
    }

    @Override
    public Student update(@NonNull Student updatedStudent) {
        Student student = get(updatedStudent.getId());
        student.setName(updatedStudent.getName());
        student.setAge(updatedStudent.getAge());
        studentRepository.save(student);
        cache.put(updatedStudent.getId(), student);
        return student;
    }

    @Override
    public void delete(@NonNull Long id) {
        studentRepository.deleteById(id);
        cache.evict(id);
    }

    @Override
    @SneakyThrows
    public Student get(@NonNull Long id) {
        Student cachedStudent = cache.get(id, Student.class);
        if (cachedStudent != null) {
            return cachedStudent;
        } else {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
            TimeUnit.SECONDS.sleep(5);
            cache.put(id, student);
            return student;
        }
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}
