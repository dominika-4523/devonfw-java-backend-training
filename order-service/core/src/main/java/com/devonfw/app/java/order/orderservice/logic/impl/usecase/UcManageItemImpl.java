package com.devonfw.app.java.order.orderservice.logic.impl.usecase;

import java.util.Set;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemEto;
import com.devonfw.app.java.order.orderservice.logic.api.usecase.UcManageItem;
import com.devonfw.app.java.order.orderservice.logic.base.usecase.AbstractItemUc;

@Named
@Validated
@Transactional
public class UcManageItemImpl extends AbstractItemUc implements UcManageItem {

  private static final Logger LOG = LoggerFactory.getLogger(UcManageItemImpl.class);

  @Override
  public boolean deleteItem(long itemId) {

    ItemEntity item = getItemRepository().find(itemId);
    getItemRepository().delete(item);
    return true;
  }

  @Override
  public ItemEto saveItem(ItemEto item) {

    ItemEntity itemEntity = getBeanMapper().map(item, ItemEntity.class);

    ItemEntity resultEntity = getItemRepository().save(itemEntity);
    return getBeanMapper().map(resultEntity, ItemEto.class);
  }

  @Override
  public void increasePriceByName(String name, Double price) {

    Set<ItemEto> itemsToReprice = getBeanMapper().mapSet(getItemRepository().findByNameAsc(name), ItemEto.class);
    if (!CollectionUtils.isEmpty(itemsToReprice)) {
      itemsToReprice.stream().forEach(item -> {
        item.setPrice(item.getPrice() + price);
        saveItem(item);
      });
    }
  }
}
