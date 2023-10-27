package eu.chrost.loan;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FormatterTest {
    @Test
    void approvalFullAmount() {
        //given
        Request request = new Request(1000, 2);
        Result result = new Approval(1000);

        //when
        Response response = new Formatter().prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::getType, Response::getMessage)
                .containsExactly("APPROVAL", "Loan approved, granted full amount");
    }

    @Test
    void approvalReducedAmount() {
        //given
        Request request = new Request(1000, 2);
        Result result = new Approval(800);

        //when
        Response response = new Formatter().prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::getType, Response::getMessage)
                .containsExactly("APPROVAL", "Loan approved, amount granted: 800");
    }

    @Test
    void refusal() {
        //given
        Request request = new Request(1000, 2);
        Result result = new Refusal("Period too long");

        //when
        Response response = new Formatter().prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::getType, Response::getMessage)
                .containsExactly("REFUSAL", "Loan refused due to: Period too long");
    }

    @Test
    void suspension() {
        //given
        Request request = new Request(1000, 2);
        Result result = new Suspension(
                Arrays.asList("Employee reference", "Application form"),
                LocalDate.parse("2024-12-31"));

        //when
        Response response = new Formatter().prepareResponse(request, result);

        //then
        assertThat(response).extracting(Response::getType, Response::getMessage)
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
