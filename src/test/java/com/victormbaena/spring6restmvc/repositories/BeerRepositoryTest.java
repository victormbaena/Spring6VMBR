package com.victormbaena.spring6restmvc.repositories;

import com.victormbaena.spring6restmvc.entities.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class BeerRepositoryTest {
    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveBeerTest() {
        Beer beerSaved = beerRepository.save(Beer.builder()
                .beerName("My beer")
                .build());
        assertThat(beerSaved).isNotNull();
        assertThat(beerSaved.getId()).isNotNull();
    }
}