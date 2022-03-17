package com.kodilla.car_service.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "CLIENTS")
@NoArgsConstructor
public class Client {

    @Id
    @NotNull
    private Long phoneNumber;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String address;

    @NotNull
    private String email;

    @OneToMany(
            targetEntity = Car.class,
            mappedBy = "client",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Car> carList = new ArrayList<>();

    public Client(Long phoneNumber, String firstname, String lastname, String address,
                  String email) {
        this.phoneNumber = phoneNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.address = address;
        this.email = email;
    }
}
