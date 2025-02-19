package ru.job4j.socialmedia.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "subscriptions")
@Data
@Accessors(chain = true)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    @NotNull
    private User subscriber;

    @ManyToOne
    @JoinColumn(name = "subscriber_id_to")
    @NotNull
    private User subscriberTo;
}
