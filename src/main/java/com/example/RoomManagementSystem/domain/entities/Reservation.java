package com.example.RoomManagementSystem.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    //TODO change team and room to be fetched by id
    @JoinColumn(name = "team", referencedColumnName = "id", nullable = false)
    private UUID teamId;

    @JoinColumn(name = "owner_id", referencedColumnName = "sub",  nullable = false)
    private UUID ownerId;

    @JoinColumn(name = "room_id", referencedColumnName = "id", nullable = false)
    private UUID roomId;

    public Reservation() {
    }

    public Reservation(UUID id, String title, String description, LocalDateTime date, LocalDateTime created, LocalDateTime updated, UUID teamId, UUID ownerId, UUID roomId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.created = created;
        this.updated = updated;
        this.teamId = teamId;
        this.ownerId = ownerId;
        this.roomId = roomId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    public UUID getTeamId() {
        return teamId;
    }

    public void setTeamId(UUID teamId) {
        this.teamId = teamId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(date, that.date) && Objects.equals(created, that.created) && Objects.equals(updated, that.updated) && Objects.equals(teamId, that.teamId) && Objects.equals(ownerId, that.ownerId) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, date, created, updated, teamId, ownerId, roomId);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", created=" + created +
                ", updated=" + updated +
                ", teamId=" + teamId +
                ", ownerId=" + ownerId +
                ", roomId=" + roomId +
                '}';
    }
}
