package com.muzik.logincustom.Constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 하나의 파일이 하나의 분류
// ex) 상품분류, 호텔분류, 직원직급 분류
// 권한 분류
@Getter
@AllArgsConstructor
public enum RoleType {

    USER, ADMIN, MANAGER

}