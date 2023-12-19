package eu.chrost.loan;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EvaluatorTest {
    private final Calendar calendar = mock(Calendar.class);
    private final Evaluator evaluator = new Evaluator(calendar);

    @Test
    void refusalDueToAmountTooBig() {
        //given
        var request = new Request(25_000, 3);

        //when
        var result = evaluator.processRequest(request);

        //then
        assertThat(result).isInstanceOfSatisfying(Refusal.class,
                refusal -> assertThat(refusal.getReason()).isEqualTo("Amount is too big"));
    }

    @Test
    void suspensionWithEmployeeReference() {
        //given
        var request = new Request(10_000, 2);
        when(calendar.now()).thenReturn(LocalDate.parse("2024-12-31"));

        //when
        var result = evaluator.processRequest(request);

        //then
        assertThat(result).isInstanceOfSatisfying(Suspension.class,
                suspension -> assertThat(suspension)
                        .extracting(Suspension::getAdditionalRequirements, Suspension::getDeadline)
                        .containsExactly(Arrays.asList("Employee reference"), LocalDate.parse("2025-01-10"))
        );
    }

    @Test
    void approvalWithReducedAmount() {
        //given
        var request = new Request(1_000, 2);

        //when
        var result = evaluator.processRequest(request);

        //then
        assertThat(result).isInstanceOfSatisfying(Approval.class,
                approval -> assertThat(approval.getAmount()).isEqualTo(800));
    }


}
