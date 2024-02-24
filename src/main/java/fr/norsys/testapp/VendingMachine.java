package fr.norsys.testapp;

import java.util.*;

public class VendingMachine {
    private Map<Product, Integer> products = new HashMap<>();
    private Map<Coin, Integer> stockCoins = new HashMap<>();
    private Map<Coin, Integer> inputCoins = new HashMap<>();
    private Map<Coin, Integer> exchangeCoins = new HashMap<>();


    public VendingMachine() {
        //add products
        products.put(Product.COCA, 5);
        products.put(Product.WATER, 6);
        products.put(Product.TWIX, 4);
        products.put(Product.BUENO, 0);
        //add coins
        stockCoins.put(Coin.ONE, 0);
        stockCoins.put(Coin.TWO, 3);
        stockCoins.put(Coin.FIVE, 2);
        stockCoins.put(Coin.TEN, 7);
    }

    public Map<Coin, Integer> buyProduct(Product product, Map<Coin, Integer> price) {
        if (products.containsKey(product) && products.get(product) > 0) {
            // Check if the machine has sufficient inputCoins for the transaction
            Map<Coin, Integer> stockCoinsCopy = new HashMap<>(stockCoins);
            int totalPriceFromClient = sumCoins(price);
            int totalPriceFromStock = sumCoins(stockCoins);
            int rest = totalPriceFromClient - product.getPrice();
            boolean check = true; //check if I can get exchange from machine or not

            if(totalPriceFromClient>totalPriceFromStock){
                throw new IllegalArgumentException("Insufficient Coins in the machine for the transaction :(");
            }
           // decrement quantity from products
            products.put(product, products.get(product) - 1);
            // Add price of chosen product to stockCoins
            // Add the price of the chosen product to stockCoins


            while (check) {
                boolean coinAdded = false; // Flag to track if a coin has been added for change
                for (Coin coin : Coin.values()) { // Iterate over coins in descending order
                    if (rest >= coin.getValue() && stockCoinsCopy.get(coin) > 0) {
                        exchangeCoins.put(coin, exchangeCoins.getOrDefault(coin, 0) + 1);
                        rest -= coin.getValue();
                        stockCoinsCopy.put(coin, stockCoinsCopy.get(coin) - 1); // Decrement stock
                        coinAdded = true;
                        break;
                    }
                }
                if (!coinAdded) {
                    check = false; // If no suitable coin found, exit the loop
                }
            }

            if(rest ==0 ){
                // Add the price of the chosen product to stockCoins
                updateStockCoins();
                // Return the exchange to the client
                return exchangeCoins;
            }else{
                // The inputCoins does not exist or Insufficient Coins
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

    public Map<Coin, Integer> getStockCoins() {
        return this.stockCoins;
    }
    public Map<Coin, Integer> getInputCoins() {
        return inputCoins;
    }

    public Map<Product, Integer> getProducts() {
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

    public void updateStockCoins() {
        for (Coin coin : Coin.values()) {
            int inputQuantity = inputCoins.getOrDefault(coin, 0);
            int exchangeQuantity = exchangeCoins.getOrDefault(coin, 0);
            int result = inputQuantity - exchangeQuantity;
            stockCoins.put(coin, stockCoins.get(coin)+result);
        }
    }
}
