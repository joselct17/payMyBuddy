package com.joselct17.paymybuddy.service.interfaces;

import com.joselct17.paymybuddy.utils.paging.Paging;

public interface IPagingService {
    Paging of(int totalPages, int pageNumber);
}
