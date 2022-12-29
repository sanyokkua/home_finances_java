package ua.home.finances.web.app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespPurchaseListDto {
    private long purchaseListId;
    private String purchaseListName;
}
