package lyan.artyom.controller.response;

import lyan.artyom.controller.dao.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class SearchResponse {
    private List<MenuItem> itemList;

    public SearchResponse(){
        itemList = new ArrayList<>();

    }

    public void addItem(MenuItem item) {
        itemList.add(item);
    }

    public List<MenuItem> getItemList() {
        return itemList;
    }
}
