package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service  // new class로 객체를 생성할 필요 없이 spring한테 자동으로 생성하게 부탁하는 에너테이션
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public void saveItem(String title, Integer price,String username){
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setUsername(username);
        // ModelAtribute를 통해서 한번에 사용할 수 있다.
        itemRepository.save(item);
    }

    public String updateItem (int id, String title, Integer price, Model model ){
        Item item = new Item();
        Optional<Item> result = itemRepository.findById(id);

        if (title.length() >= 100 ){
            model.addAttribute("item",result.get());
            System.out.println("---------------100자 이상입니다. ----------");
            return "edit.html";
        }else if(price <= -1){
            model.addAttribute("item",result.get());
            System.out.println("---------------음수 입니다. ----------");
            return "edit.html";
        }
        else {
            item.setId(id);
            item.setTitle(title);
            item.setPrice(price);
            itemRepository.save(item);
            return "redirect:/list";
        }

    }

//    public String findId(Integer id, Model model){
//        Optional<Item> result = itemRepository.findById(id);
//        if(result.isPresent()){
//            model.addAttribute("result",result.get());
//            return "detail.html";
//        }else {
//            return "redirect:/list";
//        }
//
//    }
}
