package com.example.study.model.newtwork.response;

import com.example.study.model.enumClass.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiResponse {

    private Long id;

    private String account;

    //비밀번호는 당연히 masking 할 것.
    private String password;

    private UserStatus status;

    private String email;

    private String phoneNumber;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;

    private List<OrderGroupApiResponse> orderGroupApiResponseList;
}
