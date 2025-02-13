package ru.job4j.socialmedia.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmedia.model.Subscription;
import ru.job4j.socialmedia.model.User;
import ru.job4j.socialmedia.repository.SubscriptionRepository;
import ru.job4j.socialmedia.repository.UserRepository;
import ru.job4j.socialmedia.service.PostService;
import ru.job4j.socialmedia.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class SubscribeService {
    private final UserService userService;
    private final PostService postService;
    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;

    public boolean subscribe(int userId, int friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);
        boolean result = false;
        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();
            user.getFriends().add(friend);
            userRepository.save(user);
            result = true;
        }
        return result;
    }

    public boolean deleteFromFriends(int userId, int friendId) {
        boolean result = false;
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);
        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();
            user.getFriends().remove(friend);
            subscriptionRepository.deleteBySubscriberIdAndSubscriberToId(userId, friendId);
            result = true;
        }
        return result;
    }

    public boolean sendFriendshipRequest(int userFrom, int userTo) {
        boolean result = false;
        Optional<User> from = userRepository.findById(userFrom);
        Optional<User> to = userRepository.findById(userTo);
        if (from.isPresent() && to.isPresent()) {
            Subscription subscription = new Subscription().setSubscriber(from.get()).setSubscriberTo(to.get());
            subscriptionRepository.save(subscription);
            result = true;
        }
        return result;
    }

    public void acceptFriendshipRequest(int userFrom, int userTo, boolean accept) {
        Optional<User> subscriber = userRepository.findById(userFrom);
        Optional<User> user = userRepository.findById(userTo);
        if (accept && subscriber.isPresent() && user.isPresent()) {
            user.get().getFriends().add(subscriber.get());
            userRepository.save(user.get());
            subscriber.get().getFriends().add(user.get());
            userRepository.save(subscriber.get());
        }
    }

    public boolean unsubscribe(int subscriberId, int userId) {
        int deletedRows = 0;
        Optional<User> subscriberOpt = userRepository.findById(subscriberId);
        Optional<User> byId = userRepository.findById(userId);
        if (subscriberOpt.isPresent() && byId.isPresent()) {
            deletedRows = subscriptionRepository.deleteBySubscriberIdAndSubscriberToId(subscriberId, userId);
        }
        return deletedRows > 0;
    }

    public List<User> getSubscriptionRequest(int userId) {
        Optional<User> userOpt = userRepository.findById(userId);
        List<User> subscribersWithoutFriendship = new ArrayList<>();
        if (userOpt.isPresent()) {
            List<Integer> subscriptionWithoutFriedshipIds = new ArrayList<>();
            User user = userOpt.get();
            List<Integer> friendIds = user.getFriends().stream().map(User::getId).toList();
            List<Integer> subscribersIds = subscriptionRepository.findAllBySubscriberToId(userId)
                    .stream()
                    .map(subscription -> subscription.getSubscriber().getId())
                    .toList();
            for (Integer subscriberId : subscribersIds) {
                if (!friendIds.contains(subscriberId)) {
                    subscriptionWithoutFriedshipIds.add(subscriberId);
                }
            }
            subscribersWithoutFriendship = userRepository.findByIdIn(subscriptionWithoutFriedshipIds);
        }
        return subscribersWithoutFriendship;
    }
}
