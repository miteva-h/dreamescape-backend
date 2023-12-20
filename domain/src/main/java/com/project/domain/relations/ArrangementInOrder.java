package com.project.domain.relations;

import com.project.domain.Arrangement;
import com.project.domain.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrangementInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "arrangement_id")
    private Arrangement arrangement;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private LocalDate fromDate;

    private LocalDate toDate;

    private Double price;

    public ArrangementInOrder(Arrangement arrangement,
                              Order order,
                              LocalDate from_date,
                              LocalDate to_date,
                              Double price) {
        this.arrangement = arrangement;
        this.order = order;
        this.fromDate = from_date;
        this.toDate = to_date;
        this.price = price;
    }
}
