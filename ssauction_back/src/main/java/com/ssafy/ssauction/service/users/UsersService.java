package com.ssafy.ssauction.service.users;

import com.ssafy.ssauction.domain.userImages.UserImgs;
import com.ssafy.ssauction.domain.userImages.UserImgsRepository;
import com.ssafy.ssauction.domain.users.Authority;
import com.ssafy.ssauction.domain.users.Users;

import com.ssafy.ssauction.domain.users.UsersRepository;
import com.ssafy.ssauction.web.dto.users.*;

import java.util.NoSuchElementException;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.StyledEditorKit;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final UserImgsRepository userImgsRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    public Users save(UsersSaveRequestDto requestDto) {

        UsersSaveRequestDto userDto = new UsersSaveRequestDto();
        userDto.setUserEmail(requestDto.getUserEmail());
        // 보안을 위해서 유저 패스워드 암호화 하여 디비에 저장.
        userDto.setUserPwd(passwordEncoder.encode(requestDto.getUserPwd()));
        userDto.setUserPhoneNo(requestDto.getUserPhoneNo());
        userDto.setUserNickname(requestDto.getUserNickname());
        Users user= usersRepository.save(userDto.toEntity());
        user.setAuthority(Authority.ROLE_USER);
        return user;
    }

    //이메일 중복체크
    //중복되면 true, 중복되지 않으면 false 반환
    public boolean checkEmailDuplicate(String userEmail){
        return usersRepository.existsByUserEmail(userEmail);
    }

    //닉네임 중복체크
    //중복되면 true, 중복되지 않으면 false 반환
    public boolean checkNicknameDuplicate(String userNickname){
        return usersRepository.existsByUserNickname(userNickname);
    }


    @Transactional
    public void updateInfo(Long userNo, UsersInfoUpdateRequestDto requestDto) {
        Users users = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        users.updateProfile(requestDto.getUserNickname(), requestDto.getUserPhoneNo(), requestDto.getUserComment(), requestDto.getUserDesc());
    }


    //리프레시토큰 db에 저장
    @Transactional
    public Long updateRefreshToken(Long userNo, String refreshToken) {
        Users users = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        users.setRefreshToken(refreshToken);
        return userNo;
    }

    public UsersResponseDto findById(Long userNo) {
        Users entity = usersRepository.findById(userNo).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        return new UsersResponseDto(entity);
    }

    //userEmail 넣어주면 로그인 정보 가져옴
    public UsersAuthResponseDto findByUserEmail(String userEmail) {

        System.out.println("\n\n\n---------------------------" + userEmail + "--------------------------\n\n\n");
        Users entity = usersRepository.findByUserEmail(userEmail).get();
        // orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        System.out.println(entity.toString());

        return new UsersAuthResponseDto(entity);
    }

    //userNo넣어주면 로그인 정보 가져옴
    public UsersAuthResponseDto findByUserNo(Long userNo) {
        Users entity = usersRepository.findByUserNo(userNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
        return new UsersAuthResponseDto(entity);
    }

    public Users findEntityById(Long userNo) {
        return usersRepository.findById(userNo).get();
    }

    //회원 탈퇴
    @Transactional
    public boolean deleteUser(Long userNo) {
        try {
            Users users = usersRepository.findById(userNo).get();
            users.updateAuthorities(Authority.ROLE_RESIGN);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    // 아이디 찾기
    public UsersFindIdDto findByPhoneNo(String userPhoneNo) {
        Users entity;
        try {
            entity = usersRepository.findByUserPhoneNo(userPhoneNo).get();
            System.out.println(entity.toString());
        } catch (NoSuchElementException e) {
            System.out.println("없음");
            return null;
        }
        return new UsersFindIdDto(entity);
    }

    // 비밀번호 재설정
    @Transactional
    public String updatePwd(String userPhoneNo, String userId, UsersUpdatePwdDto resetPwdDto) {
        Users users;
        try {
            users = usersRepository.findByUserPhoneNo(userPhoneNo).get();
            users.updatePwd(passwordEncoder.encode(resetPwdDto.getUserPwd()));
        } catch (NoSuchElementException e) {
            System.out.println("없음");
            return null;
        }
        return userId + "님의 비밀번호가 변경되었습니다.";
    }

    //프로필수정에서 비밀번호 변경
    @Transactional
    public boolean profileUpdatePwd(Long userNo, UsersUpdatePwdDto resetPwdDto) {
        Users users;
        try {
            users = usersRepository.findById(userNo).get();
            users.updatePwd(passwordEncoder.encode(resetPwdDto.getUserPwd()));
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("없음");
            return false;
        }
    }

    public UsersResponseDto findUser(UsersLoginDto requestDto) {
        System.out.println(requestDto.toString());
        Users user = null;
        try {
            user = usersRepository.findByUserEmailAndUserPwd(requestDto.getLoginEmail(), requestDto.getLoginPwd()).get();
        } catch (NoSuchElementException e) {
            System.out.println("없음");
            e.getMessage();
        }
        UsersResponseDto responseDto = null;

        if (user != null && user.getAuthority() == Authority.ROLE_USER) {
            responseDto = UsersResponseDto.builder()
                    .entity(user)
                    .build();
        }
        return responseDto;
    }

    public UserInfoResponseDto getInfo(Long userNo) {
        Users user = usersRepository.findById(userNo).get();
        UserImgs img = userImgsRepository.findByUser(user).get();
        return UserInfoResponseDto.builder()
                .user(user)
                .build();
    }
}
