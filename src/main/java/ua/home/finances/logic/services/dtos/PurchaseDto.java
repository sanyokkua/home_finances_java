package ua.home.finances.logic.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.common.Currency;
import ua.home.finances.logic.db.models.Purchase;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDto {
    private Long purchaseListId;
    private Long purchaseId;
    private String name;
    private long coins;
    private Currency currency;
    private LocalDate date;
    private String category;

    public static PurchaseDto fromModel(Purchase purchase) {
        return PurchaseDto.builder()
                .purchaseId(purchase.getPurchaseId())
                .purchaseListId(purchase.getPurchaseListId())
                .name(purchase.getName())
                .coins(purchase.getCoins())
                .currency(purchase.getCurrency())
                .date(purchase.getDate())
                .category(purchase.getCategory())
                .build();
    }

    public static Purchase fromDto(PurchaseDto purchaseDto) {
        return Purchase.builder()
                .purchaseId(purchaseDto.getPurchaseId())
                .purchaseListId(purchaseDto.getPurchaseListId())
                .name(purchaseDto.getName())
                .coins(purchaseDto.getCoins())
                .currency(purchaseDto.getCurrency())
                .date(purchaseDto.getDate())
                .category(purchaseDto.getCategory())
                .build();
    }
}
