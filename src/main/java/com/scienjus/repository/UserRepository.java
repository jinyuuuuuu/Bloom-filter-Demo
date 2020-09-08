package com.scienjus.repository;

import com.scienjus.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User类的CRUD操作
 *
 * @author zwl
 * @see com.scienjus.domain.User
 */
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByUsername(String username);

    User findByNickname(String name);
    List<User> findAll();
}
