package khrypach.springboot.booklibrary.services;

import khrypach.springboot.booklibrary.models.LibraryUser;
import khrypach.springboot.booklibrary.repositories.UsersRepository;
import khrypach.springboot.booklibrary.security.LibraryUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public LibraryUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<LibraryUser> user = usersRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new LibraryUserDetails(user.get());
    }
}
