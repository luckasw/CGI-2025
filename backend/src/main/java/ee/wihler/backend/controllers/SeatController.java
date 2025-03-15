package ee.wihler.backend.controllers;

import ee.wihler.backend.entities.Seat;
import ee.wihler.backend.services.SeatService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/{id}")
    public List<Seat> getSeating(@PathVariable Long id) {
        return seatService.getSeating(id);
    }
}
