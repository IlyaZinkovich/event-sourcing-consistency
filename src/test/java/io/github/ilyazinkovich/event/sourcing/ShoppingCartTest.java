package io.github.ilyazinkovich.event.sourcing;

import static java.util.stream.IntStream.range;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.ilyazinkovich.event.sourcing.inconsistent.AddProduct;
import io.github.ilyazinkovich.event.sourcing.inconsistent.EventStore;
import io.github.ilyazinkovich.event.sourcing.inconsistent.ShoppingCart;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class ShoppingCartTest {

  @Test
  void test() {
    final EventStore eventStore = new EventStore(new ArrayList<>());
    final ShoppingCart shoppingCart = new ShoppingCart(eventStore);
    final int single = 1;

    range(0, 100).mapToObj(i -> new AddProduct(single))
        .forEach(command -> shoppingCart.handle(command, eventStore));

    final int shoppingCartCapacity = 10;
    assertEquals(shoppingCartCapacity, shoppingCart.productsQuantity());
  }

  @Test
  void concurrentTest() throws InterruptedException {
    final EventStore eventStore = new EventStore(new CopyOnWriteArrayList<>());
    final int single = 1;

    final List<Callable<Void>> callables = range(0, 100).mapToObj(i -> (Callable<Void>) () -> {
      final ShoppingCart shoppingCart = new ShoppingCart(eventStore);
      range(0, 10).mapToObj(i1 -> new AddProduct(single))
          .forEach(command -> shoppingCart.handle(command, eventStore));
      return null;
    }).collect(Collectors.toList());

    final ExecutorService executorService = Executors.newFixedThreadPool(100);
    final List<Future<Void>> futures = executorService.invokeAll(callables);
    futures.forEach(this::wait);

    final ShoppingCart shoppingCart = new ShoppingCart(eventStore);
    final int shoppingCartCapacity = 10;
    assertEquals(shoppingCartCapacity, shoppingCart.productsQuantity());
  }

  private void wait(final Future<Void> future) {
    try {
      future.get();
    } catch (final InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }
  }
}
