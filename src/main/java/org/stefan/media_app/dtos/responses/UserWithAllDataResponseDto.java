package org.stefan.media_app.dtos.responses;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserWithAllDataResponseDto {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private int age;
    private String phoneNumber;
    private String createdAt;
    private String updatedAt;
    private List<PlayListResponseDto> playLists;
}
