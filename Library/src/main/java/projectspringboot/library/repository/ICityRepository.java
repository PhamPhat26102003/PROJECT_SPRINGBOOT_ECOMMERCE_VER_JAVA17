package projectspringboot.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectspringboot.library.model.City;

@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
}
