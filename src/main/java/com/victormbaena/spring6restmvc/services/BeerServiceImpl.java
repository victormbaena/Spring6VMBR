package com.victormbaena.spring6restmvc.services;

import com.victormbaena.spring6restmvc.model.BeerDTO;
import com.victormbaena.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beer1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        BeerDTO beer3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<BeerDTO> listBeers(){
        return new ArrayList<>(beerMap.values());
    }
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Get Beer by Id - in service. Id: " + id.toString());
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beer) {
        BeerDTO savedBeer = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .version(beer.getVersion())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .price(beer.getPrice())
                .upc(beer.getUpc())
                .build();

        beerMap.put(savedBeer.getId(), savedBeer);

        return savedBeer;
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO exists = beerMap.get(beerId);
        exists.setBeerName(beer.getBeerName());
        exists.setBeerStyle(beer.getBeerStyle());
        exists.setPrice(beer.getPrice());
        exists.setVersion(beer.getVersion());
        exists.setUpc(beer.getUpc());
        exists.setQuantityOnHand(beer.getQuantityOnHand());
        return Optional.of(exists);
    }

    @Override
    public Boolean deleteBeerById(UUID beerId) {
        beerMap.remove(beerId);
        return true;
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        BeerDTO exists = beerMap.get(beerId);
        if (StringUtils.hasText(beer.getBeerName())) {
            exists.setBeerName(beer.getBeerName());
        }
        if (beer.getBeerStyle() != null) {
            exists.setBeerStyle(beer.getBeerStyle());
        }
        if (beer.getVersion() != null) {
            exists.setVersion(beer.getVersion());
        }
        if (beer.getPrice() != null) {
            exists.setPrice(beer.getPrice());
        }
        if (StringUtils.hasText(beer.getUpc())) {
            exists.setPrice(beer.getPrice());
        }
        if (beer.getQuantityOnHand() != null) {
            exists.setQuantityOnHand(beer.getQuantityOnHand());
        }
        exists.setUpdateDate(LocalDateTime.now());
        return Optional.of(exists);
    }
}
