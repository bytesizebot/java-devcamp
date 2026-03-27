package za.co.entelect.java_devcamp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Profile", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false)
    private Long profileId;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    private String idNumber;

    @Column
    private Long customerTypeId;

}
