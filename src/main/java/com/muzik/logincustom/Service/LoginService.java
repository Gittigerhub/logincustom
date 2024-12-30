package com.muzik.logincustom.Service;

import com.muzik.logincustom.Constant.RoleType;
import com.muzik.logincustom.DTO.LoginDTO;
import com.muzik.logincustom.Entity.LoginEntity;
import com.muzik.logincustom.Repository.LoginRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

// 회원 삽입, 수정, 삭제 다른 클래스에서 작업
// 로그인 클래스는 독립적으로 관리
@Service
@Transactional
@RequiredArgsConstructor
// UserDetailsService : 로그인서비스를 사용자 정의로 오버라이드
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;      // 회원가입 시, 암호를 암호화
    private final ModelMapper modelMapper;

    // UserDetailsService 추상 클래스로 구성되어 있다.
    // 오버드라이브로 특정 메소드를 사용자가 원하는 내용으로 변경해서 적용
    // throws UsernameNotFoundException 사용자 이름이 없으면 예외처리 발생

    // 로그인 처리
    // 메소드명도 변경 불가
    // 내용만 로그인에 필요한 정보로 재 작성
    // try~catch : 프로그램에 일부분에 오류가 발샐할 때 예외처리
    // throws : 메소드 전체에 오류가 발생할 때 예외처리
    // 메소드에 throws를 적용하면 메소드를 호출한 곳에도 동일하게 throws을 작성
    @Override
    public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {

        // 입력받은 userid로 해당 사용자가 존재하는지를 조회
        Optional<LoginEntity> loginEntity = loginRepository.findByLoginid(loginid);

        // Optional로 받은 값은 .get()으로 불러와야 한다.
        // List로 저장된 목록에서 하나씩 호출할 때도 .get()
        if (loginEntity.isPresent()) {                                   // userid가 존재하면
            // 데이터베이스에 있는 회원정보를 가지고 로그인 처리
            // ==> 조회한 정보를 Security에 전달해서 로그인 처리
            // 아이디, 비밀번호, 권한
            return User.withUsername(loginEntity.get().getLoginid())
                    .password(loginEntity.get().getPassword())
                    .roles(loginEntity.get().getRoleType().name())
                    .build();

        } else {                                                         // userid가 존재하지 않으면, 오류발생
            throw new UsernameNotFoundException(loginid+"가 존재하지 않습니다.");

        }

    }

    // 회원가입 (가입폼 -> DTO -> Entity -> 암호화 -> 저장)
    public void saveUser(LoginDTO loginDTO) {

        // 기존에 계정이 존재하는지 확인
        // 아이디는 중복 불가능
        Optional<LoginEntity> read = loginRepository.findByLoginid(loginDTO.getLoginid());

        // 해당 ID의 존재여부 판단
        if (read.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }

        // 해당 ID가 존재하지 않으면
        String password = passwordEncoder.encode(loginDTO.getPassword());
        LoginEntity loginEntity = modelMapper.map(loginDTO, LoginEntity.class);

        // password에 암호화한 비밀번호로 다시 저장
        loginEntity.setPassword(password);

        // 회원가입 시, 기본 권한(USER) 설정 -> 차후 관리자페이지에서 관리자 등록 재조정
        // 회원수가 0이면 관리자로 등록, 1이상이면 일반사용자 등록하게 활용
        loginEntity.setRoleType(RoleType.USER);

        loginRepository.save(loginEntity);

    }


}