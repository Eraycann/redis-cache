package org.kafka.redis_cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kafka.redis_cache.model.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDto {
    private String username;

    private String password;

    public User toEntity(CreateUserDto createUserDto){
        return User.builder()
                    .username(createUserDto.getUsername())
                    .password(createUserDto.getPassword())
                .build();
    }
}
