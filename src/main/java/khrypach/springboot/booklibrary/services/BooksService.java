package khrypach.springboot.booklibrary.services;


import khrypach.springboot.booklibrary.models.Book;
import khrypach.springboot.booklibrary.models.LibraryUser;
import khrypach.springboot.booklibrary.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks(boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(Sort.by("year"));
        } else
            return booksRepository.findAll();
    }

    public List<Book> getBooksWithPagination(int page, int booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
        } else
            return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
    }

    public Book getBookById(long id) {
        return booksRepository.findById(id).orElse(null);
    }

    public List<Book> getBooksByTitle(String title) {
        return booksRepository.findBooksByTitleStartingWithIgnoreCase(title);
    }

    public LibraryUser getBookOwner(long id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void saveBook(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void updateBook(long id, Book updatedBook) {
        Book existedBook = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(existedBook.getOwner());

        booksRepository.save(updatedBook);
    }

    @Transactional
    public void deleteBook(long id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void releaseBook(long id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(null);
            book.setExpired(false);
            book.setTakenAt(null);
        });
    }

    @Transactional
    public void assignBook(long id, LibraryUser selectedUser) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setOwner(selectedUser);
            book.setTakenAt(new Date());
        });
    }
}
