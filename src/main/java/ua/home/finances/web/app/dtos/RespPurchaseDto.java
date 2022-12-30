package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.services.dtos.PurchaseDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespPurchaseDto {
    private long purchaseId;
    private String purchaseName;
    private long purchaseCoins;
    private String purchaseCurrency;
    private String purchaseDate;
    private String purchaseCategory;

    public static RespPurchaseDto fromPurchaseDto(PurchaseDto purchaseDto) {
        return RespPurchaseDto.builder()
                .purchaseId(purchaseDto.getPurchaseId())
                .purchaseName(purchaseDto.getName())
                .purchaseCoins(purchaseDto.getCoins())
                .purchaseCurrency(purchaseDto.getCurrency().name())
                .purchaseDate(purchaseDto.getDate().format(Purchase.DATE_FORMATTER))
                .purchaseCategory(purchaseDto.getCategory())
                .build();
    }
}
