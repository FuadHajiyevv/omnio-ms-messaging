package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.UserRepository;
import az.atl.msmessaging.dto.response.DeleteResponse;
import az.atl.msmessaging.service.SuperVisorService;
import org.springframework.stereotype.Service;

@Service
public class SuperVisorProfileServiceImpl implements SuperVisorService {

    private final UserRepository repository;

    private final UserService userService;

    public SuperVisorProfileServiceImpl(UserRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public DeleteResponse deleteByUsername(String username) {

        UserEntity user = userService.findByUsername(username);

        repository.deleteById(user.getId());

        return DeleteResponse.builder()
                .isDeleted(true)
                .build();
    }
}
