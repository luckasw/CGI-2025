package ee.wihler.backend.services;

import ee.wihler.backend.entities.Plane;
import ee.wihler.backend.entities.Seat;
import ee.wihler.backend.repositories.FlightRepository;
import ee.wihler.backend.repositories.PlaneRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    @Autowired
    private PlaneRepository planeRepository;

    @Autowired
    private FlightRepository flightRepository;

    public List<Seat> getSeating(Long id) {
        List<Seat> seats = new ArrayList<>();
        Plane plane = planeRepository.findById(id).orElse(null);
        if (plane == null) {
            return null;
        }

        for (int row = 1; row <= plane.getRows(); row++) {
            for (int column = 1; column <= plane.getSeatsPerRow(); column++) {
                seats.add(new Seat(row, column, Math.random() < 0.5));
            }
        }

        return seats;
    }
}
