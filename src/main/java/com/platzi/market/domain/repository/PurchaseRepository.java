package com.platzi.market.domain.repository;

import com.platzi.market.domain.Purchase;
import com.platzi.market.domain.PurchaseItem;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository {
    List<Purchase> getAll(); //Para objeter la lista de compas
    Optional<List<Purchase>> getByClient(String clientId); //Lista de compras por cliente
    Purchase save(Purchase purchase); //Guardar una compra
}
