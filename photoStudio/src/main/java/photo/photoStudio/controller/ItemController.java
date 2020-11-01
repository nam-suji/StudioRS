package photo.photoStudio.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import photo.photoStudio.controller.form.ItemForm;
import photo.photoStudio.domain.Item;
import photo.photoStudio.service.ItemService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new ItemForm());
        return "admin/createItemForm";
    }

    @PostMapping("/admin/items/new")
    public String create(@Valid ItemForm form, BindingResult result){
        if(result.hasErrors()){
            return  "admin/createItemForm";
        }
        Item item = new Item();
        item.setItem(form.getName(), form.getPrice());

        itemService.saveItem(item);
        return "redirect:/"+"admin/home";
    }

    @GetMapping("/admin/items")
    public String adminList(Model model){
        List<Item> items = itemService.findAll();
        model.addAttribute("items",items);
        return "admin/itemList";
    }

    @GetMapping("/admin/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Item item = (Item) itemService.findOne(itemId);

        ItemForm form = new ItemForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());

        model.addAttribute("form",form);
        return "admin/updateItemForm";
    }

    @PostMapping("/admin/items/{itemId}/edit")
    public String updateItem(@ModelAttribute("form") ItemForm form){
        itemService.updateItem(form.getId(), form.getName(), form.getPrice());
        return "redirect:/"+"admin/home";
    }


    /**
     * 고객용 List
     * */
    @GetMapping("/items/list")
    public String list(Model model){
        List<Item> items = itemService.findAll();

        //studio는 발표회 끝나면 삭제
        List<String> studio = new ArrayList<>();
        studio.add("포토클럽");
        studio.add("다정한 사진관");
        studio.add("아이꼴 스튜디오");
        studio.add("경북 안동시 남문로 2-1");
        studio.add("경북 안동시 풍천면 갈전리 1313");
        studio.add("경북 안동시 경북대로 417");
        model.addAttribute("studio",studio);


        model.addAttribute("items",items);
        return "items/itemList";
    }
}
