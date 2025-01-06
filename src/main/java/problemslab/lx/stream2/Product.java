package problemslab.lx.stream2;

class Product {


  private String name;
  private int quantity;
  private int price;

  public String getName() {
    return name;
  }
  public int getQuantity() {
    return quantity;
  }

  public Product(String name, int quantity, int price) {
    this.name = name;
    this.quantity = quantity;
    this.price = price;
  }
}
