package io.github.ilyazinkovich.event.sourcing.inconsistent;

import java.util.List;
import java.util.function.Consumer;

class EventStore {

  private final List<Event> events;

  EventStore(final List<Event> events) {
    this.events = events;
  }

  void append(final Event event) {
    events.add(event);
  }

  void forEach(final Consumer<Event> consumer) {
    events.forEach(consumer);
  }
}
