package fr.norsys.testapp;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VendingMachineTest {
    VendingMachine vendingMachine;

    @Test
    void testBuyProductWithInsufficientCoins() {
        vendingMachine = new VendingMachine();
        Map<Coin, Integer> payment = new HashMap<>();
        payment.put(Coin.ONE, 2);//2<10
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vendingMachine.buyProduct(Product.TWIX, payment));
        assertEquals("Insufficient Coins for the transaction :(", exception.getMessage());
    }

    @Test
    void testBuyProductWithInsufficientCoinsInStock() {
        vendingMachine = new VendingMachine();
        Map<Coin, Integer> payment = new HashMap<>();
        payment.put(Coin.TEN, 10); //100>90
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vendingMachine.buyProduct(Product.TWIX, payment));
        assertEquals("Insufficient Coins in the machine for the transaction :(", exception.getMessage());
    }

    @Test
    public void shouldNotBuyProductFromStock() {
        vendingMachine = new VendingMachine();
        Map<Coin, Integer> expected = new HashMap<>();
        expected.put(Coin.TWO, 1); // Assuming the expected quantity of Coin.TWO after the transaction

        vendingMachine.setStockCoins(Coin.TWO, 1);
        vendingMachine.setStockCoins(Coin.FIVE, 1);
        Map<Coin, Integer> coins = vendingMachine.getInputCoins();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> vendingMachine.buyProduct(Product.TWIX, coins));
        assertEquals("Insufficient Coins for the transaction :(", exception.getMessage());    }

    @Test
    public void shouldBuyProductFromStock() {
        vendingMachine = new VendingMachine();
        Map<Coin, Integer> expected = new HashMap<>();
        expected.put(Coin.FIVE, 2);
        expected.put(Coin.TWO, 3);
        expected.put(Coin.TEN, 1);

        vendingMachine.setStockCoins(Coin.TEN, 2);
        vendingMachine.setStockCoins(Coin.FIVE, 2);
        vendingMachine.setStockCoins(Coin.TWO, 3);
        Map<Coin, Integer> coins = vendingMachine.getInputCoins();
        Map<Coin, Integer> result  = vendingMachine.buyProduct(Product.TWIX,coins);
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    public void shouldAddCoinsToStock() {
        vendingMachine = new VendingMachine();
        Map<Coin, Integer> expected = new HashMap<>();
        expected.put(Coin.FIVE, 4);
        expected.put(Coin.TWO, 3);
        expected.put(Coin.TEN, 7);
        expected.put(Coin.ONE, 0);

        //vendingMachine.setStockCoins(Coin.TEN, 2);
        vendingMachine.setStockCoins(Coin.FIVE, 2);
        vendingMachine.setStockCoins(Coin.TWO, 3);
        Map<Coin, Integer> coins = vendingMachine.getInputCoins();
        Map<Coin, Integer> result = vendingMachine.getStockCoins();

        System.out.println("Stock before buying : "+result);
        vendingMachine.buyProduct(Product.TWIX,coins);
        System.out.println("Stock after buying : "+vendingMachine.getStockCoins());
        assertEquals(expected, result);
    }


    @Test
    public void shouldUpdateQuantityOfProduct() {
        vendingMachine = new VendingMachine();
        Map<Product, Integer> expected = new HashMap<>();
        expected.put(Product.COCA, 5);
        expected.put(Product.WATER, 6);
        expected.put(Product.TWIX, 3);
        expected.put(Product.BUENO, 0);

        vendingMachine.setStockCoins(Coin.TEN, 2);
        vendingMachine.setStockCoins(Coin.FIVE, 2);
        vendingMachine.setStockCoins(Coin.TWO, 3);
        Map<Coin, Integer> coins = vendingMachine.getInputCoins();
        Map<Product, Integer> result = vendingMachine.getProducts();
        //Products before : {WATER=6, TWIX=4, COCA=5, BUENO=0}
        vendingMachine.buyProduct(Product.TWIX,coins);

        //Products after : {WATER=6, TWIX=3, COCA=5, BUENO=0}
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    public void shouldCancelRequest() {
        vendingMachine = new VendingMachine();
        Map<Coin, Integer> expected = new HashMap<>();
        expected.put(Coin.FIVE, 2);
        expected.put(Coin.TWO, 3);
        expected.put(Coin.TEN, 2);
        vendingMachine.setStockCoins(Coin.TEN, 2);
        vendingMachine.setStockCoins(Coin.FIVE, 2);
        vendingMachine.setStockCoins(Coin.TWO, 3);
        Map<Coin, Integer> result  = vendingMachine.cancelRequest();
        System.out.println(result);
        assertEquals(expected, result);
    }

    @Test
    public void shouldResetOperation() {
        vendingMachine = new VendingMachine();
        Map<Product, Integer> expected = new HashMap<>();
        Map<Product, Integer> products = vendingMachine.getProducts();
        expected.put(Product.COCA, 0);
        expected.put(Product.WATER, 0);
        expected.put(Product.TWIX, 0);
        expected.put(Product.BUENO, 0);
        System.out.println(products);
        Map<Product, Integer> result  = vendingMachine.resetOperation();
        System.out.println(products);
        assertEquals(expected, result);
    }
}
