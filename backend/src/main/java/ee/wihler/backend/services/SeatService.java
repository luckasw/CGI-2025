package ee.wihler.backend.services;

import ee.wihler.backend.dtos.SeatSuggestionRequest;
import ee.wihler.backend.dtos.SeatSuggestionResponse;
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

    public List<List<Seat>> getSeating(Plane plane) {
        List<List<Seat>> seats = new ArrayList<>();
        for (int row = 1; row <= plane.getRows(); row++) {
            List<Seat> rowSeats = new ArrayList<>();
            for (int column = 1; column <= plane.getSeatsPerRow(); column++) {
                rowSeats.add(
                    new Seat(row, column, Math.random() < 0.5, false, false)
                );
            }
            seats.add(rowSeats);
        }

        return seats;
    }

    public SeatSuggestionResponse getSeatingSuggestion(
        Long id,
        SeatSuggestionRequest request
    ) {
        Plane plane = planeRepository.findById(id).orElse(null);
        if (plane == null) {
            return null;
        }

        List<List<Seat>> seating = getSeating(plane);
        int rows = plane.getRows();
        int columns = plane.getSeatsPerRow();

        if (request.getTickets() > 1 && request.getTogether()) {
            for (List<Seat> seatRow : seating) {
                int freeSeatsInRow = 0;
                int startIndex = -1;

                for (int i = 0; i < columns; i++) {
                    if (!seatRow.get(i).isOccupied()) {
                        if (freeSeatsInRow == 0) {
                            startIndex = i;
                        }
                        freeSeatsInRow++;
                    } else {
                        freeSeatsInRow = 0;
                        startIndex = -1;
                    }
                    int foundSeats = 0;
                    if (freeSeatsInRow >= request.getTickets()) {
                        for (int j = 0; j < request.getTickets(); j++) {
                            Seat seat = seatRow.get(startIndex + j);
                            int row = seat.getRow();
                            int column = seat.getColumn();
                            if (fulfills(request, row, column, rows, columns)) {
                                seat.setSuggested(true);
                                seat.setSelected(true);
                                foundSeats++;
                            }
                        }
                        if (foundSeats == request.getTickets()) {
                            return new SeatSuggestionResponse(seating, true);
                        }
                    }
                }
            }
        } else {
            int foundSeats = 0;
            for (List<Seat> seatRow : seating) {
                for (Seat seat : seatRow) {
                    if (!seat.isOccupied()) {
                        int row = seat.getRow();
                        int column = seat.getColumn();
                        if (fulfills(request, row, column, rows, columns)) {
                            seat.setSuggested(true);
                            seat.setSelected(true);
                            foundSeats++;
                            if (foundSeats == request.getTickets()) {
                                return new SeatSuggestionResponse(
                                    seating,
                                    true
                                );
                            }
                        }
                    }
                }
            }
        }

        return new SeatSuggestionResponse(seating, false);
    }

    private Boolean fulfills(
        SeatSuggestionRequest request,
        int row,
        int column,
        int rows,
        int columns
    ) {
        Boolean fulfilled = true;
        if (request.getNearWindow() && column != 1 && column != columns) {
            fulfilled = false;
        }
        if ((request.getMoreLegSpace() && row != (rows / 2)) || row == 1) {
            fulfilled = false;
        }
        if (request.getNearExit() && row > rows / 3) {
            fulfilled = false;
        }

        return fulfilled;
    }
}
