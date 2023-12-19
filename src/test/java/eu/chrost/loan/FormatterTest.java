package eu.chrost.loan;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FormatterTest {
    private final Formatter formatter = new Formatter();

    @Test
    void approvalFullAmount() {
        //given
        var request = new Request(1000, 2);
        var result = new Approval(1000);

        //when
        var response = formatter.prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::type, Response::message)
                .containsExactly("APPROVAL", "Loan approved, granted full amount");
    }

    @Test
    void approvalReducedAmount() {
        //given
        var request = new Request(1000, 2);
        var result = new Approval(800);

        //when
        var response = formatter.prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::type, Response::message)
                .containsExactly("APPROVAL", "Loan approved, amount granted: 800");
    }

    @Test
    void refusal() {
        //given
        var request = new Request(1000, 2);
        var result = new Refusal("Period too long");

        //when
        var response = formatter.prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::type, Response::message)
                .containsExactly("REFUSAL", "Loan refused due to: Period too long");
    }

    @Test
    void suspension() {
        //given
        var request = new Request(1000, 2);
        var result = new Suspension(
                List.of("Employee reference", "Application form"),
                LocalDate.parse("2024-12-31"));

        //when
        var response = formatter.prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::type, Response::message)
                .containsExactly(
                        "SUSPENSION",
                        "Loan processing suspended.\n" +
                        "Following additional requirements are needed to make final decision:\n" +
                        "Employee reference\n" +
                        "Application form\n" +
                        "Deadline to fulfill requirements mentioned above: 2024-12-31"
                );
    }
}
