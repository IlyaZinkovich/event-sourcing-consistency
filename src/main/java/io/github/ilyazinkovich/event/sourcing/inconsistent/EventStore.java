package io.github.ilyazinkovich.event.sourcing.inconsistent;

import java.util.List;
import java.util.function.Consumer;

public class EventStore {

  private final List<Event> events;

  public EventStore(final List<Event> events) {
    this.events = events;
  }

  public void append(final Event event) {
    events.add(event);
  }

  public void forEach(final Consumer<Event> consumer) {
    events.forEach(consumer);
  }
}
