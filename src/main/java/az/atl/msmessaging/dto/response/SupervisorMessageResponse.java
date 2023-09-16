package az.atl.msmessaging.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupervisorMessageResponse {

    private Date timestamp;
    private String firstUser;
    private String secondUser;
    private String content;
}
