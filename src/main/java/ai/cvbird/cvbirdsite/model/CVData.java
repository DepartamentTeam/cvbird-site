package ai.cvbird.cvbirdsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "cv_data", indexes = {
        @Index(name = "idx__cv_data__cvbird_user_id", columnList = "cvbird_user_id"),
        @Index(name = "idx__cv_data__id", columnList = "id")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CVData {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cvbird_user_id", referencedColumnName = "cvbird_user_id")
    CVBirdUser cvbirdUser;

    @Column(name = "cv_file", updatable = false)
    String cvFile;

    @Column(name = "cv_description", updatable = false)
    String cvDescription;
}
