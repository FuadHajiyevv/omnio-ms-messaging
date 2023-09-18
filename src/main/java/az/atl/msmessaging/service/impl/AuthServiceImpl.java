package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.ActivityReportEntity;
import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.entity.UserStatusEntity;
import az.atl.msmessaging.dao.repository.ActivityReportRepository;
import az.atl.msmessaging.dao.repository.UserRepository;
import az.atl.msmessaging.dao.repository.UserStatusRepository;
import az.atl.msmessaging.dto.request.SwitchStatusRequest;
import az.atl.msmessaging.dto.request.UserAuthRequest;
import az.atl.msmessaging.dto.response.AuthResponse;
import az.atl.msmessaging.dto.response.SwitchResponse;
import az.atl.msmessaging.service.AuthService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Date;

import static az.atl.msmessaging.enums.Status.OFFLINE;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;

    private final ActivityReportRepository activityReportRepository;

    private final UserStatusRepository userStatusRepository;

    public AuthServiceImpl(UserRepository repository, ActivityReportRepository activityReportRepository, UserStatusRepository userStatusRepository) {
        this.repository = repository;
        this.activityReportRepository = activityReportRepository;
        this.userStatusRepository = userStatusRepository;
    }

    @Transactional
    @Override
    public AuthResponse saveNewRegisterUser(UserAuthRequest request) {
        UserEntity user = UserEntity.builder()
                .phoneNumber(request.getPhoneNumber())
                .username(request.getUsername())
                .createdAt(new Date())
                .build();

        repository.save(user);
        userStatusRepository.save(
                UserStatusEntity.builder()
                        .userId(user)
                        .status(OFFLINE)
                        .lastActive(null)
                        .build()
        );
        activityReportRepository.save(
                ActivityReportEntity.builder()
                        .userId(user)
                        .activeFriends(0)
                        .blockedChats(0)
                        .totalFriends(0)
                        .chats(0)
                        .responseTime(0)
                        .totalMessages(0)
                        .lastOffline(null)
                        .lastOnline(null)
                        .build()
        );
        return AuthResponse.builder()
                .isSaved(true)
                .build();
    }

    @Transactional
    @Override
    public SwitchResponse switchStatus(SwitchStatusRequest request) {

        UserStatusEntity status = userStatusRepository.getStatusByUsername(request.getUsername());

        if (request.getStatus().equals(status.getStatus().name())) {
            return SwitchResponse.builder()
                    .switched(false)
                    .build();
        }

        if (status.getStatus().name().equals(OFFLINE.name())) {
            userStatusRepository.updateUserStatusToOnlineByUsername(request.getUsername());
            activityReportRepository.updateLastOffline(status.getUserId().getId());
        } else {
            userStatusRepository.updateUserStatusToOfflineByUsername(request.getUsername());
            activityReportRepository.updateLastOnline(status.getUserId().getId());
            activityReportRepository.updateResponseTimeForUser(status.getUserId().getId());
        }
        return SwitchResponse.builder()
                .switched(true)
                .build();
    }
}
