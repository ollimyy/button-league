package fi.ollimyy.buttonleague.service;

import fi.ollimyy.buttonleague.domain.AppUser;
import fi.ollimyy.buttonleague.domain.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AppUserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(AppUserRepository appUserRepository) {
        this.repository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser currentUser = repository.findByUsername(username);

        return new User(username, currentUser.getPasswordHash(),
                AuthorityUtils.createAuthorityList(currentUser.getRole().getName()));
    }
}
