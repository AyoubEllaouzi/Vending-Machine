package fr.norsys.testapp;

import java.util.HashMap;
import java.util.Map;

public class MachineClass {
   private Map<Integer, Product> products = new HashMap<>();
   private Map<Integer, Integer> exchangeCoins = new HashMap<>();
   private int balance;
   private int restValue = 0;
   public int varc =0;
   public int sumCoins =0;

   public MachineClass() {
      insertProducts(1, Product.COCA);
      insertProducts(2, Product.COCA);
      insertProducts(3, Product.COCA);
      insertProducts(4, Product.WATER);
      insertProducts(5, Product.WATER);
      insertProducts(6, Product.WATER);
      setBalance(10);
   }

   public void setBalance(int coin) {
      this.balance = coin;
   }

   public int getReturnValue() {
      return restValue;
   }

   public void setReturnValue(int rest) {
      this.restValue = rest;
   }

   public int getBalance() {
      return balance;
   }

   //Add products to the machine
   public void insertProducts(int id, Product product) {
      products.put(id, product);
   }

   //Show products from the machine
   public void showProducts() {
      Map<Integer, Product> productsCopy = new HashMap<>(products); // Create a copy of the products map
      System.out.print("---------Available Products:--------\n");
      for (Map.Entry<Integer, Product> product : productsCopy.entrySet()) {
         System.out.println("Key : "+ product.getKey() + " Name " + product.getValue() +" : "+product.getValue().getPrice() + " dirhams");
      }
      System.out.print("\n---------------------------------");
   }

   public void setCoins(Coin coin){
      this.sumCoins += coin.getValue();
   }
   public int getCoins(){
      return this.sumCoins;
   }

// 2) Allow user to select products water(5), coca(7), twix(10), bueno(12)
   public int buyProduct(int productId, int price) {
      Product product = products.get(productId);
      int productPrice = product.getPrice();// Get the price of the product
      int restPrice;
      int newBalance;
      if (productPrice <= price) {
         restPrice = price - productPrice; // Calculate the rest value
         newBalance = getBalance()+productPrice;
         setBalance(newBalance);

         setReturnValue(restPrice);
         products.remove(productId);
         exchange(restPrice);
         return restPrice;
      }else {
          varc += price;
          if (varc>=product.getPrice()){
             restPrice = price - productPrice; // Calculate the rest value
             newBalance = getBalance()+productPrice;
             setBalance(getBalance()+newBalance);
             System.out.println("-------------"+getBalance());
             System.out.println("++++++++++++"+newBalance);
             setReturnValue(restPrice);
             products.remove(productId);
             exchange(restPrice);
             return restPrice;
          }
          else {
             System.out.println("zid l flos : " + varc);
          }
      }
      return 0;
   }

   //5) Reset operation for vending machine supplier
   public void reset() {
      products.clear();
      setBalance(0);
      System.out.println("Vending machine has been reset.");
   }

   public void exchange(int totalDirhams) {
      // Clear the exchangeCoins map before populating it
      exchangeCoins.clear();

      // Calculate the number of each type of coin needed
      int numTens = totalDirhams / Coin.TEN.getValue();
      totalDirhams %= Coin.TEN.getValue();

      int numFives = totalDirhams / Coin.FIVE.getValue();
      totalDirhams %= Coin.FIVE.getValue();

      int numTwos = totalDirhams / Coin.TWO.getValue();
      totalDirhams %= Coin.TWO.getValue();

      int numOnes = totalDirhams / Coin.ONE.getValue();

      // Populate the exchangeCoins map with the calculated values
      exchangeCoins.put(Coin.TEN.getValue(), numTens);
      exchangeCoins.put(Coin.FIVE.getValue(), numFives);
      exchangeCoins.put(Coin.TWO.getValue(), numTwos);
      exchangeCoins.put(Coin.ONE.getValue(), numOnes);
   }

   public void displayExchange() {
      System.out.println("Exchange Coins:");
      for (Map.Entry<Integer, Integer> entry : exchangeCoins.entrySet()) {
         System.out.println(entry.getKey() + " dirham coins: " + entry.getValue() + " coins");
      }
   }

}
