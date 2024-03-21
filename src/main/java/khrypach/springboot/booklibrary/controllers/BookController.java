package khrypach.springboot.booklibrary.controllers;

import jakarta.validation.Valid;

import khrypach.springboot.booklibrary.models.Book;
import khrypach.springboot.booklibrary.models.LibraryUser;
import khrypach.springboot.booklibrary.services.BooksService;
import khrypach.springboot.booklibrary.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BooksService booksService;
    private final UsersService usersService;

    @Autowired
    public BookController(BooksService booksService, UsersService usersService) {
        this.booksService = booksService;
        this.usersService = usersService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false ) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear) {

        if (page == null || booksPerPage == null)
            model.addAttribute("books", booksService.getAllBooks(sortByYear));
        else
            model.addAttribute("books", booksService.getBooksWithPagination(page, booksPerPage, sortByYear));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model, @ModelAttribute("user") LibraryUser user) {
        model.addAttribute("book", booksService.getBookById(id));

        LibraryUser bookOwner = booksService.getBookOwner(id);

        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("users", usersService.getAllUsers());

        return "books/display";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book Book) {
        return "books/new";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") @Valid Book Book,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/new";

        booksService.saveBook(Book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(Model model, @PathVariable("id") long id) {
        model.addAttribute("book", booksService.getBookById(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        booksService.deleteBook(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") long id) {
        booksService.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") LibraryUser selectedUser) {
        booksService.assignBook(id, selectedUser);
        return "redirect:/books/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "books/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", booksService.getBooksByTitle(query));
        return "books/search";
    }
}
