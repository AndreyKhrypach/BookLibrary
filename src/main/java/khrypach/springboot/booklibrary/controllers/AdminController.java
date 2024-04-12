package khrypach.springboot.booklibrary.controllers;

import jakarta.validation.Valid;
import khrypach.springboot.booklibrary.models.LibraryUser;
import khrypach.springboot.booklibrary.services.UsersService;
import khrypach.springboot.booklibrary.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsersService usersService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UsersService usersService, UserValidator userValidator) {
        this.usersService = usersService;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public String libraryUsers(Model model){
        model.addAttribute("users", usersService.getAllUsers());
        return "users/admin/index";
    }

    @GetMapping("/{id}")
    public String displayUsers(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", usersService.getUserById(id));
        model.addAttribute("books", usersService.getBooksByUserId(id));

        return "users/admin/display";
    }
    @GetMapping("/profile")
    public String showProfile( Model model) {
        LibraryUser currentUser = usersService.getCurrentUser();
        model.addAttribute("user", usersService.getUserById(currentUser.getId()));
        model.addAttribute("books", usersService.getBooksByUserId(currentUser.getId()));

        return "users/profile";
    }

    @GetMapping("/new")
    public String newLibraryUser(@ModelAttribute("user") LibraryUser user) {
        return "users/admin/new";
    }

    @PostMapping()
    public String createUser(@ModelAttribute("user") @Valid LibraryUser user,
                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
            return "users/new";

        usersService.saveUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", usersService.getUserById(id));
        return "users/edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") @Valid LibraryUser user, BindingResult bindingResult,
                             @PathVariable("id") long id) {
        if (bindingResult.hasErrors())
            return "users/edit";


        usersService.updateUser(id, user);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        usersService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
