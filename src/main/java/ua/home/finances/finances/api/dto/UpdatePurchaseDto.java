package ua.home.finances.finances.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.finances.db.models.Purchase;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePurchaseDto {
    private Long id;
    private String name;
    private long coins;
    private String currency;
    private String date;

    public static Purchase map(UpdatePurchaseDto updatePurchaseDto) {
        return Purchase.builder()
                //TODO:
                .build();
    }
}
