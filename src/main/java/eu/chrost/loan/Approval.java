package eu.chrost.loan;

import lombok.Value;

@Value
public class Approval implements Result {
    long amount;
}
