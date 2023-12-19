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
            case Approval approval when approval.amount() >= request.amount() -> "Loan approved, granted full amount";
            case Approval approval -> String.format("Loan approved, amount granted: %d", approval.amount());
            case Refusal refusal -> String.format("Loan refused due to: %s", refusal.reason());
            case Suspension suspension -> {
                var builder = new StringBuilder();
                builder.append("Loan processing suspended.\n");
                builder.append("Following additional requirements are needed to make final decision:\n");
                for (var requirement : suspension.additionalRequirements()) {
                    builder.append(requirement);
                    builder.append("\n");
                }
                builder.append(String.format("Deadline to fulfill requirements mentioned above: %s", suspension.deadline()));
                yield builder.toString();
            }
            default -> throw new IllegalStateException("Unknown result type");
        };
    }
}
