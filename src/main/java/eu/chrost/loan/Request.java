package eu.chrost.loan;

import lombok.Value;

@Value
public class Request {
    long amount;
    int years;
}
