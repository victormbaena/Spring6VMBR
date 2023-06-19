package com.victormbaena.spring6restmvc.mappers;

import com.victormbaena.spring6restmvc.entities.Beer;
import com.victormbaena.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
