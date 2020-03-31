package app.java.firebase.dltest.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Suffix {
    @Id
    private Long id;
    private String option;
}
