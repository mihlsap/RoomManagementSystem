package com.example.RoomManagementSystem.services.impl;

import com.example.RoomManagementSystem.domain.entities.Team;
import com.example.RoomManagementSystem.domain.pagination.PaginationRequest;
import com.example.RoomManagementSystem.domain.pagination.PaginationUtils;
import com.example.RoomManagementSystem.domain.pagination.PagingResult;
import com.example.RoomManagementSystem.repositories.TeamRepository;
import com.example.RoomManagementSystem.services.TeamService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    public PagingResult<Team> getAllTeams(PaginationRequest paginationRequest) {
        final Pageable pageable = PaginationUtils.getPageable(paginationRequest);
        final Page<Team> teams = teamRepository.findAll(pageable);
        return new PagingResult<>(
                teams.getContent(),
                teams.getTotalPages(),
                teams.getTotalElements(),
                teams.getSize(),
                teams.getNumber(),
                teams.isEmpty()
        );
    }

    @Override
    public Team getTeamById(UUID id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Override
    public Team createTeam(Team team) {

        validateInput(team);

        return teamRepository.save(new Team(
                null,
                team.getName(),
                team.getSize()
        ));
    }

    @Transactional
    @Override
    public Team updateTeam(UUID id, Team team) {

        if (Objects.isNull(team))
            throw new IllegalArgumentException("Team cannot be null");

        Team existingTeam = teamRepository.findById(id).orElse(null);

        if (existingTeam == null)
            throw new IllegalArgumentException("Team does not exist!");

        if (team.getName() != null)
            existingTeam.setName(team.getName());
        if (team.getSize() != null)
            existingTeam.setSize(team.getSize());

        return teamRepository.save(existingTeam);
    }

    @Override
    public void deleteTeam(UUID id) {
        teamRepository.deleteById(id);
    }

    private void validateInput(Team team) {

        if (Objects.isNull(team))
            throw new IllegalArgumentException("Team cannot be null");

        if (team.getId() != null)
            throw new IllegalArgumentException("Team already has an id!");

        if (team.getName() == null || team.getName().isEmpty())
            throw new IllegalArgumentException("Team name cannot be empty!");

        if (team.getSize() == null || team.getSize() == 0)
            throw new IllegalArgumentException("Team size cannot be empty!");
    }
}
