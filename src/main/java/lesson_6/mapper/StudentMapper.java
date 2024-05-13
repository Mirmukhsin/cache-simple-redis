package lesson_6.mapper;

import lesson_6.dto.StudentCreateDto;
import lesson_6.entity.Student;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
    Student toEntity(StudentCreateDto studentCreateDto);
}