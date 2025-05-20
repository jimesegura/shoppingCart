package com.globant.academy.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "birthdate")
    private LocalDate birthdate;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private Integer phone;
    @Column(name = "idNumber")
    private Integer idNumber;

    @OneToMany(mappedBy = "customer")
    private List<ShoppingCart> shoppingCartList;

    public Customer(Integer id, String name, String lastname, LocalDate bithdate, String email, Integer phone, Integer idNumber) {
        validateCustomer(id, name, lastname,  bithdate,  email,  phone,  idNumber);
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.birthdate = bithdate;
        this.email= email;
        this.phone=phone;
        this.idNumber=idNumber;
    }

    private void validateCustomer(Integer id, String name, String lastname, LocalDate bithdate, String email, Integer phone, Integer idNumber){
        if(Objects.isNull(id))throw new IllegalArgumentException("ID cannot be null");
        if(Objects.isNull(name))throw new IllegalArgumentException("Name cannot be null");
        if(Objects.isNull(lastname))throw  new IllegalArgumentException("Lastname cannot be null");
        if(Objects.isNull(bithdate))throw new IllegalArgumentException("Birthdate cannot be null");
        if(Objects.isNull(email))throw new IllegalArgumentException("Email cannot be null");
        if(Objects.isNull(phone))throw new IllegalArgumentException("Phone cannot be null");
        if(Objects.isNull(idNumber))throw new IllegalArgumentException("IDNumber cannot be null");
    }

}
