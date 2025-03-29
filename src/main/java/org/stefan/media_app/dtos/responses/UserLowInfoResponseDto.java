package org.stefan.media_app.dtos.responses;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLowInfoResponseDto {
    private UUID id;
    private String name;
    private String surname;
    private String email;
}
