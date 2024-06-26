package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {
}
