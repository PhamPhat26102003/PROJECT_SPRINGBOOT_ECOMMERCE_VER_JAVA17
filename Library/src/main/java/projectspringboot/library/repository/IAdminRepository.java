package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.Admin;

@Repository
public interface IAdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username);
}
