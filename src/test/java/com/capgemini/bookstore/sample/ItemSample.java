package com.capgemini.bookstore.sample;

import com.capgemini.bookstore.entity.Item;
import com.capgemini.bookstore.generated.model.ItemDto;

public class ItemSample {

    public static final int ID = 33;
    public static final int QUANTITY = 1;

    public static Item getEntity() {
        Item item = new Item();
        item.setId(ID);
        item.setQuantity(QUANTITY);
        item.setBook(BookSample.getEntity());
        return item;
    }

    public static ItemDto getDto() {
        ItemDto item = new ItemDto();
        item.setId(ID);
        item.setQuantity(QUANTITY);
        item.setBookId(BookSample.ID);
        return item;
    }
}
