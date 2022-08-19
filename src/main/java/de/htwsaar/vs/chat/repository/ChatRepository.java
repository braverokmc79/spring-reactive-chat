package de.htwsaar.vs.chat.repository;

import de.htwsaar.vs.chat.model.Chat;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * {@link Chat}용 Spring 데이터 저장소.
 *
 * @author Niklas Reinhard
 * @author Julian Quint
 */
@Repository
public interface ChatRepository extends ReactiveCrudRepository<Chat, String> {

    Flux<Chat> findAllByMembers(String userId);
}
