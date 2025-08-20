package com.example.RoomManagementSystem.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('admin')")
@RequestMapping(path = "rooms")
public class RoomController {
    // TODO:
    //  implement adding, retrieving, deleting and modifying rooms
}
