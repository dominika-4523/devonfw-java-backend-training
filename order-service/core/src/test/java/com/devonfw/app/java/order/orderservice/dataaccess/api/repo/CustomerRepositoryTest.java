package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.devonfw.app.java.order.orderservice.dataaccess.api.CustomerEntity;
import com.devonfw.app.java.order.orderservice.dataaccess.api.OrderEntity;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class CustomerRepositoryTest extends ComponentTest {

  @Inject
  private CustomerRepository customerRepository;

  private final CustomerEntity customer0 = createCustomerEntity(2, "Maryla", "Rodowicz",
      Collections.singleton(new OrderEntity()));

  private final CustomerEntity customer1 = createCustomerEntity(3, "Krzysztof", "Krawczyk",
      Collections.singleton(new OrderEntity()));

  private CustomerEntity createCustomerEntity(int id, String firstname, String lastname, Set<OrderEntity> orders) {

    final CustomerEntity customer = new CustomerEntity();
    customer.setId((long) id);
    customer.setFirstname(firstname);
    customer.setLastname(lastname);
    customer.setOrders(orders);
    return customer;
  }

  @Before
  public void beforeTests() {

    this.customerRepository.saveAll(Stream.of(this.customer0, this.customer1).collect(Collectors.toList()));
  }

  @After
  public void afterTests() {

    this.customerRepository.deleteAll(Stream.of(this.customer0, this.customer1).collect(Collectors.toList()));
  }

  @Test
  public void removeById() {

    // when
    this.customerRepository.deleteById((long) 2);

    // then
    final Optional<CustomerEntity> customer = this.customerRepository.findById(Long.parseLong("1"));
    assertEquals(customer.isPresent(), false);
  }
}
