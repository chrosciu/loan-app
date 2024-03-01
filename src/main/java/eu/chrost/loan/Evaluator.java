package eu.chrost.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class Evaluator {
    private final Calendar calendar;

    public Result processRequest(Request request) {
        return switch (request) {
            case Request(var amount, var years) when amount > 20_000 -> new Refusal("Amount is too big");
            case Request(var amount, var years) when amount > 5_000 && years > 1 ->
                    new Suspension(Collections.singletonList("Employee reference"), calendar.now().plusDays(10));
            case Request(var amount, var years) -> new Approval(amount * 4 / 5);
        };
    }
}
