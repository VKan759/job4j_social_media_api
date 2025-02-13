package ru.job4j.socialmedia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import ru.job4j.socialmedia.model.Subscription;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {
    Subscription findBySubscriberId(int subscriberId);

    @Modifying
    int deleteBySubscriberIdAndSubscriberToId(int subscriberId, int subscriberToId);

    List<Subscription> findAllBySubscriberToId(int subscriberId);
}
