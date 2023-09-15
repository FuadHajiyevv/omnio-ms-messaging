package az.atl.msmessaging.service.impl;

import az.atl.msmessaging.dao.entity.MessageEntity;
import az.atl.msmessaging.dao.entity.UserEntity;
import az.atl.msmessaging.dao.repository.FriendShipRepository;
import az.atl.msmessaging.dao.repository.MessageRepository;
import az.atl.msmessaging.dto.request.MessageRequest;
import az.atl.msmessaging.dto.response.ChatListResponse;
import az.atl.msmessaging.dto.response.DeliverResponse;
import az.atl.msmessaging.dto.response.MessageResponse;
import az.atl.msmessaging.exceptions.MessageBlockedException;
import az.atl.msmessaging.exceptions.NoFriendshipExistsException;
import az.atl.msmessaging.service.MessageService;
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

import static az.atl.msmessaging.enums.FriendshipStatus.ACCEPTED;
import static az.atl.msmessaging.enums.FriendshipStatus.BLOCKED;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final FriendShipRepository friendShipRepository;

    private final MessageSource messageSource;

    private final UserService userService;

    public MessageServiceImpl(MessageRepository messageRepository, FriendShipRepository friendShipRepository, MessageSource messageSource, UserService userService) {
        this.messageRepository = messageRepository;
        this.friendShipRepository = friendShipRepository;
        this.messageSource = messageSource;
        this.userService = userService;
    }


    @Transactional
    @Override
    public DeliverResponse sendMessage(MessageRequest request) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        String status = friendShipRepository.friendShipStatus(user.getName(),request.getFriend());

        if(Objects.isNull(status)){
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists",null,LocaleContextHolder.getLocale()));
        }

        if(status.equals(BLOCKED.name())){
            throw  new MessageBlockedException(messageSource.getMessage("message_blocked",null,LocaleContextHolder.getLocale()));
        }

        if(!status.equals(ACCEPTED.name())){
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists",null, LocaleContextHolder.getLocale()));
        }

        UserEntity sender = userService.findByUsername(user.getName());
        UserEntity receiver = userService.findByUsername(request.getFriend());

        messageRepository.save(
                MessageEntity.builder()
                        .senderId(sender)
                        .receiverId(receiver)
                        .content(request.getContent())
                        .timestamp(new Date())
                        .build()
        );
        return DeliverResponse.builder()
                .isDelivered(true)
                .build();
    }
    @Transactional
    @Override
    public List<MessageResponse> getMessages(String username) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        String status = friendShipRepository.friendShipStatus(user.getName(),username);

        if (Objects.isNull(status)) {
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists",null, LocaleContextHolder.getLocale()));
        }

        if(!status.equals(ACCEPTED.name())){
            throw new NoFriendshipExistsException(messageSource.getMessage("friendship_doesnt_exists",null, LocaleContextHolder.getLocale()));
        }

        List<Object[]> messages = messageRepository.getMessages(user.getName(),username);

        return messages != null ?
                messages.stream().map(data -> {
                            Date timestamp = (Date) data[0];
                            String name = (String) data[1];
                            String content = (String) data[2];
                            String formattedMessage = "[" + timestamp.toString() + "] " + name + ": " + content;
                            return MessageResponse.builder()
                                    .message(formattedMessage)
                                    .build();
                        }).toList()
                :
                Collections.emptyList();
    }
    @Transactional
    @Override
    public List<ChatListResponse> getChatList() {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();

        List<String> chats = messageRepository.getChats(user.getName());

        return chats != null ?
                chats.stream().map(o -> ChatListResponse.builder().chat(o).build()).toList()
                :
                Collections.emptyList();
    }
}
