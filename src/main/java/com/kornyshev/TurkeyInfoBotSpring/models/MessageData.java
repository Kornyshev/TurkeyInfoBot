package com.kornyshev.TurkeyInfoBotSpring.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity(name = "Messages")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MessageData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    Timestamp messageDate;
    String ratesString;
    String city;
    String temperature;
    String humidity;
    String condition;
    String waterTemperature;

}
