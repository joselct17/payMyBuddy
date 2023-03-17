package com.joselct17.paymybuddy.service.implementation;

import com.joselct17.paymybuddy.service.interfaces.IPagingService;
import com.joselct17.paymybuddy.utils.paging.Paging;
import org.springframework.stereotype.Service;

/**
 * This class exists only for being able to mock the static function Paging.of in unit testing...
 *
 *
 */
@Service
public class PagingServiceImpl implements IPagingService {

    @Override
    public Paging of(int totalPages, int pageNumber) {

        return Paging.of(totalPages, pageNumber);
    }
}
