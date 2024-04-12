package khrypach.springboot.booklibrary.services;

import jakarta.transaction.Transactional;
import khrypach.springboot.booklibrary.models.LibraryUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UsersService usersService, PasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(LibraryUser user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        usersService.saveUser(user);
    }

}
