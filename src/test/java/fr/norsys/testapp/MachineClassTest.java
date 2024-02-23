package fr.norsys.testapp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MachineClassTest {

    @Test
    public void shouldSumCoins() {
        MachineClass m = new MachineClass();
        int expected = 5;
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        int result = m.getCoins();
        System.out.println("Sum Coins : " + result);
        assertEquals(expected, result);
    }

    @Test
    public void shouldBuyProduct() {
        MachineClass m = new MachineClass();
        int expected = 1;
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        int coins = m.getCoins();
        m.buyProduct(4, coins);//water = 5dh
        int result = m.getReturnValue();
        assertEquals(expected, result);
    }
    @Test
    public void shouldBuyProduct1() {
        MachineClass m = new MachineClass();
        int expected = 3;
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        int coins = m.getCoins();
        m.buyProduct(4, coins);//water = 5dh
        int result = m.getReturnValue();
        assertEquals(expected, result);
    }

    @Test
    public void shouldDisplayProducts() {
        MachineClass m = new MachineClass();
        int expected = 3;
        m.showProducts();
        m.setCoins(Coin.TEN);
        m.buyProduct(1, m.getCoins());//COCA = 7DH
        m.showProducts();
        m.reset();
        m.showProducts();
        int result = m.getReturnValue();
        assertEquals(expected, result);
    }

    @Test
    public void shouldResetProducts() {
        MachineClass m = new MachineClass();
        int expected = 0;
        m.showProducts();
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        m.buyProduct(4, m.getCoins());//WATER = 5DH
        m.showProducts();
        m.reset();
        m.showProducts();
        int result = m.getReturnValue();
        assertEquals(expected, result);
    }

    @Test
    public void shouldDisplayExchange() {
        MachineClass m = new MachineClass();
        int expected = 1;
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        int coins = m.getCoins();
        m.buyProduct(1, coins);//water = 5dh

        int result = m.getReturnValue();
        m.exchange(result);
        m.displayExchange();
        assertEquals(expected, result);
    }

    @Test
    public void shouldDisplayExchange2() {
        MachineClass m = new MachineClass();
        int expected = 9;
        m.setCoins(Coin.TEN);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);
        int coins = m.getCoins();
        m.buyProduct(1, coins);//COCA = 7dh

        int result = m.getReturnValue();
        m.exchange(result);
        m.displayExchange();
        assertEquals(expected, result);
    }
    @Test
    public void shouldReturnBalance() {
        MachineClass m = new MachineClass();
        int expected = 15;
        m.setCoins(Coin.TEN);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);//16 dh
        int coins = m.getCoins();
        m.buyProduct(1, coins);//COCA = 7dh

        int result = m.getBalance();
        m.exchange(result);
        m.displayExchange();
        assertEquals(expected, result);
    }

    @Test
    public void shouldResetVendingMachine() {
        MachineClass m = new MachineClass();
        int expected = 15;
        m.setCoins(Coin.TEN);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.TWO);
        m.setCoins(Coin.ONE);
        m.setCoins(Coin.ONE);//16 dh
        int coins = m.getCoins();
        System.out.println("coins"+coins);
        m.buyProduct(1, coins);//COCA = 7dh

        int result = m.getBalance();
        m.exchange(result);
        m.displayExchange();
        assertEquals(expected, result);
    }
}