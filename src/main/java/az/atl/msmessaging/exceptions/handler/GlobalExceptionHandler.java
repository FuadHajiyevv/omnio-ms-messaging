package az.atl.msmessaging.exceptions.handler;

import az.atl.msmessaging.dto.response.exception.CustomExceptionResponse;
import az.atl.msmessaging.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(BlockingProhibitedException.class)
    public ResponseEntity<CustomExceptionResponse> blockingProhibited(BlockingProhibitedException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(FriendshipAcceptedException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipAccepted(FriendshipAcceptedException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(FriendshipBlockedException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipBlocked(FriendshipBlockedException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(FriendshipInPendingStatusException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipInPendingStatus(FriendshipInPendingStatusException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(FriendshipRequestExistsException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipExists(FriendshipRequestExistsException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.CONFLICT
        );
    }


    @ExceptionHandler(FriendShipToHimselfException.class)
    public ResponseEntity<CustomExceptionResponse> friendshipHimself(FriendShipToHimselfException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MessageBlockedException.class)
    public ResponseEntity<CustomExceptionResponse> messageBlocked(MessageBlockedException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(MissingPendingRequestException.class)
    public ResponseEntity<CustomExceptionResponse> missingPendingRequest(MissingPendingRequestException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NoFriendshipExistsException.class)
    public ResponseEntity<CustomExceptionResponse> noFriendship(NoFriendshipExistsException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(PendingStatusException.class)
    public ResponseEntity<CustomExceptionResponse> pendingStatus(PendingStatusException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.CONFLICT.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.CONFLICT
        );
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<CustomExceptionResponse> noFriendship(UserNotFoundException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.NOT_FOUND.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(UnBlockingProhibitedException.class)
    public ResponseEntity<CustomExceptionResponse> noFriendship(UnBlockingProhibitedException exception) {
        return new ResponseEntity<>(
                CustomExceptionResponse.builder()
                        .status(HttpStatus.FORBIDDEN.value())
                        .reason(exception.getLocalizedMessage())
                        .build(), HttpStatus.FORBIDDEN
        );
    }


}
