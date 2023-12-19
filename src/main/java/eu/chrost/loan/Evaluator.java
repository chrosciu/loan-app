package eu.chrost.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class Evaluator {
    private final Calendar calendar;

    public Result processRequest(Request request) {
        if (request.amount() > 20_000) {
            return new Refusal("Amount is too big");
        }
        if (request.amount() > 5_000 && request.years() > 1) {
            return new Suspension(Collections.singletonList("Employee reference"), calendar.now().plusDays(10));
        }
        return new Approval(request.amount() * 4 / 5);
    }
}
