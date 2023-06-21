package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.entities.Beer;
import com.victormbaena.spring6restmvc.mappers.BeerMapper;
import com.victormbaena.spring6restmvc.model.BeerDTO;
import com.victormbaena.spring6restmvc.repositories.BeerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SpringBootTest
class BeerControllerIntegrationTest {

    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

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

    @Rollback
    @Transactional
    @Test
    void saveNewBeerTest() {
        BeerDTO beerDTO = BeerDTO.builder()
                .beerName("New Beer")
                .build();
        ResponseEntity<HttpStatus> responseEntity = beerController.handlePost(beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = Objects.requireNonNull(responseEntity.getHeaders().getLocation())
                .getPath()
                .split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Beer beer = beerRepository.findById(savedUUID).orElse(Beer.builder().build());
        assertThat(beer).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void updateExistingBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        beerDTO.setId(null);
        beerDTO.setVersion(null);
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity<HttpStatus> responseEntity = beerController.updateById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer updatedBeer = beerRepository.findById(beer.getId()).orElse(Beer.builder().build());
        assertThat(updatedBeer.getBeerName()).isEqualTo(beerName);
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(NotFoundException.class, () ->
                beerController.updateById(UUID.randomUUID(), BeerDTO.builder().build()));
    }
    @Transactional
    @Rollback
    @Test
    void deleteByIdFoundTest() {
        Beer beer = beerRepository.findAll().get(0);
        log.info(beer.getBeerName());
        ResponseEntity <HttpStatus> responseEntity = beerController.deleteById(beer.getId());
        assertThat(beerRepository.findById(beer.getId())).isEmpty();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
    }

    @Test
    void deleteByIdNotFoundTest() {
        assertThrows(NotFoundException.class, () ->
                beerController.deleteById(UUID.randomUUID()));
    }

    @Transactional
    @Rollback
    @Test
    void patchExistingBeerTest() {
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDto(beer);
        log.info(beer.getBeerName());
        final String beerName = "UPDATED";
        beerDTO.setBeerName(beerName);

        ResponseEntity<HttpStatus> responseEntity = beerController.patchBeerById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Beer patchedBeer = beerRepository.findById(beer.getId()).orElse(Beer.builder().build());
        log.info(patchedBeer.getBeerName());
        assertThat(patchedBeer.getBeerName()).isEqualTo(beerName);
    }
}