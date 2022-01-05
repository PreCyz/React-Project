package react.pw.carly.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import react.pw.carly.dao.UserRepository;

import java.util.ArrayList;
import java.util.Optional;

public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<react.pw.carly.models.User> user = repository.getUserByUsername(username);
        if (user.isPresent()) {
            return new User(user.get().getUsername(), user.get().getPassword(), true,
                    true,true,true,
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
