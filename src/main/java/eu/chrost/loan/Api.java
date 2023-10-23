package eu.chrost.loan;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Api {
    private final Evaluator evaluator;
    private final Formatter formatter;

    @PostMapping
    public Response handle(@RequestBody Request request) {
        var result = evaluator.processRequest(request);
        return formatter.prepareResponse(request, result);
    }
}
