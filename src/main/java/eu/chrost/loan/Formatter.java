package eu.chrost.loan;

import org.springframework.stereotype.Service;

@Service
public class Formatter {
    public Response prepareResponse(Request request, Result result) {
        return new Response(getResponseType(result), prepareMessage(request, result));
    }

    private String getResponseType(Result result) {
        return result.getClass().getSimpleName().toUpperCase();
    }

    private String prepareMessage(Request request, Result result) {
        return switch (result) {
            case Approval(var amount) when amount >= request.amount() -> "Loan approved, granted full amount";
            case Approval(var amount) -> String.format("Loan approved, amount granted: %d", amount);
            case Refusal(var reason) -> String.format("Loan refused due to: %s", reason);
            case Suspension suspension -> buildMessageForSuspension(suspension);
        };
    }

    private String buildMessageForSuspension(Suspension suspension) {
        var builder = new StringBuilder("""
                Loan processing suspended.
                Following additional requirements are needed to make final decision:
                """);
        for (var requirement : suspension.additionalRequirements()) {
            builder.append(requirement);
            builder.append("\n");
        }
        builder.append(String.format("Deadline to fulfill requirements mentioned above: %s", suspension.deadline()));
        return builder.toString();
    }
}
