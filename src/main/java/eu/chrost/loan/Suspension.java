package eu.chrost.loan;

import java.time.LocalDate;
import java.util.List;

public record Suspension(List<String> additionalRequirements, LocalDate deadline) implements Result {
    public Suspension(List<String> additionalRequirements, LocalDate deadline) {
        this.additionalRequirements = List.copyOf(additionalRequirements);
        this.deadline = deadline;
    }
}
