package com.muzik.logincustom.DTO;

import com.muzik.logincustom.Constant.RoleType;
import lombok.*;

@Getter @Setter @ToString
@AllArgsConstructor @NoArgsConstructor
@Builder
public class LoginDTO {

    private Integer id;

    private String loginid;

    private String password;

    private String username;

    private RoleType roleType;

}