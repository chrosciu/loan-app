package eu.chrost.loan;

import lombok.Value;

@Value
public class Refusal implements Result {
    String reason;
}
