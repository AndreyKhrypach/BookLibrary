package khrypach.springboot.booklibrary.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "library_user")
public class LibraryUser {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    @NotEmpty(message = "username should be not empty")
    @Size(min = 2, max = 100, message = "username should be between 2 - 100 symbols length")
    private String username;

    @Column(name = "year_of_birth")
    @Min(value = 1800, message = "year should be more than 1800")
    private int yearOfBirth;

    @Column(name = "password")
    @Size(min = 4, max = 100, message = "full name should be between 4 - 100 symbols length")
    private String password;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public LibraryUser(String fullName, int yearOfBirth) {
        this.username = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public LibraryUser() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String fullName) {
        this.username = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
