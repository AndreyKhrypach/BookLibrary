package khrypach.springboot.booklibrary.util;


import khrypach.springboot.booklibrary.models.LibraryUser;
import khrypach.springboot.booklibrary.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    private final UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return LibraryUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LibraryUser user = (LibraryUser) target;

        if (usersService.getUserByFullName(user.getUsername()).isPresent()) {
            errors.rejectValue("fullName", "", "User with this name already exists");
        }
    }
}
