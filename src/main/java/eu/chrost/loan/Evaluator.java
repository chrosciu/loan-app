package eu.chrost.loan;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class Evaluator {
    public Result processRequest(Request request) {
        if (request.amount() > 20_000) {
            return new Refusal("Amount is too big");
        }
        if (request.amount() > 5_000 && request.years() > 1) {
            return new Suspension(List.of("Employee reference"), LocalDate.now().plusDays(10));
        }
        return new Approval(request.amount() * 4 / 5);
    }
}
