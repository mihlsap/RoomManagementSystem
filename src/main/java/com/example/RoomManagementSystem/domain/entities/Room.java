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

    @Column(name = "number", nullable = false, updatable = false)
    private int number;

    @Column(name = "floor", nullable = false, updatable = false)
    private int floor;

    @Column(name = "size", nullable = false, updatable = false)
    private RoomSize size;

    @Column(name = "seats", nullable = false)
    private int seats;

    public Room() {
    }

    public Room(UUID id, int number, int floor, RoomSize size, int seats) {
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public RoomSize getSize() {
        return size;
    }

    public void setSize(RoomSize size) {
        this.size = size;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return number == room.number && floor == room.floor && seats == room.seats && Objects.equals(id, room.id) && size == room.size;
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
