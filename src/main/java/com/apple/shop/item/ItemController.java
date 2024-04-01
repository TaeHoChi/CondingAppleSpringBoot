package com.apple.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


// Controller는 보통 데이터나 html을 보낼때 사용한다.
@Controller
@RequiredArgsConstructor
public class ItemController {

    //Dependency Injection이라고 한다.
    // 1. object를 여러개를 안 뽑아도 되어서 효율적이다. (안하면 계속해서 함수마다 object를 정의해줘야 한다.)
    // 2. 클래스간의 커플링을 줄일 수 있다. (커플링: new 클래스 처럼 객체를 생성하는 것을 말한다.)
//    @RequiredArgsConstructor가 없을 경우 사용
//    @Autowired // 스프링에게 object를 알아서 뽑아서 위에 집어 넣어라는 구문이다.
//    public ItemController(ItemRepository itemRepository, ItemService itemService) {
//        this.itemRepository = itemRepository;
//        this.itemService = itemService;
//    }

    private final ItemRepository itemRepository;

    private final ItemService itemService;

    @GetMapping("/list")
    String list(Model model){
        List<Item> result = itemRepository.findAll();
        // 전달할 데이터 이름과, 데이터를 기입한다.
        model.addAttribute("items",result);
        return "list.html";
    }

    @GetMapping("/write")
    String write(Authentication auth){
        if (auth !=null && auth.isAuthenticated())
            return "write.html";
        else
            return "redirect:/list?error";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price, String username) {
        itemService.saveItem(title, price, username);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Integer id, Model model){
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()){
            model.addAttribute("result",result.get());
            return "detail.html";
        }else {
            return "redirect:/list";
        }
    }

    @GetMapping("/edit/{id}")
    String edit(@PathVariable Integer id, Model model){

        Optional<Item> result = itemRepository.findById(id);

        if(result.isPresent()){
            model.addAttribute("item",result.get());
            return "edit.html";
        }else {
            return "redirect:/list";
        }
    }

    @PostMapping("/edit/{id}")
    String editPost(int id, String title,  Integer price,Model model ){
        return itemService.updateItem(id, title, price, model);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> delete(@PathVariable Integer id ){
        itemRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("삭제완료");
    }

}
