package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.entities.Beer;
import com.victormbaena.spring6restmvc.model.BeerDTO;
import com.victormbaena.spring6restmvc.repositories.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIntegrationTest {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Test
    void listBeerTest() {
        List<BeerDTO> dtoList = beerController.listBeer();
        assertThat(dtoList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void emptyListTest() {
        beerRepository.deleteAll();
        List<BeerDTO> dtoList = beerController.listBeer();
        assertThat(dtoList.size()).isEqualTo(0);
    }

    @Test
    void getBeerByIdTest() {
        Beer beer = beerRepository.findAll().get(0);

        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();
    }

    @Test
    void getBeerByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
    }
}