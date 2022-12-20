package ua.home.finances.finances.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.finances.common.Currency;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    private Long id;
    private String name;
    private long coins;
    private Currency currency;
    private LocalDate date;
}
