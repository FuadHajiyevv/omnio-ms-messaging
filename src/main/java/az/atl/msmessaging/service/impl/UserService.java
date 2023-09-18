package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.UserRepository;
import az.atl.msmessaging.exceptions.UserNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    private final MessageSource messageSource;

    public UserService(UserRepository repository, MessageSource messageSource) {
        this.repository = repository;
        this.messageSource = messageSource;
    }

    public UserEntity findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException(messageSource.getMessage("username_not_found", null, LocaleContextHolder.getLocale()))
        );
    }
}
