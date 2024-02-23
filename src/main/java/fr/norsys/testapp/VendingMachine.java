package fr.norsys.testapp;

import java.util.*;

public class VendingMachine {
    private Map<Product, Integer> products = new HashMap<>();
    private Map<Coin, Integer> coinsStock = new HashMap<>();
    private Map<Coin, Integer> coins = new HashMap<>(); // Initialize the coins map

    public VendingMachine() {
        //put products
        products.put(Product.COCA, 5);
        products.put(Product.WATER, 6);
        products.put(Product.TWIX, 4);
        products.put(Product.BUENO, 0);
        //put coins
        coinsStock.put(Coin.ONE, 0);
        coinsStock.put(Coin.TWO, 3);
        coinsStock.put(Coin.FIVE, 2);
        coinsStock.put(Coin.TEN, 7);
        System.out.println("coin stock "+coinsStock);
        System.out.println("coin entry "+coins);
    }

    public Map<Coin, Integer> buyProduct(Product product, Map<Coin, Integer> price) {
        if (products.containsKey(product) && products.get(product) > 0) {

            // Check if the machine has sufficient coins for the transaction
            int totalPrice = sumCoins(price);
            if (totalPrice > sumCoins(coinsStock)) {
                throw new IllegalArgumentException("Insufficient coins in the machine for the transaction.");
            }

            // Update the stock of products
            products.put(product, products.get(product) - 1);

            // Update the stock of coins in the machine
            for (Map.Entry<Coin, Integer> entry : price.entrySet()) {
                Coin coin = entry.getKey();
                int requiredQuantity = entry.getValue();
                int remainingStock = coinsStock.get(coin) - requiredQuantity;
                coinsStock.put(coin, remainingStock);
                System.out.println("coins before 1 "+coins);
                coins.put(coin, coins.getOrDefault(coin, 0) + requiredQuantity); // Update the coins map
                System.out.println("coins after 2 "+coins);
            }

            // Calculate and return the change
            int changeAmount = sumCoins(price) - totalPrice;
            System.out.println("Calculate price entry "+sumCoins(price));
            System.out.println("Calculate price should return "+totalPrice);
            System.out.println("Calculate price in stock "+sumCoins(coinsStock));
            System.out.println("Calculate and return the change "+changeAmount);
            Map<Coin, Integer> change = calculateChange(changeAmount);
            updateCoinsStock(change); // Update the coins stock with the change returned
            System.out.println("coin stock "+coinsStock);
            System.out.println("coin entry "+coins);
            // Return the remaining coins in the machine
            return coinsStock;
        } else {
            // The product does not exist or is out of stock
            throw new IllegalArgumentException("Product " + product + " is not available.");
        }
    }

    private Map<Coin, Integer> calculateChange(int amount) {
        Map<Coin, Integer> change = new HashMap<>();
        int remainingAmount = amount;

        // Sort the coins by their denomination (descending order)
        List<Coin> sortedCoins = new ArrayList<>(coinsStock.keySet());
        sortedCoins.sort(Comparator.comparing(Coin::getValue).reversed());

        // Iterate through the sorted coins and calculate the change
        for (Coin coin : sortedCoins) {
            int coinValue = coin.getValue();
            int availableQuantity = coinsStock.getOrDefault(coin, 0);

            int numCoinsToUse = Math.min(remainingAmount / coinValue, availableQuantity);
            if (numCoinsToUse > 0) {
                change.put(coin, numCoinsToUse);
                remainingAmount -= numCoinsToUse * coinValue;
            }

            if (remainingAmount == 0) {
                break;
            }
        }

        if (remainingAmount > 0) {
            throw new IllegalArgumentException("Cannot make exact change.");
        }

        return change;
    }

    private void updateCoinsStock(Map<Coin, Integer> change) {
        for (Map.Entry<Coin, Integer> entry : change.entrySet()) {
            Coin coin = entry.getKey();
            int quantity = entry.getValue();
            coinsStock.put(coin, coinsStock.get(coin) + quantity);
            coins.put(coin, coins.get(coin) - quantity); // Deduct the change from the coins map
        }
    }



    public void setCoins(Coin coin,int number) {
        this.coins.put(coin, number);
    }

    public Map<Coin, Integer> gatCoins() {
        System.out.println(coins);
        return coins;
    }

    public Map<Coin, Integer> gatStockCoins() {
        System.out.println(coinsStock);
        return coinsStock;
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



}
