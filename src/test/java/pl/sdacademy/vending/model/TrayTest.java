package pl.sdacademy.vending.model;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class TrayTest {


    @Test
    public void shouldBeAbleToBuyLastAvailableProduct(){
        //given
        Product definedProduct = new Product("P1");
        Tray tray = Tray.builder("A1")
                .product(definedProduct)
                .build();

        //when
        Optional<Product> boughtProduct = tray.buyProduct();

        //then
        assertTrue(boughtProduct.isPresent());
        assertEquals(definedProduct,boughtProduct.get());

    }

}