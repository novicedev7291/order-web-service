package com.coding.saga.inventory;

import com.coding.saga.inventory.entity.Item;
import com.coding.saga.inventory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author <a href="kuldeepyadav7291@gmail.com">Kuldeep</a>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repo;

    public void bulkAdd(InputStream is) {

        List<ItemDto> itemDataList = CsvUtil.readCsv(is);

        List<Item> items = itemDataList.stream().map(this::create).collect(toList());

        repo.saveAll(items);
    }

    private Item create(ItemDto data) {
        Item item = Item.of(data.sku(), data.name(), data.price());
        item.setQuantity(data.initialQty());
        return item;
    }
}
