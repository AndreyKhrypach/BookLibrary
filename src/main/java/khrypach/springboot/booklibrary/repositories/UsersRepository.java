package khrypach.springboot.booklibrary.repositories;


import khrypach.springboot.booklibrary.models.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<LibraryUser, Long> {

    Optional<LibraryUser> findByFullName(String fullName);
}
