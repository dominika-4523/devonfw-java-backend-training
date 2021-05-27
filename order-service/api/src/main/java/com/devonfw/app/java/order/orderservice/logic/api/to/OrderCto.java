package com.devonfw.app.java.order.orderservice.logic.api.to;

import java.util.HashSet;
import java.util.Set;

public class OrderCto {

  private OrderEto order;

  private CustomerEto customer;

  private Set<ItemEto> items = new HashSet<>();
}
