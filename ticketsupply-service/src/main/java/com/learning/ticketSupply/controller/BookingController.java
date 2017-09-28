package com.learning.ticketSupply.controller;


import com.learning.ticketSupply.commons.resolver.PointOfSaleResolver;
import com.learning.ticketSupply.model.BookingRequest;
import com.learning.ticketSupply.model.BookingResponse;
import com.learning.ticketSupply.routing.service.BookingService.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by prem on 28/09/2017.
 */

@Controller
@RequestMapping(value ="/v1/bookings", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BookingController {


    private BookingService bookingService;
    private final PointOfSaleResolver pointOfSaleResolver;

    @Autowired
    public BookingController(BookingService bookingService, PointOfSaleResolver pointOfSaleResolver) {
        this.bookingService = bookingService;
        this.pointOfSaleResolver = pointOfSaleResolver;
    }


    @RequestMapping(value="", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String,String>> createRecordLocator(final HttpServletRequest httpServletRequest) {
        String recordLoacator = String.valueOf(bookingService.recordLocator());
        Map<String, String> map = new HashMap<>();
        map.put("recordLocator",recordLoacator);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @RequestMapping(value = "/{recordLocator}/preppare",method = RequestMethod.PUT,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<BookingResponse> prepareBooking(@PathVariable String recordLocator,
                                                          @RequestBody BookingRequest bookingRequest,
                                                          final HttpServletRequest httpRequest) {

        pointOfSaleResolver.resolve(httpRequest,bookingRequest);
        Long recordLocatorId = Long.parseLong(recordLocator);
        BookingResponse bookingResponse = bookingService.prepare(recordLocatorId, bookingRequest);
        return new ResponseEntity<BookingResponse>(bookingResponse,HttpStatus.OK);
    }


}
