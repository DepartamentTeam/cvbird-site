package ai.cvbird.cvbirdsite.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

 /* Simple JavaBean object that represent a User
         * @since 01.01.2024
        * @author Konstantin Emelianov
  */
@Entity
@Table(name = "user_account", indexes = {
        @Index(name = "idx__user_account__id", columnList = "id"),
        @Index(name = "idx__user_account__email", columnList = "email"),

})
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

     @EqualsAndHashCode.Include
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

     @Column(name = "email", nullable = false, updatable = false, unique = true)
     String email;

     @Column(name = "password", nullable = false, updatable = false)
     String password;

     @Column(name = "enabled", nullable = false)
     private boolean enabled;
     public User() {
         super();
         this.enabled=false;
     }
}
