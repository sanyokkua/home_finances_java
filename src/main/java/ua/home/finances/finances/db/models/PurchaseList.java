package ua.home.finances.finances.db.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseList {
    private Long listId;
    private Long listUserId;
    private String name;
}
