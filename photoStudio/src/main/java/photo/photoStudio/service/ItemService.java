package photo.photoStudio.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import photo.photoStudio.domain.Item;
import photo.photoStudio.repository.ItemRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Long saveItem(Item item){
        validateDuplicateItem(item);
        itemRepository.save(item);
        return item.getId();
    }

    private void validateDuplicateItem(Item item) {
        List<Item> findItem = itemRepository.findByName(item.getName());
        if(!findItem.isEmpty()){
            throw new IllegalStateException("이미 등록된 서비스입니다.");
        }
    }
    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }

    public List<Item> findName(String name){
        return itemRepository.findByName(name);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public void updateItem(Long id, String name, int price){
        Item item = itemRepository.findOne(id);
        item.setUpdateItem(id, name, price);
    }

    public void deleteItem(Long id){
        Item item = itemRepository.findOne(id);
        itemRepository.delete(item);
    }

}
