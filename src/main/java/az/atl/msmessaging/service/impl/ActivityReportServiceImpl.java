package az.atl.msmessaging.service.impl;


import az.atl.msmessaging.dao.entity.ActivityReportEntity;
import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.ActivityReportRepository;
import az.atl.msmessaging.dto.response.ActivityReportResponse;
import az.atl.msmessaging.service.ActivityReportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ActivityReportServiceImpl implements ActivityReportService {

    private final UserService userService;

    @PersistenceContext
    private final EntityManager entityManager;

    private final ActivityReportRepository activityReportRepository;

    public ActivityReportServiceImpl(UserService userService, EntityManager entityManager, ActivityReportRepository activityReportRepository) {
        this.userService = userService;
        this.entityManager = entityManager;
        this.activityReportRepository = activityReportRepository;
    }

    @Transactional
    @Override
    public ActivityReportResponse getActivityReport() {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userService.findByUsername(principal.getName());

        calculateReport(user);

        Double avgResponse = activityReportRepository.calculateAverageResponseTime();

        ActivityReportEntity report = activityReportRepository.findAllByUserId(user);

        return ActivityReportResponse.builder()
                .userId(user.getId())
                .activeFriends(report.getActiveFriends())
                .blockedUsers(report.getBlockedChats())
                .averageResponseTime(avgResponse)
                .totalFriends(report.getTotalFriends())
                .totalMessages(report.getTotalMessages())
                .totalChats(report.getChats())
                .build();
    }

    @Transactional
    public void calculateReport(UserEntity user) {

        Long id = user.getId();

        long activeFriends = activityReportRepository.activeFriends(id);
        long totalMessages = activityReportRepository.totalMessages(id);
        long blockUsers = activityReportRepository.blockUsers(id);
        long totalChats = activityReportRepository.totalChats(id);
        long totalFriends = activityReportRepository.totalFriends(id);


        ActivityReportEntity report = entityManager.find(ActivityReportEntity.class, user.getId());

        report.setActiveFriends(activeFriends);
        report.setTotalFriends(totalFriends);
        report.setTotalMessages(totalMessages);
        report.setChats(totalChats);
        report.setBlockedChats(blockUsers);
    }


}
