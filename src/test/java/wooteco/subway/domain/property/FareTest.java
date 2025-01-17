package wooteco.subway.domain.property;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import wooteco.subway.domain.property.Age;
import wooteco.subway.domain.property.Distance;
import wooteco.subway.domain.property.Fare;

class FareTest {

    @Test
    @DisplayName("기본 요금은 1250원이다.")
    public void calculateFare() {
        assertThat(Fare.calculate(new Distance(5), Age.from(20), 0)).isEqualTo(1250);
    }

    @ParameterizedTest
    @CsvSource({"9,1250", "12,1350", "16,1450", "58,2150"})
    @DisplayName("성인의 경우 할인 없이 거리에 맞는 요금을 구한다.")
    public void calculateFareByDistance(int distance, int expectedFare) {
        assertThat(Fare.calculate(new Distance(distance), Age.from(20), 0)).isEqualTo(expectedFare);
    }

    @ParameterizedTest
    @CsvSource({"9,1070", "12,1150", "16,1230", "58,1790"})
    @DisplayName("청소년의 경우 350원을 공제한 금액의 20%를 할인한다.")
    public void discountToTeenager(int distance, int expectedFare) {
        assertThat(Fare.calculate(new Distance(distance), Age.from(15), 0)).isEqualTo(expectedFare);
    }

    @ParameterizedTest
    @CsvSource({"9,800", "12,850", "16,900", "58,1250"})
    @DisplayName("어린이의 경우 350원을 공제한 금액의 50%를 할인한다.")
    public void discountChildren(int distance, int expectedFare) {
        assertThat(Fare.calculate(new Distance(distance), Age.from(8), 0)).isEqualTo(expectedFare);
    }

    @Test
    @DisplayName("추가 금액이 있는 경우 함께 더한다.")
    public void extraFare() {
        assertThat(Fare.calculate(new Distance(5), Age.from(20), 100)).isEqualTo(1350);
    }

    @Test
    @DisplayName("영유아의 경우 0원이다.")
    void baby() {
        assertThat(Fare.calculate(new Distance(100), Age.from(3), 500)).isEqualTo(0);
    }

}
