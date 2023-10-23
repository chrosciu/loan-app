package eu.chrost.loan;

public sealed interface Result permits Approval, Refusal, Suspension {
}
