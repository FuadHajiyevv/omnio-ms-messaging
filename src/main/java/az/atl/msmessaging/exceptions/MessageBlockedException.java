package az.atl.msmessaging.exceptions;

import az.atl.msmessaging.dto.response.DeliverResponse;

public class MessageBlockedException extends RuntimeException {
    public MessageBlockedException() {
    }

    public MessageBlockedException(String message) {
        super(message);
    }
}
