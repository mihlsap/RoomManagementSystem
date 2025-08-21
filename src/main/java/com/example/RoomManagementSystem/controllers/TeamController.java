package com.example.RoomManagementSystem.controllers;

import com.example.RoomManagementSystem.domain.entities.Team;
import com.example.RoomManagementSystem.services.TeamService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@PreAuthorize("hasRole('admin')")
@RequestMapping(path = "teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PreAuthorize("permitAll()")
    @GetMapping("all")
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PreAuthorize("permitAll()")
    @GetMapping("{team_id}")
    public Team getTeamById(@PathVariable("team_id") UUID id) {
        return teamService.getTeamById(id);
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @PutMapping("{team_id}")
    public Team updateTeam(@PathVariable("team_id") UUID id, @RequestBody Team team) {
        return teamService.updateTeam(id, team);
    }

    @DeleteMapping("{team_id}")
    public void deleteTeam(@PathVariable("team_id") UUID id) {
        teamService.deleteTeam(id);
    }
}
