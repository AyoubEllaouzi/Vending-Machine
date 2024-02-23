package fr.norsys.testapp;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VendingMachineTest {
    VendingMachine vendingMachine;

 /*   @Test
    public void shouldCheckIfProductNotExistInStock() {
        vendingMachine = new VendingMachine();
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.buyProduct(Product.BUENO, Coin.TWO),
                "Product WATER is not available.");
    }

    @Test
    public void shouldCheckIfCoinNotExistInStock() {
        vendingMachine = new VendingMachine();
        assertThrows(IllegalArgumentException.class, () -> vendingMachine.buyProduct(Product.BUENO, Coin.ONE),
                "Coin TEN is not available.");
    }*/


    @Test
    public void shouldReturnCoinsInsert() {
        vendingMachine = new VendingMachine();
        int expectedTwoCoins = 3;
        int expectedOneCoins = 2;

        vendingMachine.setCoins(Coin.TWO, 3);
        vendingMachine.setCoins(Coin.ONE, 2);

        Map<Coin, Integer> result = vendingMachine.gatCoins();
        System.out.println(result);
        assertEquals(expectedTwoCoins, result.get(Coin.TWO));
        assertEquals(expectedOneCoins, result.get(Coin.ONE));
    }

    @Test
    public void shouldBuyProductFromStock() {
        vendingMachine = new VendingMachine();
        int expectedTwoCoins = 3;
        int expectedOneCoins = 2;

        vendingMachine.setCoins(Coin.TWO, 3);
        vendingMachine.setCoins(Coin.ONE, 2);
        Map<Coin, Integer> coins = vendingMachine.gatCoins();
        vendingMachine.buyProduct(Product.WATER,coins);
        System.out.println(coins);
        assertEquals(expectedTwoCoins, coins.get(Coin.TWO));
        assertEquals(expectedOneCoins, coins.get(Coin.ONE));
    }

    @Test
    public void shouldBuyProductAndUpdateCoinStock() {
        vendingMachine = new VendingMachine();
        int expected = 3;
        // Set up the vending machine with initial coin stock
        Map<Coin, Integer> initialCoinStock = vendingMachine.gatCoins();
        if (initialCoinStock == null) {
            // Handle case where gatCoins() returns null
            initialCoinStock = new HashMap<>();
        }

        // Set up the price of the product
        Map<Coin, Integer> price = new HashMap<>();
        price.put(Coin.FIVE, 2); // Total price: 2 dirhams

        // Buy the product
        Map<Coin, Integer> remainingCoins = vendingMachine.buyProduct(Product.WATER, price);
        System.out.println(remainingCoins);
        System.out.println(vendingMachine.gatStockCoins());

        // Verify that the product was bought and coin stock was updated
        assertEquals(expected, remainingCoins.getOrDefault(Coin.TWO, 0));
    }




}
