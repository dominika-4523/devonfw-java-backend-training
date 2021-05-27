package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import static com.querydsl.core.alias.Alias.$;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.jpa.dataaccess.api.QueryUtil;
import com.devonfw.module.jpa.dataaccess.api.data.DefaultRepository;
import com.querydsl.jpa.impl.JPAQuery;

@Repository
public interface ItemRepository extends DefaultRepository<ItemEntity> {

  default Page<ItemEntity> findByCriteria(ItemSearchCriteriaTo criteria) {

    ItemEntity alias = newDslAlias();
    JPAQuery<ItemEntity> query = newDslQuery(alias);

    String name = criteria.getName();
    if (name != null && !name.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getName()), name, criteria.getNameOption());
    }

    String description = criteria.getDescription();
    if (description != null && !description.isEmpty()) {
      QueryUtil.get().whereString(query, $(alias.getDescription()), description, criteria.getDescriptionOption());
    }

    Double price = criteria.getPrice();
    if (price != null && price > -1) {
      query.where($(alias.getPrice()).eq(price));
    }

    addOrderBy(query, alias, criteria.getPageable().getSort());

    return QueryUtil.get().findPaginated(criteria.getPageable(), query, true);
  }

  public default void addOrderBy(JPAQuery<ItemEntity> query, ItemEntity entity, Sort sort) {

    if (sort != null) {
      for (Order order : sort) {
        switch (order.getProperty()) {
          case "name":
            if (order.isAscending()) {
              query.orderBy($(entity.getName()).asc());
            } else {
              query.orderBy($(entity.getName()).desc());
            }
            break;
          case "description":
            if (order.isAscending()) {
              query.orderBy($(entity.getDescription()).asc());
            } else {
              query.orderBy($(entity.getDescription()).desc());
            }
            break;
          case "price":
            if (order.isAscending()) {
              query.orderBy($(entity.getPrice()).asc());
            } else {
              query.orderBy($(entity.getPrice()).desc());
            }
            break;
          default:
            throw new IllegalArgumentException("Sorting by unknown property: " + order.getProperty());
        }
      }
    }
  }

  @Query(value = "select * from Item i where i.name = :name order by i.name asc", nativeQuery = true)
  Set<ItemEntity> findByNameAsc(@Param("name") String name);

  @Transactional
  @Modifying
  @Query("update Item i set i.price = ?2 where i.name = ?1")
  void setPriceByName(String name, Double price);
}
