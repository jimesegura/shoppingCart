package com.globant.academy.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class Notification {

    private String nombre;
    private String apellido;
    private Integer phone;
    private String email;
    private Message message;
}
