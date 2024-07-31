package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

// "by" 앞에 올 수 있는 구절들
    // 기능이 없어서 따로 작성한 메소드 정의
    // 반환 타임은 User. 유저가 없다면, null이 반환된다.
    Optional<User> findByName(String name);

    //boolean existByName(String name);

    //long countByAge(Integer age);
    // ...

// "by" 뒤에 올 수 있는 구절들
        // And, OR
        //List<User> findAllByNameAndAge(String name, int age);
        // GreaterThan, GreatherThanEqual
        // Between
        // LessThan, LessThanEqual
        // StartsWith, EndsWith
        // ...
}
