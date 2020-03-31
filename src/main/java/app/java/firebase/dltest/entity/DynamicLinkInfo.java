package app.java.firebase.dltest.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DynamicLinkInfo {
    @Id private Long id;

    private String domainUriPrefix;
    private String link;

    @OneToOne(fetch = FetchType.LAZY)
    private AndroidInfo androidInfo;

    @OneToOne(fetch = FetchType.LAZY)
    private IosInfo iosInfo;
}
