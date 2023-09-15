package az.atl.msmessaging.service.security;

import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.UserRepository;
import az.atl.msmessaging.exceptions.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository repository;
    private  final MessageSource messageSource;

    public UserDetailsService(UserRepository repository, MessageSource messageSource) {
        this.repository = repository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity userCredentials = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("user_not_found",null,LocaleContextHolder.getLocale())));

        return userCredentials;
    }
}
