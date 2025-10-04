package com.romander.tradingauction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "exchange_proposal")
public class ExchangeProposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "from_user_id")
    private User fromUser;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, name = "to_user_id")
    private User toUser;
    @OneToMany
    @JoinColumn(name = "product_from_id")
    private Set<Product> fromProduct;
    @OneToMany
    @JoinColumn(name = "product_to_id")
    private Set<Product> toProduct;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public enum Status {
        PENDING, ACCEPTED, REJECTED, CANCELED
    }
}
