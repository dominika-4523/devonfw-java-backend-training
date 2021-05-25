package com.devonfw.app.java.order.orderservice.dataaccess.api.repo;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.devonfw.app.java.order.orderservice.dataaccess.api.ItemEntity;
import com.devonfw.app.java.order.orderservice.logic.api.to.ItemSearchCriteriaTo;
import com.devonfw.module.test.common.base.ComponentTest;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ItemRepositoryTest extends ComponentTest {

  @Inject
  private ItemRepository itemRepository;

  private final ItemEntity item0 = createItemEntity("Lasagna", "The best lasagna", 25.0);

  private final ItemEntity item1 = createItemEntity("Spaghetti Carbonara", "Carbonara description", 55.5);

  @Before
  public void beforeTests() {

    this.itemRepository.saveAll(Stream.of(this.item0, this.item1).collect(Collectors.toList()));
  }

  @After
  public void afterTests() {

    this.itemRepository.deleteAll(Stream.of(this.item0, this.item1).collect(Collectors.toList()));
  }

  private ItemEntity createItemEntity(String name, String description, Double price) {

    ItemEntity item = new ItemEntity();
    item.setName(name);
    item.setDescription(description);
    item.setPrice(price);
    return item;
  }

  @Test
  public void shouldFindAllItems() {

    // when
    List<ItemEntity> foundItems = this.itemRepository.findAll();

    // then
    assertThat(foundItems).hasSize(3);
  }

  @Test
  public void shouldFindNothing() {

    // given
    final ItemSearchCriteriaTo searchCriteria = new ItemSearchCriteriaTo();
    searchCriteria.setDescription("Spaghetti Napoli");
    Sort sort = Sort.by("description");
    Pageable pageable = PageRequest.of(0, 20, sort);
    searchCriteria.setPageable(pageable);

    // when
    Page<ItemEntity> foundItems = this.itemRepository.findByCriteria(searchCriteria);

    // then
    assertThat(foundItems.getNumberOfElements()).isEqualTo(0);
  }

  @Test
  public void findItemsByName() {

    // given
    final ItemSearchCriteriaTo searchCriteria = new ItemSearchCriteriaTo();
    searchCriteria.setName("Lasagna");
    Sort sort = Sort.by("name");
    Pageable pageable = PageRequest.of(0, 20, sort);
    searchCriteria.setPageable(pageable);

    // when
    Page<ItemEntity> foundItems = this.itemRepository.findByNameAsc(searchCriteria);

    // then
    assertThat(foundItems.getNumberOfElements()).isEqualTo(1);
    assertThat(foundItems.iterator().next().getName()).isEqualTo("Lasagna");
  }

  @Test
  public void updatePriceForCarbonara() {

    // given
    final ItemSearchCriteriaTo searchCriteria = new ItemSearchCriteriaTo();
    searchCriteria.setName("Spaghetti Carbonara");
    Sort sort = Sort.by("name");
    Pageable pageable = PageRequest.of(0, 20, sort);
    searchCriteria.setPageable(pageable);

    // when
    this.itemRepository.setPriceByName("Spaghetti Carbonara", 20.0);
    Page<ItemEntity> foundItems = this.itemRepository.findByNameAsc(searchCriteria);

    // then
    assertThat(foundItems.getNumberOfElements()).isEqualTo(1);
    assertThat(foundItems.iterator().next().getName()).isEqualTo("Spaghetti Carbonara");
    assertThat(foundItems.iterator().next().getPrice()).isEqualTo(20.0);
  }
}
