package wooteco.subway.dto.response;

import java.util.List;
import java.util.stream.Collectors;

import wooteco.subway.domain.line.Line;
import wooteco.subway.domain.station.StationSeries;

public class LineResponse {
    private final Long id;
    private final String name;
    private final String color;
    private final Integer extraFare;
    private final List<StationResponse> stations;

    public LineResponse(Long id, String name, String color, Integer extraFare, List<StationResponse> stations) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.extraFare = extraFare;
        this.stations = stations;
    }

    public LineResponse() {
        this(null, null, null, null,null);
    }

    public static LineResponse from(Line line) {
        return new LineResponse(
                line.getId(),
                line.getName(),
                line.getColor(),
                line.getExtraFare(),
                StationSeries.fromSectionsAsOrdered(line.getSectionSeries().getSections())
                        .getStations()
                        .stream()
                        .map(StationResponse::from)
                        .collect(Collectors.toList())
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getExtraFare() {
        return extraFare;
    }

    public List<StationResponse> getStations() {
        return stations;
    }
}
