package com.globant.academy.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    private Integer id;
    private String name;
    private String lastname;
    private LocalDate birthdate;
    private String email;
    private Integer phone;
    private Integer idNumber;

    public CustomerDTO(String name, String lastname, LocalDate birthdate, String email, Integer phone, Integer idNumber) {
        validateCustomer(name,lastname,birthdate,email,phone,idNumber);
        this.name = name;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.idNumber = idNumber;
    }

    private void validateCustomer(String name, String lastname, LocalDate bithdate, String email, Integer phone, Integer idNumber){
        if(Objects.isNull(name))throw new IllegalArgumentException("Name cannot be null");
        if(Objects.isNull(lastname))throw  new IllegalArgumentException("Lastname cannot be null");
        if(Objects.isNull(bithdate))throw new IllegalArgumentException("Birthdate cannot be null");
        if(Objects.isNull(email))throw new IllegalArgumentException("Email cannot be null");
        if(Objects.isNull(phone))throw new IllegalArgumentException("Phone cannot be null");
        if(Objects.isNull(idNumber))throw new IllegalArgumentException("IDNumber cannot be null");
    }
}
