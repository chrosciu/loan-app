package eu.chrost.loan;

public record Request(long amount, int years) {
    public Request {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (years < 1) {
            throw new IllegalArgumentException("Zero or negative years amount not allowed");
        }
    }
}
