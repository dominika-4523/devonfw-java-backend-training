package com.devonfw.app.java.order.orderservice.service.api.rest;

import java.time.LocalDate;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.devonfw.app.java.order.orderservice.common.api.OrderStatus;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderCto;
import com.devonfw.app.java.order.orderservice.logic.api.to.OrderEto;
import com.devonfw.module.rest.common.api.RestService;

@Path("/orderservice/v1")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface OrderserviceRestService extends RestService {

  @GET
  @Path("/items/{name}")
  public Set<ItemEto> findItemByName(@PathParam("name") String name);

  @PUT
  @Path("/order/save/")
  public OrderEto saveOrder(OrderEto order);

  @PUT
  @Path("/items/increasepricebyname")
  public void increasePriceByName(String name, Double price);

  @GET
  @Path("/order/{date}/{status}")
  public Set<OrderEto> findOrdersByDayAndStatus(@PathParam("date") LocalDate date,
      @PathParam("status") OrderStatus status);

  @PUT
  @Path("/items/saveorderwithtwopositions")
  public OrderCto saveOrderWithTwoPositionsAndOwner(OrderCto order);
}
