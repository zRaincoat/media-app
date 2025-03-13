package org.stefan.bankapp.dtos.responses;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponseDto {
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
