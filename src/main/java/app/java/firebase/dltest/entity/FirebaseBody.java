package app.java.firebase.dltest.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FirebaseBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private DynamicLinkInfo dynamicLinkInfo;

    @OneToOne(fetch = FetchType.LAZY)
    private Suffix suffix;

}
