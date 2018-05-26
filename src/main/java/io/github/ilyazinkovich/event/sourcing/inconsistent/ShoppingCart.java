package io.github.ilyazinkovich.event.sourcing.inconsistent;

public class ShoppingCart {

  private static final int CAPACITY = 10;

  private int productsQuantity;

  public ShoppingCart(final EventStore eventStore) {
    eventStore.forEach(this::when);
  }

  public int productsQuantity() {
    return productsQuantity;
  }

  public void handle(final AddProduct addProduct, final EventStore eventStore) {
    if (productsQuantity + addProduct.quantity <= CAPACITY) {
      final ProductAdded productAdded = new ProductAdded(addProduct.quantity);
      eventStore.append(productAdded);
      when(productAdded);
    }
  }

  private void when(final Event event) {
    if (event instanceof ProductAdded) {
      when((ProductAdded) event);
    }
  }

  private void when(final ProductAdded productAdded) {
    productsQuantity += productAdded.quantity;
  }
}
