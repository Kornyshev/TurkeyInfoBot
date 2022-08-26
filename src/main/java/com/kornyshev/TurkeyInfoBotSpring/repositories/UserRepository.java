package com.kornyshev.TurkeyInfoBotSpring.repositories;

import com.kornyshev.TurkeyInfoBotSpring.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
