package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class OrderRepositoryTest extends ComponentTest {

  @Inject
  private OrderRepository orderRepository;

  private CustomerEntity customer = new CustomerEntity();

  private ItemEntity item0 = new ItemEntity();

  private ItemEntity item1 = new ItemEntity();

  private OrderEntity order = new OrderEntity();

  @Before
  public void beforeTests() {

    // given
    this.customer.setFirstname("Maryla");
    this.customer.setLastname("Rodowicz");

    this.item0.setDescription("Carbonara description");
    this.item0.setName("Carbonara");
    this.item0.setPrice(1.0);

    this.item1.setDescription("Sausage description");
    this.item1.setName("Sausage");
    this.item1.setPrice(2.0);

    this.order.setCreationDate(LocalDate.now());
    this.order.setOwner(this.customer);
    this.order.setPrice(3.0);
    this.order.setStatus(OrderStatus.PAID);
    this.order.setOrderPositions(Stream.of(this.item0, this.item1).collect(Collectors.toSet()));
  }

  @Test
  public void insertOrderWithTwoPositions() {

    // when
    this.orderRepository.insertOrderWithTwoPositions(this.order);

    // then
    List<OrderEntity> foundOrders = this.orderRepository.findAll();
    assertThat(foundOrders).isNotNull();
    assertThat(foundOrders).isNotEmpty();
    assertThat(foundOrders).hasSize(2);
  }

  @Test
  public void findOrdersByDayAndStatus() {

    // when
    List<OrderEntity> result = this.orderRepository.findOrdersByDayAndStatus(LocalDate.now(), OrderStatus.PAID);

    // then
    assertThat(result).isNotEmpty();
    assertThat(result).isNotNull();
    assertThat(result).hasSize(1);
  }

}
