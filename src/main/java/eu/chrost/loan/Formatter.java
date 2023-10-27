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
        if (result instanceof Approval) {
            Approval approval = (Approval) result;
            long amount = approval.getAmount();
            if (amount >= request.getAmount()) {
                return "Loan approved, granted full amount";
            }
            return String.format("Loan approved, amount granted: %d", amount);
        } else if (result instanceof Refusal) {
            Refusal refusal = (Refusal) result;
            return String.format("Loan refused due to: %s", refusal.getReason());
        } else if (result instanceof Suspension) {
            Suspension suspension = (Suspension) result;
            StringBuilder builder = new StringBuilder();
            builder.append("Loan processing suspended.\n");
            builder.append("Following additional requirements are needed to make final decision:\n");
            for (String requirement : suspension.getAdditionalRequirements()) {
                builder.append(requirement);
                builder.append("\n");
            }
            builder.append(String.format("Deadline to fulfill requirements mentioned above: %s", suspension.getDeadline()));
            return builder.toString();
        }
        throw new IllegalStateException("Unknown result type");
    }
}
