package io.github.ilyazinkovich.event.sourcing.inconsistent;

public class ProductAdded implements Event {

  final int quantity;

  public ProductAdded(final int quantity) {
    this.quantity = quantity;
  }
}
