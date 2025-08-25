package com.example.RoomManagementSystem.services;

import com.example.RoomManagementSystem.domain.entities.Team;
import com.example.RoomManagementSystem.domain.pagination.PaginationRequest;
import com.example.RoomManagementSystem.domain.pagination.PagingResult;

import java.util.UUID;

public interface TeamService {

    PagingResult<Team> getAllTeams(PaginationRequest paginationRequest);

    Team getTeamById(UUID id);

    Team createTeam(Team team);

    Team updateTeam(UUID id, Team team);

    void deleteTeam(UUID id);
}
