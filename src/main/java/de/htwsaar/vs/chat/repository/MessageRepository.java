package de.htwsaar.vs.chat.repository;

import de.htwsaar.vs.chat.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
* {@link Message}용 Spring 데이터 저장소.
 * @author Niklas Reinhard
 */
@Repository
public interface MessageRepository extends ReactiveCrudRepository<Message, String> {

    Flux<Message> findAllByChatId(String chatId);

    Flux<Message> findAllByChatId(String chatId, Pageable pageable);
}
