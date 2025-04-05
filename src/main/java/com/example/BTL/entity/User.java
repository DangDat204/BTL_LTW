package com.example.BTL.entity;

import com.example.BTL.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @Column(unique = true, length = 100, nullable = false)
    private String username;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String fullname;

    @Column(length = 20)
    private String tel;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Avatar avatar;

    @Column(length = 255)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
}

//    @Column(unique = true)
//    @Size(max = 100)
//    @NotNull
//    private String username;
//
//    @Size(max = 100)
//    @NotNull
//    private String password;
//
//    @Size(max = 100)
//    @NotNull
//    private String fullname;
//
//    private String tel;
//
//    private String email;
//
//    //@OneToOne(cascade = CascadeType.ALL)
//    //Mối quan hệ 1:1 với role
//    private long role_id;
//
//    private String linkAvatar;

