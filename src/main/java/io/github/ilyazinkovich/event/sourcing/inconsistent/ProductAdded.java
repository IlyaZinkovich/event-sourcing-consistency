package io.github.ilyazinkovich.event.sourcing.inconsistent;

class ProductAdded implements Event {

  final int quantity;

  ProductAdded(final int quantity) {
    this.quantity = quantity;
  }
}
