package eu.chrost.loan;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class Calendar {
    public LocalDate now() {
        return LocalDate.now();
    }
}
