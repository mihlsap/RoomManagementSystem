package com.example.RoomManagementSystem.domain.entities;

import com.example.RoomManagementSystem.domain.enums.RoomSize;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "floor", nullable = false, updatable = false)
    private Integer floor;

    @Column(name = "size", nullable = false)
    private RoomSize size;

    @Column(name = "seats", nullable = false)
    private Integer seats;
}
