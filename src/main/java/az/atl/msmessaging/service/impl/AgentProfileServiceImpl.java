package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.UserRepository;
import az.atl.msmessaging.dto.response.DeleteResponse;
import az.atl.msmessaging.dto.response.UpdateResponse;
import az.atl.msmessaging.service.AgentProfileService;
import org.hibernate.sql.Delete;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AgentProfileServiceImpl implements AgentProfileService {

    private final UserRepository userRepository;

    private final UserService userService;

    public AgentProfileServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public UpdateResponse updateUsername(String username) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userService.findByUsername(authentication.getName());

        user.setUsername(username);

        userRepository.save(user);

        SecurityContextHolder.clearContext();

        return UpdateResponse.builder()
                .isUpdated(true)
                .build();
    }

    @Override
    public DeleteResponse deleteUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = (UserEntity)authentication.getPrincipal();

        System.out.println(user.getId());

        userRepository.deleteById(user.getId());

        return DeleteResponse.builder()
                .isDeleted(true)
                .build();
    }
}
