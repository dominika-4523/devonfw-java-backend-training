package com.devonfw.app.java.order.orderservice.logic.base.usecase;

import javax.inject.Inject;

import com.devonfw.app.java.order.general.logic.base.AbstractUc;
import com.devonfw.app.java.order.orderservice.dataaccess.api.repo.CustomerRepository;

public class AbstractCustomerUc extends AbstractUc {

  @Inject
  private CustomerRepository customerRepository;

  public CustomerRepository getCustomerRepository() {

    return this.customerRepository;
  }

}
