package wruh.pkmn.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wruh.pkmn.services.CardService;
import wruh.pkmn.models.Card;
import wruh.pkmn.models.Student;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("")
    public List<Card> getCards() {
        return cardService.getAllCards();
    }

    @PostMapping("")
    public Card createCard(@RequestBody Card card) {
        return cardService.createCard(card);
    }


    @GetMapping("/{name}")
    public Card getCard(@PathVariable String name) {
        return cardService.getCardByName(name);
    }

    @GetMapping("/owner")
    public Card getCardByOwner(@RequestBody Student student) {
        return cardService.getCardByOwnerData(student.getFirstName(),
                student.getSurName(),
                student.getFamilyName());
    }

    @GetMapping("/id/{id}")
    public Card getCardById(@PathVariable String id) {
        return cardService.getCardById(id);
    }

    @GetMapping("/img/{name}")
    public String getCardWithImg(@PathVariable String name) {
        try {
            return cardService.getImageUrl(name);
        }
        catch (Exception e){
            return null;
        }
    }
}
