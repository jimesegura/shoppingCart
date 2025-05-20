package com.globant.academy.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationDTO {
    private String name;
    private String lastname;
    private Integer phone;
    private String email;
    private Message message;
}

