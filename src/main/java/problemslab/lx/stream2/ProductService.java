package problemslab.lx.stream2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * 1. Find all transactions from 2011 and sort them by value (from smallest to largest).
 * 2. List all the unique cities where traders work.
 * 3. Find all traders from Cambridge and sort them by name.
 * 4. Return a string with all traders’ names, sorted alphabetically.
 * 5. Determine if there is any trader based in Milan.
 * 6. Print the total values of all transactions from traders in Cambridge.
 * 7. What is the maximum amount among all transactions?
 * 8. Find the transaction with the minimum value.
 */
class ProductService {
  static Product product1 = new Product("name 1", 1, 10);
  static Product product2 = new Product("name 2", 11, 100);
  static Product product3 = new Product("name 3", 12, 120);
  static Product product4 = new Product("name 4", 13, 120);
  static Product product5 = new Product("name 5", 14, 120);
  static Product product6 = new Product("name 6", 100, 10);
  static Order order1 = new Order(1, List.of(product1, product2));
  static Order order2 = new Order(2, List.of(product3, product4));
  static Order order3 = new Order(3, List.of(product5, product6));

  public static void main(String[] args) {
    ProductService productService = new ProductService();
    productService.findMostPopularProducts(List.of(order1, order2, order3), 2).forEach(System.out::println);
  }


  public List<String> findMostPopularProducts(List<Order> orders, int topN) {
    Map<String, Integer> productQuantities = orders
        .stream()
        .flatMap(order -> order.getProducts().stream())
        .collect(
            Collectors
                .groupingBy(
                    Product::getName,
                    TreeMap::new,
                    Collectors.summingInt(Product::getQuantity)
                ));
    return productQuantities.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(topN)
        .map(Map.Entry::getKey)
        .collect(Collectors.toList());
  }

}
