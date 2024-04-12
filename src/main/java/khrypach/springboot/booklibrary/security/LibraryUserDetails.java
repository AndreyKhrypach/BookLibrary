package khrypach.springboot.booklibrary.security;


import khrypach.springboot.booklibrary.models.LibraryUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class LibraryUserDetails implements UserDetails {

    private final LibraryUser libraryUser;

    public LibraryUserDetails(LibraryUser libraryUser) {
        this.libraryUser = libraryUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(libraryUser.getRole()));
    }

    @Override
    public String getPassword() {
        return this.libraryUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.libraryUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public LibraryUser getLibraryUser() {
        return libraryUser;
    }
}
