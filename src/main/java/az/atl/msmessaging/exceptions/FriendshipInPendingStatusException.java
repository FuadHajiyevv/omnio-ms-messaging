package az.atl.msmessaging.exceptions;

public class FriendshipInPendingStatusException extends RuntimeException{

    public FriendshipInPendingStatusException() {
    }

    public FriendshipInPendingStatusException(String message) {
        super(message);
    }
}
