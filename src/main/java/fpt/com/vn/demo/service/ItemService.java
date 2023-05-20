package fpt.com.vn.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fpt.com.vn.demo.model.Item;
import fpt.com.vn.demo.repositories.ItemRepository;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public Item addNewItem(Item item){
        itemRepository.save(item);
        return itemRepository.getLastestItem();
    }
    public List<Item> getAllItem(){
        return itemRepository.findAll();
    }
    public Item getItemById(Integer id){
        Optional<Item> op = itemRepository.findById(id);
        if(op.isPresent()) return op.get();
        return null;
    }
}
