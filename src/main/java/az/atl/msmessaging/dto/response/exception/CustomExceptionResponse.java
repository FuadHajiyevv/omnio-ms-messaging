package az.atl.msmessaging.dto.response.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.net.http.HttpClient;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomExceptionResponse {
    private String reason;
    private int status;
}
