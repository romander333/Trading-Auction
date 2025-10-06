package com.romander.tradingauction.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = {"fromProducts", "toProducts", "fromUser", "toUser"})
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

    @ManyToMany
    @JoinTable(
            name = "exchange_proposal_from_product",
            joinColumns = @JoinColumn(name = "exchange_proposal_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> fromProducts = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "exchange_proposal_to_product",
            joinColumns = @JoinColumn(name = "exchange_proposal_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> toProducts = new HashSet<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public enum Status {
        PENDING, ACCEPTED, REJECTED, CANCELED
    }
}



