package ee.wihler.backend.dtos;

import lombok.Data;

@Data
public class SeatSuggestionRequest {

    private Boolean nearWindow;
    private Boolean moreLegSpace;
    private Boolean nearExit;
    private Boolean together;
    private int tickets;
}
