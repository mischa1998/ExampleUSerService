package ru.nikitin.entity.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = "followers")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "city")
    private String city;

    @Column(name = "is_deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private UserEntity publisher;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "publisher", fetch = FetchType.EAGER)
    private Set<UserEntity> followers;
}
