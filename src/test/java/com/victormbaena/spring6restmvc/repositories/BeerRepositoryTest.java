package com.victormbaena.spring6restmvc.repositories;

import com.victormbaena.spring6restmvc.entities.Beer;
import com.victormbaena.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeerTest() {
        Beer beerSaved = beerRepository.save(Beer.builder()
                    .beerName("My beer")
                    .beerStyle(BeerStyle.IMPERIAL)
                    .upc("123456789")
                    .price(new BigDecimal("11.99"))
                .build());

        beerRepository.flush();
        assertThat(beerSaved).isNotNull();
        assertThat(beerSaved.getId()).isNotNull();
    }

    @Test
    void saveBeerNameTooLongTest() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer beerSaved = beerRepository.save(Beer.builder()
                    .beerName("My beer 0123456789 0123456789 0123456789 0123456789 0123456789 0123456789")
                    .beerStyle(BeerStyle.IMPERIAL)
                    .upc("123456789")
                    .price(new BigDecimal("11.99"))
                    .build());
            log.info(beerSaved.toString());
            beerRepository.flush();

        });
    }
}