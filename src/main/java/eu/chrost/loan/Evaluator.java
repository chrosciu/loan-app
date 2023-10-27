package eu.chrost.loan;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class Evaluator {
    public Result processRequest(Request request) {
        if (request.getAmount() > 20_000) {
            return new Refusal("Amount is too big");
        }
        if (request.getAmount() > 5_000 && request.getYears() > 1) {
            return new Suspension(Collections.singletonList("Employee reference"), LocalDate.now().plusDays(10));
        }
        return new Approval(request.getAmount() * 4 / 5);
    }
}
