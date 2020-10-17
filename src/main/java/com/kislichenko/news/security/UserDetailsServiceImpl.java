package com.kislichenko.news.security;

import com.kislichenko.news.dao.AppUserRepository;
import com.kislichenko.news.model.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private AppUserRepository userRepository;

    public UserDetailsServiceImpl(AppUserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }

        //третий параметр - список ролей
        return new User(user.getUsername(),
                user.getPassword(),
                emptyList());
    }
}
