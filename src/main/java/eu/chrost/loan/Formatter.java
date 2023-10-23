package eu.chrost.loan;

import org.springframework.stereotype.Service;

@Service
public class Formatter {
    public Response prepareResponse(Request request, Result result) {
        return new Response(result.getClass().getSimpleName().toUpperCase(),
                prepareMessage(request, result));
    }

    private String prepareMessage(Request request, Result result) {
        return switch (result) {
            case Approval(var amount) when amount >= request.amount() ->
                    "Loan approved, granted full amount";
            case Approval(var amount) ->
                    String.format("Loan approved, amount granted: %d", amount);
            case Refusal(var reason) -> String.format("Loan refused due to: %s", reason);
            case Suspension(var additionalRequirements, var deadline) -> {
                StringBuilder builder = new StringBuilder();
                builder.append("Loan processing suspended.");
                builder.append("Following additional requirements are needed to make final decision: ");
                for (String requirement : additionalRequirements) {
                    builder.append(requirement);
                }
                builder.append(String.format("Deadline to fulfill requirements mentioned above: %s%n", deadline));
                yield builder.toString();
            }
        };
    }
}
