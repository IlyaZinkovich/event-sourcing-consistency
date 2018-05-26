package io.github.ilyazinkovich.event.sourcing.inconsistent;

public class AddProduct {

  final int quantity;

  public AddProduct(final int quantity) {
    this.quantity = quantity;
  }
}
