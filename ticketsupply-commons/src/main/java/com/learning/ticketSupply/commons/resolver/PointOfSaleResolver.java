package com.learning.ticketSupply.commons.resolver;

import com.learning.ticketSupply.model.BookingRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by prem on 28/09/2017.
 */
@Component
public interface PointOfSaleResolver {
    void resolve(HttpServletRequest httpRequest, BookingRequest bookingRequest);
}
