package projectspringboot.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.util.Set;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permission",
                joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"),
                inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "permission_id"))
   private Set<Permission> permissions;
}
