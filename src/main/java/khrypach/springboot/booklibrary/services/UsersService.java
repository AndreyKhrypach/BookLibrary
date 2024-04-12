package khrypach.springboot.booklibrary.services;


import khrypach.springboot.booklibrary.models.Book;
import khrypach.springboot.booklibrary.models.LibraryUser;
import khrypach.springboot.booklibrary.repositories.UsersRepository;
import khrypach.springboot.booklibrary.security.LibraryUserDetails;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    @Transactional
    public void saveUser(LibraryUser user) {
        usersRepository.save(user);
    }

    @Transactional
    public void updateUser(long id, LibraryUser updatedUser) {
        LibraryUser oldData = usersRepository.findById(id).get();
        updatedUser.setPassword(oldData.getPassword());
        updatedUser.setRole(oldData.getRole());

        updatedUser.setId(id);
        usersRepository.save(updatedUser);
    }

    @Transactional
    public void deleteUser(long id) {
        usersRepository.deleteById(id);
    }

    public List<LibraryUser> getAllUsers() {
        return usersRepository.findAll();
    }

    public LibraryUser getUserById(long id) {
        return usersRepository.findById(id).orElse(null);
    }

    public Optional<LibraryUser> getUserByFullName(String username) {
        return usersRepository.findByUsername(username);
    }

    public List<Book> getBooksByUserId(long id) {
        Optional<LibraryUser> user = usersRepository.findById(id);

        if (user.isPresent()) {
            Hibernate.initialize(user.get().getBooks());
            user.get().getBooks().forEach(book -> {
                long differenceTimeInMilliSeconds = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (differenceTimeInMilliSeconds > 10 * 24 * 60 * 60 * 1000) {   // expired time in 10 days
                    book.setExpired(true);
                }
            });
            return user.get().getBooks();
        }
        return Collections.emptyList();
    }

    public LibraryUser getCurrentUser() {
        return ((LibraryUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getLibraryUser();
    }
}
