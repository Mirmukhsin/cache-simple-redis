package lesson_6.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link lesson_6.entity.Student}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateDto implements Serializable {
    private String name;
    private int age;
}