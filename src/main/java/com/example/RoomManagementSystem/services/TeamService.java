package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Team;

import java.util.List;
import java.util.UUID;

public interface TeamService {

    List<Team> getAllTeams();

    Team getTeamById(UUID id);

    Team createTeam(Team team);

    Team updateTeam(UUID id, Team team);

    void deleteTeam(UUID id);
}
