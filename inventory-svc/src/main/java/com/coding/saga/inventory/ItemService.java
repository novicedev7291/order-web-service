package com.coding.saga.inventory;

import com.coding.saga.domain.model.InventoryItem;
import com.coding.saga.inventory.entity.Event;
import com.coding.saga.inventory.entity.EventFactory;
import com.coding.saga.inventory.entity.Item;
import com.coding.saga.inventory.repository.EventRepository;
import com.coding.saga.inventory.repository.ItemRepository;
import com.coding.saga.inventory.util.CsvUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repo;
    private final EventRepository eventRepo;

    public void bulkAdd(InputStream is) {

        List<ItemDto> itemDataList = CsvUtil.readCsv(is);

        List<Item> items = itemDataList.stream().map(this::create).toList();

        items = repo.saveAll(items);

        List<Event> addEvents = items.stream().map(this::createAddEvent).toList();
        eventRepo.saveAll(addEvents);
    }

    private Item create(ItemDto data) {
        Item item = Item.of(data.sku(), data.name(), data.price());
        item.setQuantity(data.initialQty());
        return item;
    }

    private Event createAddEvent(Item item) {
        InventoryItem payload = transform(item);
        return EventFactory.create(
                item.getId().toString(),
                "inventory",
                EventType.ITEM_ADDED,
                payload
        );
    }

    private InventoryItem transform(Item item) {
        return new InventoryItem(
                item.getId(),
                item.getSku(),
                item.getName(),
                item.getInventory().getFreeQty(),
                item.getPrice()
        );
    }
}
