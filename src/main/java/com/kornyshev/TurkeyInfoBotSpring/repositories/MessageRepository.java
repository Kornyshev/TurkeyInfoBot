package com.kornyshev.TurkeyInfoBotSpring.repositories;

import com.kornyshev.TurkeyInfoBotSpring.models.MessageData;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;

public interface MessageRepository extends CrudRepository<MessageData, Timestamp> {
}
