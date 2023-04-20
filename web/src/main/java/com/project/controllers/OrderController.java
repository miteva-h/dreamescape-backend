package com.project.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.config.JwtAuthConstants;
import com.project.config.filters.JWTAuthorizationFilter;
import com.project.domain.relations.ArrangementInOrder;
import com.project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final JWTAuthorizationFilter filter;

    @GetMapping("/user")
    public List<ArrangementInOrder> findAllOrders(HttpServletRequest request) throws JsonProcessingException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);
        String username = filter.getUsername(header);
        return this.orderService.findAllByOrderAndFutureArrangement(username);
    }

    @GetMapping("/totalPrice")
    public Double getTotalPrice(HttpServletRequest request) throws JsonProcessingException {
        String header = request.getHeader(JwtAuthConstants.HEADER_STRING);
        String username = filter.getUsername(header);
        return this.orderService.getTotalPrice(username);
    }


}
