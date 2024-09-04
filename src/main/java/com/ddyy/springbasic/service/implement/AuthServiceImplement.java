package com.ddyy.springbasic.service.implement;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ddyy.springbasic.dto.PostUserRequestDto;
import com.ddyy.springbasic.dto.SignInRequestDto;
import com.ddyy.springbasic.entity.SampleUserEntity;
import com.ddyy.springbasic.provider.JwtProvider;
// import com.ddyy.springbasic.entity.SampleUserEntity;
import com.ddyy.springbasic.repository.SampleUserRepository;
import com.ddyy.springbasic.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    // PasswordEncoder 인터페이스:
    // 비밀번호를 안전하고 쉽게 암호화하여 관리할 수 있도록 도와주는 인터페이스
    // - 구현체
    // - BCryptPasswordEncoder, ScryptPasswordEncoder, Pbkdf2PasswordEncoder
    // - String encode(평문비밀번호): 평문 비밀번호를 암호화하여 반환
    // - boolean matches(평문비밀번호, 암호화된비밀번호): 평문 비밀번호화 암호화된 비밀번호가 일치하는지 여부를 반환
    private PasswordEncoder passwordEncoder= new BCryptPasswordEncoder();
    private final SampleUserRepository sampleUserRepository;
    private final JwtProvider jwtProvider;

    @Override
    public String signUp(PostUserRequestDto dto) {


        try {

            String userId = dto.getUserId();
            boolean isExistedId = sampleUserRepository.existsById(userId);
            if (isExistedId) return "존재하는 아이디!";
            
            String telNumber = dto.getTelNumber();
            boolean isExistedTelNumber = sampleUserRepository.existsByTelNumber(telNumber);
            if (isExistedTelNumber) return "존재하는 전화번호!";

            // String password = dto.getPassword();
            // String name = dto.getName();
            // String address = dto.getAddress();
            // SampleUserEntity user = new SampleUserEntity(userId, password, name, address, telNumber);
            // SampleServiceImplement userEntity = new SampleUserEntity();
            // userEntity.setUserId(userId);
            // userEntity.setPassword(password);

            // SampleUserEntity userEntity =
            //     SampleUserEntity().builder()
            //         .userId(userId)
            //         .password(password)
            //         .name(name)
            //         .address(address)
            //         .telNumber(telNumber)
            //         .build();        // AllArgs 썼을 때 발생할 수 있는 실수를 막고, setter는 편의를 위해 사용한 적 있으나 권장하지 않으므로 builder 패턴을 사용하여 인스턴스(DB 상 클래스) 생성

            // 비밀번호 암호화
            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);
                    
            SampleUserEntity userEntity = new SampleUserEntity(dto);
            sampleUserRepository.save(userEntity);      // save()는 repository에 값을 저장하는 메서드

            return "성공!";
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return "예외 발생!";
        }
    }

    @Override
    public String signIp(SignInRequestDto dto) {
        
        try {
            String userId = dto.getUserId();
            SampleUserEntity userEntity = sampleUserRepository.findByUserId(userId);
            if (userEntity == null) return "로그인 정보가 일치하지 않습니다.";

            String password = dto.getPassword();
            String encodePassword = userEntity.getPassword();

            boolean isMatched = passwordEncoder.matches(password, encodePassword);
            if (!isMatched) return "로그인 정보가 일치하지 않습니다.";

            String token = jwtProvider.create(userId);
            return token;


        } catch (Exception exception) {
            exception.printStackTrace();
            return "예외 발생!";
        }
    }
}
