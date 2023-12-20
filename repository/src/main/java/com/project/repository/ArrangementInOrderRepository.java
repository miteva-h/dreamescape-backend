package com.project.repository;

import com.project.domain.Order;
import com.project.domain.relations.ArrangementInOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArrangementInOrderRepository extends JpaRepository<ArrangementInOrder, Long> {
    List<ArrangementInOrder> findByOrderAndFromDateGreaterThanEqual(Order order, LocalDate fromDate);
}
