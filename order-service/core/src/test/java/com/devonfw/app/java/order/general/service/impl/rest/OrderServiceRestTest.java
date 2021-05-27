package com.devonfw.app.java.order.general.service.impl.rest;

import java.time.LocalDate;
import java.util.Set;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.devonfw.app.java.order.SpringBootApp;
import com.devonfw.app.java.order.general.common.base.test.DbTestHelper;
import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.CustomerRepository;
import com.devonfw.app.java.order.orderservice.logic.api.Orderservice;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.app.java.order.orderservice.service.api.rest.OrderserviceRestService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { SpringBootApp.class })
public class OrderServiceRestTest extends AbstractRestServiceTest {

  @Inject
  private OrderserviceRestService service;

  @Inject
  private DbTestHelper dbTestHelper;

  @Inject
  private Orderservice orderservice;

  @Inject
  private CustomerRepository customerRepository;

  @Override
  public void doSetUp() {

    super.doSetUp();
    this.dbTestHelper.resetDatabase();
  }

  @Override
  public void doTearDown() {

    this.service = null;
    super.doTearDown();
  }

  @Test
  public void shouldFindItemByName() {

    // when
    Set<ItemEto> items = this.service.findItemByName("Pierogi");

    // then
    assertThat(items).hasSize(1);
    assertThat(items.iterator().next().getName()).isEqualTo("Pierogi");
  }

  @Test
  public void shouldSaveOrder() {

    // given
    LocalDate current = LocalDate.now();
    OrderEto order = new OrderEto();
    order.setCreationDate(current);
    order.setOwnerId((long) 31);
    order.setPrice(60.0);
    order.setStatus(OrderStatus.CANCELLED);

    // when
    this.service.saveOrder(order);
    Set<OrderEto> result = this.service.findOrdersByDayAndStatus(current, OrderStatus.CANCELLED);

    // then
    assertThat(result).hasSize(1);
    assertThat(result.iterator().next().getCreationDate()).isEqualTo(current);
    assertThat(result.iterator().next().getStatus()).isEqualTo(OrderStatus.CANCELLED);
  }

  @Test
  public void shouldIncreasePrice() {

    // given
    String name = "Pierogi";
    Double price = 20.0;

    // when
    this.service.increasePriceByName(name, price);
    Set<ItemEto> items = this.service.findItemByName("Pierogi");

    // given
    assertThat(items).hasSize(1);
    assertThat(items.iterator().next().getName()).isEqualTo("Pierogi");
    assertThat(items.iterator().next().getPrice()).isEqualTo(270.0);
  }
}
