package ee.wihler.backend.dtos;

import ee.wihler.backend.entities.Seat;
import java.util.List;
import lombok.Data;

@Data
public class SeatSuggestionResponse {

    private List<List<Seat>> seating;
    private Boolean success;

    public SeatSuggestionResponse(List<List<Seat>> seating, Boolean success) {
        this.seating = seating;
        this.success = success;
    }
}
