package com.muzik.logincustom.Entity;

import com.muzik.logincustom.Constant.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "users")
public class LoginEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String loginid;
    private String password;
    private String username;

    @Enumerated(EnumType.STRING)        // 열거형 저장할 수 있는 필드로 선언
    private RoleType roleType;          // 열거형 클래스 선언해서 사용

}