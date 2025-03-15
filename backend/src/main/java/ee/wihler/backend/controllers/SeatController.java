package ee.wihler.backend.controllers;

import ee.wihler.backend.dtos.SeatSuggestionRequest;
import ee.wihler.backend.dtos.SeatSuggestionResponse;
import ee.wihler.backend.entities.Seat;
import ee.wihler.backend.services.SeatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/{id}")
    public SeatSuggestionResponse getSeating(
        @PathVariable Long id,
        @RequestBody SeatSuggestionRequest request
    ) {
        return seatService.getSeatingSuggestion(id, request);
    }
}
