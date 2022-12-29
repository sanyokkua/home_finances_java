package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
