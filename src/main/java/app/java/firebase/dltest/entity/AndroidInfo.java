package app.java.firebase.dltest.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AndroidInfo {

    @Id
    private Long id;
    private String androidPackageName;
    private String androidFallbackLink;
}
