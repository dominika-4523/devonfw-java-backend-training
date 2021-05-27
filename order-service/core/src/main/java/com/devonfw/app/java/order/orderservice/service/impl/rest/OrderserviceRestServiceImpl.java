package com.devonfw.app.java.order.orderservice.service.impl.rest;

import java.time.LocalDate;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.service.api.rest.OrderserviceRestService;

@Named("OrderserviceRestService")
public class OrderserviceRestServiceImpl implements OrderserviceRestService {

  @Inject
  private Orderservice orderservice;

  @Override
  public Set<ItemEto> findItemByName(String name) {

    return this.orderservice.findItemsByNameOrdered(name);
  }

  @Override
  public OrderEto saveOrder(OrderEto order) {

    return this.orderservice.saveOrder(order);
  }

  @Override
  public void increasePriceByName(String name, Double price) {

    this.orderservice.increasePriceByName(name, price);
  }

  @Override
  public Set<OrderEto> findOrdersByDayAndStatus(LocalDate date, OrderStatus status) {

    return this.orderservice.findOrdersByDayAndStatus(date, status);
  }

  @Override
  public OrderCto saveOrderWithTwoPositionsAndOwner(OrderCto order) {

    return this.orderservice.saveOrderWithTwoPositionsAndOwner(order);
  }

}
