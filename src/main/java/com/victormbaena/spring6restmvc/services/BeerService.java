package com.victormbaena.spring6restmvc.services;

import com.victormbaena.spring6restmvc.model.Beer;

import java.util.UUID;

public interface BeerService {
    Beer getBeerById(UUID id);
}
