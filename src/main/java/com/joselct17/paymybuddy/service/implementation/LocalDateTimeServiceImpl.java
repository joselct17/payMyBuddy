package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.service.interfaces.ILocalDateTimeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocalDateTimeServiceImpl implements ILocalDateTimeService {

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
