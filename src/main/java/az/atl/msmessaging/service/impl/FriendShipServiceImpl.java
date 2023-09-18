package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.FriendshipEntity;
import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.FriendShipRepository;
import az.atl.msmessaging.dto.request.AcceptFriendShipRequest;
import az.atl.msmessaging.dto.request.FriendShipRequest;
import az.atl.msmessaging.dto.response.AcceptFriendShipResponse;
import az.atl.msmessaging.dto.response.FriendListResponse;
import az.atl.msmessaging.dto.response.FriendShipResponse;
import az.atl.msmessaging.exceptions.*;
import az.atl.msmessaging.service.FriendShipService;
import jakarta.transaction.Transactional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static az.atl.msmessaging.enums.FriendshipStatus.*;
import static az.atl.msmessaging.enums.Status.OFFLINE;

@Service
public class FriendShipServiceImpl implements FriendShipService {

    private final UserService userService;
    private final FriendShipRepository friendShipRepository;

    private final MessageSource messageSource;


    public FriendShipServiceImpl(UserService userService, FriendShipRepository friendShipRepository, MessageSource messageSource) {
        this.userService = userService;
        this.friendShipRepository = friendShipRepository;
        this.messageSource = messageSource;
    }


    @Transactional
    @Override
    public FriendShipResponse sendFriendshipRequest(FriendShipRequest request) {

        Authentication sender = SecurityContextHolder.getContext().getAuthentication();

        UserEntity user = userService.findByUsername(sender.getName());

        UserEntity friend = userService.findByUsername(request.getFriend());


        String firstStatus = friendShipRepository.getFriendshipStatus(request.getFriend(), sender.getName());

        String secondStatus = friendShipRepository.getFriendshipStatus(sender.getName(), request.getFriend());

        if (Objects.nonNull(firstStatus)) {


            if (Objects.nonNull(secondStatus) && secondStatus.equals(BLOCKED.name())) {
                throw new FriendshipBlockedException(messageSource.getMessage("blocked_status", null, LocaleContextHolder.getLocale()));
            }


            if (firstStatus.equals(PENDING.name())) {
                throw new FriendshipRequestExistsException(messageSource.getMessage("same_friendship_request", null, LocaleContextHolder.getLocale()));
            }

            if (Objects.nonNull(secondStatus) && secondStatus.equals(ACCEPTED.name())) {
                throw new FriendshipAcceptedException(messageSource.getMessage("friendship_accepted", null, LocaleContextHolder.getLocale()));
            }

            if (firstStatus.equals(DECLINED.name())) {

                friendShipRepository.changeFriendshipStatusByUsernames(sender.getName(), request.getFriend(), PENDING.name());

                return FriendShipResponse.builder()
                        .status(PENDING)
                        .build();
            }
        }

        if ((Objects.nonNull(secondStatus)) && secondStatus.equals(PENDING.name())) {
            throw new FriendshipInPendingStatusException(messageSource.getMessage("friendship_exists", null, LocaleContextHolder.getLocale()));
        }

        friendShipRepository.save(
                FriendshipEntity.builder()
                        .userId(user)
                        .friendId(friend)
                        .status(PENDING)
                        .createdAt(new Date())
                        .lastUpdate(new Date())
                        .build()
        );
        return FriendShipResponse.builder()
                .status(PENDING)
                .build();
    }


    @Transactional
    @Override
    public FriendShipResponse acceptFriendshipRequest(AcceptFriendShipRequest request) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        UserEntity userId = userService.findByUsername(user.getName());
        UserEntity friendId = userService.findByUsername(request.getUsername());


        List<String> pendingList = friendShipRepository.getListOfPendingFriends(user.getName());

        if (!pendingList.contains(request.getUsername())) {
            throw new MissingPendingRequestException(messageSource.getMessage("missing_list_requested", null, LocaleContextHolder.getLocale()));
        }
        friendShipRepository.acceptFriendShip(user.getName(), request.getUsername());

        friendShipRepository.save(
                FriendshipEntity.builder()
                        .userId(userId)
                        .friendId(friendId)
                        .status(ACCEPTED)
                        .createdAt(new Date())
                        .lastUpdate(new Date())
                        .build());

        return FriendShipResponse.builder()
                .status(ACCEPTED)
                .build();
    }

    @Transactional
    @Override
    public FriendShipResponse rejectFriendshipRequest(String username) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        String status = friendShipRepository.getFriendshipStatus(user.getName(), username);

        if (Objects.nonNull(status)) {
            if (!status.equals(PENDING.name())) {
                throw new PendingStatusException(messageSource.getMessage("user_pending_status", null, LocaleContextHolder.getLocale()));
            } else {
                friendShipRepository.changeFriendshipStatusByUsernames(username, user.getName(), DECLINED.name());

                return FriendShipResponse.builder()
                        .status(DECLINED)
                        .build();
            }
        } else
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists", null, LocaleContextHolder.getLocale()));

    }

    @Transactional
    @Override
    public List<FriendListResponse> listFriends() {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        List<Object[]> friendList = friendShipRepository.getListOfFriends(user.getName());

        if (Objects.isNull(friendList)) return Collections.emptyList();

        return getFriendListResponses(friendList);
    }

    public List<FriendListResponse> getFriendListResponses(List<Object[]> friendList) {
        return friendList.stream().map(o ->
                {
                    String username = (String) o[0];
                    String status = (String) o[1];
                    Date lastActive = (Date) o[2];
                    String formattedText;
                    if (status.equals(OFFLINE.name())) {
                        formattedText = "[" + lastActive + "] " + username + " (" + status + ")";
                    } else {
                        formattedText = username + " (" + status + ")";
                    }
                    return FriendListResponse.builder()
                            .friend(formattedText)
                            .build();
                }
        ).toList();
    }

    @Transactional
    @Override
    public List<AcceptFriendShipResponse> friendshipRequests() {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        List<String> responses = friendShipRepository.getListOfPendingFriends(user.getName());

        if (Objects.isNull(responses)) return Collections.emptyList();

        return responses.stream().map(o -> AcceptFriendShipResponse.builder().username(o).build()).toList();
    }

    @Transactional
    @Override
    public FriendShipResponse blockUser(String username) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        String status1 = friendShipRepository.getFriendshipStatus(username, user.getName());
        String status2 = friendShipRepository.getFriendshipStatus(user.getName(), username);

        if (Objects.isNull(status1) && Objects.isNull(status2))
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists", null, LocaleContextHolder.getLocale()));
        if (Objects.nonNull(status1)) {
            if (!status1.equals(ACCEPTED.name())) {
                throw new BlockingProhibitedException(messageSource.getMessage("blocking_prohibited", null, LocaleContextHolder.getLocale()));
            }
        }
        if (Objects.nonNull(status2)) {
            if (!status2.equals(ACCEPTED.name())) {
                throw new BlockingProhibitedException(messageSource.getMessage("blocking_prohibited", null, LocaleContextHolder.getLocale()));
            }
        }
        friendShipRepository.changeFriendshipStatusByUsernames(username, user.getName(), BLOCKED.name());
        friendShipRepository.changeFriendshipStatusByUsernames(user.getName(), username, BLOCKED.name());

        return FriendShipResponse
                .builder()
                .status(BLOCKED)
                .build();
    }

    @Transactional
    @Override
    public FriendShipResponse unblockUser(String username) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        String status = friendShipRepository.getFriendshipStatus(username, user.getName());

        if ((Objects.nonNull(status))) {
            if (status.equals(BLOCKED.name())) {
                friendShipRepository.changeFriendshipStatusByUsernames(user.getName(), username, ACCEPTED.name());
                friendShipRepository.changeFriendshipStatusByUsernames(username, user.getName(), ACCEPTED.name());
                return FriendShipResponse.builder()
                        .status(ACCEPTED)
                        .build();
            } else
                throw new UnBlockingProhibitedException(messageSource.getMessage("unblock_prohibited", null, LocaleContextHolder.getLocale()));
        }
        throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists", null, LocaleContextHolder.getLocale()));
    }
}
