package com.project.service;

import com.project.domain.relations.ArrangementInOrder;

import java.util.List;

public interface OrderService {
    ArrangementInOrder getArrangmentInOrderDetails(Long id);

    List<ArrangementInOrder> findAllByOrderAndFutureArrangement(String username);

    Double getTotalPrice(String username);
}
