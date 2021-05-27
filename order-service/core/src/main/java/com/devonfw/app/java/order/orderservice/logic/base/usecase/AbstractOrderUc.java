package com.devonfw.app.java.order.orderservice.logic.base.usecase;

import javax.inject.Inject;

import com.devonfw.app.java.order.general.logic.base.AbstractUc;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.CustomerRepository;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.ItemRepository;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.OrderRepository;

public class AbstractOrderUc extends AbstractUc {

  @Inject
  private OrderRepository orderRepository;

  @Inject
  private ItemRepository itemRepository;

  @Inject
  private CustomerRepository customerRepository;

  public OrderRepository getOrderRepository() {

    return this.orderRepository;
  }

  public ItemRepository getItemRepository() {

    return this.itemRepository;
  }

  public CustomerRepository getCustomerRepository() {

    return this.customerRepository;
  }

}
