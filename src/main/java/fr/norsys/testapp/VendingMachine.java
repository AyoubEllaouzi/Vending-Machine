package fr.norsys.testapp;

import java.util.*;

public class VendingMachine {
    private final Map<Product, Integer> products = new HashMap<>();
    private final Map<Coin, Integer> stockCoins = new HashMap<>();
    private final Map<Coin, Integer> inputCoins = new HashMap<>();
    private final Map<Coin, Integer> exchangeCoins = new HashMap<>();

    public VendingMachine() {
        //put products
        products.put(Product.COCA, 5);
        products.put(Product.WATER, 6);
        products.put(Product.TWIX, 4);
        products.put(Product.BUENO, 0);
        //put inputCoins
        stockCoins.put(Coin.ONE, 0);
        stockCoins.put(Coin.TWO, 3);
        stockCoins.put(Coin.FIVE, 2);
        stockCoins.put(Coin.TEN, 7);
    }

    public Map<Coin, Integer> buyProduct(Product product, Map<Coin, Integer> price) {
        if (products.containsKey(product) && products.get(product) > 0) {
            // Check if the machine has sufficient inputCoins for the transaction
            int totalPriceFromClient = sumCoins(price);
            int totalPriceFromStock = sumCoins(stockCoins);
            int rest = totalPriceFromClient - product.getPrice();
            boolean check = true; //check if I can get exchange from machine or not

            if(totalPriceFromClient>totalPriceFromStock){
                throw new IllegalArgumentException("Insufficient Coins in the machine for the transaction :(");
            }
           // decrement quantity from products
            products.put(product, products.get(product) - 1);
            System.out.println("Products after : "+products);

            while (check) {
                boolean coinAdded = false; // Flag to track if a coin has been added for change
                for (Coin coin : Coin.values()) { // Iterate over coins in descending order
                    if (rest >= coin.getValue() && stockCoins.get(coin) > 0) {
                        exchangeCoins.put(coin, exchangeCoins.getOrDefault(coin, 0) + 1);
                        rest -= coin.getValue();
                        stockCoins.put(coin, stockCoins.get(coin) - 1); // Decrement stock
                        coinAdded = true;
                        break;
                    }
                }
                if (!coinAdded) {
                    check = false; // If no suitable coin found, exit the loop
                }
            }

            if(rest ==0 ){
                System.out.println("exchange Coins : " + exchangeCoins);
                return exchangeCoins;
            }else{
                // The inputCoins does not exist or Insufficient Coins
                System.out.println("------- "+exchangeCoins);
                throw new IllegalArgumentException("Insufficient Coins for the transaction :(");
            }
        } else {
            // The product does not exist or is out of stock
            throw new IllegalArgumentException("Product " + product + " is not available.");
        }
    }

    public void setStockCoins(Coin coin,int number) {
        this.inputCoins.put(coin, number);
    }

    public Map<Coin, Integer> getInputCoins() {
        return inputCoins;
    }

    public Map<Product, Integer> gatProducts() {
        return products;
    }

    private int sumCoins(Map<Coin, Integer> price) {
        int sum = 0;
        for (Map.Entry<Coin, Integer> entryCoin : price.entrySet()) {
            int number = entryCoin.getKey().getValue();
            int quantity = entryCoin.getValue();
            sum += (quantity*number);
        }
        return sum;
    }

    public Map<Coin, Integer> cancelRequest() {
        // Return inserted coins as refund
        // Reset input coins
        return inputCoins;
    }

    public Map<Product, Integer> resetOperation() {
        // Reset input coins, exchange coins, and product quantities
        inputCoins.clear();
        exchangeCoins.clear();
        for (Product product : products.keySet()) {
            products.put(product, 0);
        }
        return products;
    }


}
