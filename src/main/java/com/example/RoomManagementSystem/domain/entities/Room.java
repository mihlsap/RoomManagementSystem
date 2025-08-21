package com.example.RoomManagementSystem.domain.entities;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

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

    public Room() {
    }

    public Room(UUID id, Integer number, Integer floor, RoomSize size, Integer seats) {
        this.id = id;
        this.number = number;
        this.floor = floor;
        this.size = size;
        this.seats = seats;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public RoomSize getSize() {
        return size;
    }

    public void setSize(RoomSize size) {
        this.size = size;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(number, room.number) && Objects.equals(floor, room.floor) && size == room.size && Objects.equals(seats, room.seats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number, floor, size, seats);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", number=" + number +
                ", floor=" + floor +
                ", size=" + size +
                ", seats=" + seats +
                '}';
    }
}
