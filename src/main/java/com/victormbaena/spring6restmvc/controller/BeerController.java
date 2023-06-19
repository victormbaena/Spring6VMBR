package com.victormbaena.spring6restmvc.controller;

import com.victormbaena.spring6restmvc.model.BeerDTO;
import com.victormbaena.spring6restmvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;
    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    @GetMapping(BEER_PATH)
    public List<BeerDTO> listBeer() {
        log.info("Get Beer list - In controller");
        List<BeerDTO> beerList = beerService.listBeers();
        log.info("list of beer:\n" + beerList.stream().toList());
        return beerList;
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable UUID beerId) {
        log.debug("Get Beer by ID - In controller");
        BeerDTO beer = beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
        log.info("Beer: " + beer);
        return beer;
    }

    /**
     * Remember do not forget the annotation RequestBody to receive the element sent into the body
     */
    @PostMapping(BEER_PATH)
    public ResponseEntity<HttpStatus> handlePost(@RequestBody BeerDTO beer) {
        log.info("Received beer: " + beer.toString());
        BeerDTO beerSaved = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + beerSaved.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> updateById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        beerService.updateBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeerById(beerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity<HttpStatus> patchBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDTO beer) {
        beerService.patchBeerById(beerId, beer);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * This method launches an Exception only for BeerController.
     * Could be removed at controller level.
     * @return ResponseEntity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpStatus> handleNotFoundException() {
        log.info("Not found Exception Handler method");
        return ResponseEntity.notFound().build();
    }
}
