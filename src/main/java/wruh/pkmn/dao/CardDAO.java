package wruh.pkmn.dao;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import wruh.pkmn.entities.CardEntity;
import wruh.pkmn.repositorie.CardRepository;
import wruh.pkmn.models.Card;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CardDAO {
    private final CardRepository cardRepository;

    @SneakyThrows
    public Card createCard(Card card) {
        return Card.fromEntity(cardRepository.save(CardEntity.toEntity(card)));
    }

    @SneakyThrows
    public List<Card> getAllCards() {
        return cardRepository.findAll().stream().map(Card::fromEntity).toList();
    }

    @SneakyThrows
    public Card getCardById(UUID id) {
        return Card.fromEntity(cardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Карта с id: " + id.toString() + ", не найдена"))
        );
    }

    @SneakyThrows
    public Card getCardByName(String name) {
        return Card.fromEntity(cardRepository.findByName(name).orElseThrow(
                () -> new IllegalArgumentException("Карта с именем: " + name + ", не найдена"))
        );
    }

    @SneakyThrows
    public Card getCardByOwnerFullName(String firstName, String surName, String familyName) {
        return Card.fromEntity(cardRepository.findByOwnerFullName(
                firstName, surName, familyName
        ).orElseThrow(
                () -> new IllegalArgumentException(
                        "Карта с владельцем: " + firstName + " " + surName + " " + familyName + ", не найдена"
                )
        ));
    }
}
