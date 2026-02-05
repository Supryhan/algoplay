package problemslab.lx.stream2;

import java.util.List;

class Order {
  private int id;
  private List<Product> products;
  public int getId() {
    return id;
  }
  public List<Product> getProducts() {
    return products;
  }

  public Order(int id, List<Product> products) {
    this.id = id;
    this.products = products;
  }

}
