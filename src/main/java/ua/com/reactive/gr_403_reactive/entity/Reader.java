package ua.com.reactive.gr_403_reactive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("readers")
public class Reader {

    @Id
    private Long id;
    private String firstName;   // колонка first_name
    private String lastName;    // колонка last_name
    private String email;
}