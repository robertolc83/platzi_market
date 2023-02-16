package com.platzi.market.persistence.mapper;

import com.platzi.market.domain.PurchaseItem;
import com.platzi.market.persistence.entity.ComprasProducto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//como hacemos referencia a producto para ignorarlo lo tenemos que declarar como uses
@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface PurchaseItemMapper {

    //al ser id una llave primaria accedemos al idProducto por medio de id.idProducto
    @Mapping(source = "id.idProducto" , target = "productId")
    @Mapping(source = "cantidad" , target = "quantity")
    //@Mapping(source = "total" , target = "total") como el source y el target llevan el mismo nombre no es necesario colocarlo
    @Mapping(source = "estado" , target = "active")
    PurchaseItem toPurchaseItem(ComprasProducto comprasProducto);

    @InheritInverseConfiguration
    @Mapping(target = "compra", ignore = true)
    @Mapping(target = "producto", ignore = true)
    @Mapping(target = "id.idCompra", ignore = true)
    ComprasProducto toCompasProducto(PurchaseItem purchaseItem);
}
