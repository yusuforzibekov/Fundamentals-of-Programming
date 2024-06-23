package com.epam.rd.autotask.io.serialization;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

public class Order implements Serializable {
    private Long id;
    private transient BigDecimal total;
    private final Map<Item, Integer> items;

    public Order(Long id, Map<Item, Integer> items) {
        this.id = id;
        this.items = items;
    }

    public BigDecimal getTotal() {
        if (total == null) {
            total = calculateTotal(items);
        }
        return total;
    }

    private BigDecimal calculateTotal(Map<Item, Integer> items) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Item, Integer> entry : items.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            total = total.add(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        }
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(items, order.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, items);
    }

    public Long getId() {
        return id;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }
}
