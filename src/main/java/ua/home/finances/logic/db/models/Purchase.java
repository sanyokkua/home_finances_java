package ua.home.finances.logic.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.common.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Purchase {
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE; // 2011-12-03
    private Long purchaseId;
    private Long purchaseListId;
    private String name;
    private long coins;
    private Currency currency;
    private LocalDate date;
    private String category;
}
