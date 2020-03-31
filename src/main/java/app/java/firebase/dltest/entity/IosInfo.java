package app.java.firebase.dltest.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class IosInfo {

    @Id
    private Long id;

    private String iosBundleId;
    private String iosFallbackLink;
}
