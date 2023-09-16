package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.ActivityReportEntity;
import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.ActivityReportRepository;
import az.atl.msmessaging.dao.repository.FriendShipRepository;
import az.atl.msmessaging.dao.repository.MessageRepository;
import az.atl.msmessaging.dto.response.*;
import az.atl.msmessaging.exceptions.NoFriendshipExistsException;
import az.atl.msmessaging.service.SuperVisorMessageService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static az.atl.msmessaging.enums.FriendshipStatus.ACCEPTED;

@Service
public class SuperVisorMessageServiceImpl implements SuperVisorMessageService {

    private final FriendShipRepository friendShipRepository;

    private final MessageRepository messageRepository;

    private final ActivityReportRepository activityReportRepository;

    private final UserService userService;

    private final MessageServiceImpl messageService;

    private final ActivityReportServiceImpl activityReportService;

    private final MessageSource messageSource;

    private final FriendShipServiceImpl friendShipService;

    public SuperVisorMessageServiceImpl(FriendShipRepository friendShipRepository, MessageRepository messageRepository, ActivityReportRepository activityReportRepository, UserService userService, MessageServiceImpl messageService, ActivityReportServiceImpl activityReportService, MessageSource messageSource, FriendShipServiceImpl friendShipService) {
        this.friendShipRepository = friendShipRepository;
        this.messageRepository = messageRepository;
        this.activityReportRepository = activityReportRepository;
        this.userService = userService;
        this.messageService = messageService;
        this.activityReportService = activityReportService;
        this.messageSource = messageSource;
        this.friendShipService = friendShipService;
    }

    @Override
    public List<MessageResponse> getMessagesByUsername(String u1, String u2) {
        String status = friendShipRepository.friendShipStatus(u1, u2);

        if (Objects.isNull(status)) {
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists", null, LocaleContextHolder.getLocale()));
        }

        if (!status.equals(ACCEPTED.name())) {
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists", null, LocaleContextHolder.getLocale()));
        }

        List<Object[]> messages = messageRepository.getMessages(u1, u2);

        return messageService.getMessageResponses(messages);
    }

    @Override
    public List<ChatListResponse> getChatsOfUserByUsername(String username) {
        List<String> chats = messageRepository.getChats(username);

        return chats != null ?
                chats.stream().map(o -> ChatListResponse.builder().chat(o).build()).toList()
                :
                Collections.emptyList();
    }

    @Override
    public List<FriendListResponse> getFriendsOfUser(String u) {

        List<Object[]> friendList = friendShipRepository.getListOfFriends(u);

        if (Objects.isNull(friendList)) return Collections.emptyList();

        return friendShipService.getFriendListResponses(friendList);
    }

    @Override
    public ActivityReportResponse getActivityReport(String username) {

        UserEntity user = userService.findByUsername(username);

        activityReportService.calculateReport(user);

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
}
