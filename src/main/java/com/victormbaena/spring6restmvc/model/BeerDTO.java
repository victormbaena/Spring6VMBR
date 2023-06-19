package com.victormbaena.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class BeerDTO {

    @EqualsAndHashCode.Include
    private UUID id;
    @EqualsAndHashCode.Include
    private Integer version;
    @EqualsAndHashCode.Include
    private String beerName;
    @EqualsAndHashCode.Exclude
    private BeerStyle beerStyle;
    @EqualsAndHashCode.Exclude
    private String upc;
    @EqualsAndHashCode.Exclude
    private Integer quantityOnHand;
    @EqualsAndHashCode.Exclude
    private BigDecimal price;
    @EqualsAndHashCode.Exclude
    private LocalDateTime createdDate;
    @EqualsAndHashCode.Exclude
    private LocalDateTime updateDate;
}
