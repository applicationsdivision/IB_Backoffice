package com.backoffice.BackofficeInternetBanking.Entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name="backoffice_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long Id;
    @Column(name = "username")
    private String username;
    @Column(name="email",unique = true)
    private String Email;
}
