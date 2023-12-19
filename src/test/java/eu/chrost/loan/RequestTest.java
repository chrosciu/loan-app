package eu.chrost.loan;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RequestTest {
    @Test
    void shouldNotAllowToPassNegativeAmount() {
        assertThatThrownBy(() -> new Request(-100, 2))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAllowToPassZeroYears() {
        assertThatThrownBy(() -> new Request(100, 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAllowToPassNegativeYears() {
        assertThatThrownBy(() -> new Request(100, -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldAllowToPassValidValue() {
        var request = new Request(100, 2);
        assertThat(request).extracting(Request::amount, Request::years)
                .containsExactly(100L, 2);
    }


}
