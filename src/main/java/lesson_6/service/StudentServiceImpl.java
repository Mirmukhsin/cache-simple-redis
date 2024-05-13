package lesson_6.service;

import lesson_6.dto.StudentCreateDto;
import lesson_6.entity.Student;
import lesson_6.mapper.StudentMapper;
import lesson_6.repository.StudentRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ConcurrentHashMap<Long, Student> studentCache = new ConcurrentHashMap<>();

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
        studentCache.put(updatedStudent.getId(), student);
        return student;
    }

    @Override
    public void delete(@NonNull Long id) {
        studentRepository.deleteById(id);
        studentCache.remove(id);
    }

    @Override
    @SneakyThrows
    public Student get(@NonNull Long id) {
        Student cachedStudent = studentCache.get(id);
        if (cachedStudent != null) {
            return cachedStudent;
        } else {
            Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
            TimeUnit.SECONDS.sleep(5);
            studentCache.put(id, student);
            return student;
        }
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}
