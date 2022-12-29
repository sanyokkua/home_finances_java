package ua.home.finances.logic.common;

import org.junit.jupiter.api.Test;
import ua.home.finances.logic.common.exceptions.ApplicationUserValidationException;
import ua.home.finances.logic.common.exceptions.ParamValidationException;
import ua.home.finances.logic.common.exceptions.PurchaseListValidationException;
import ua.home.finances.logic.common.exceptions.PurchaseValidationException;
import ua.home.finances.logic.db.models.ApplicationUser;
import ua.home.finances.logic.db.models.Purchase;
import ua.home.finances.logic.db.models.PurchaseList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilsTests {
    @Test
    void testValidateApplicationUserWithNull() {
        assertThrows(ApplicationUserValidationException.class, () -> ValidationUtils.validateApplicationUser(null));
    }

    @Test
    void testValidateApplicationUserWithBlankPassword() {
        assertThrows(ApplicationUserValidationException.class, () -> ValidationUtils.validateApplicationUser(
                ApplicationUser.builder().userId(null).email("user@email.com").password("   ").build()));
    }

    @Test
    void testValidateApplicationUserWithIncorrectEmail() {
        assertThrows(ParamValidationException.class, () -> ValidationUtils.validateApplicationUser(
                ApplicationUser.builder().userId(null).email("user---email.com").password("password").build()));
    }

    @Test
    void testValidateEmailWithBlankEmail() {
        assertThrows(ParamValidationException.class, () -> ValidationUtils.validateEmail("   "));
    }

    @Test
    void testValidateEmailWithIncorrectFormat() {
        assertThrows(ParamValidationException.class, () -> ValidationUtils.validateEmail("non-valid#email.com"));
    }

    @Test
    void testValidateApplicationUserWithIncorrectId() {
        assertThrows(ApplicationUserValidationException.class, () -> ValidationUtils.validateApplicationUser(
                ApplicationUser.builder().userId(null).nickname("nickname").build()));
    }

    @Test
    void testValidateApplicationUserWithBlankNickname() {
        assertThrows(ApplicationUserValidationException.class, () -> ValidationUtils.validateApplicationUser(
                ApplicationUser.builder().userId(1L).nickname("     ").build()));
    }

    @Test
    void testValidatePurchaseWithNull() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(null));
    }

    @Test
    void testValidatePurchaseWithIncorrectListId() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(Purchase.builder()
                                                                                                       .purchaseId(null)
                                                                                                       .purchaseListId(
                                                                                                               -100L)
                                                                                                       .name("purchase-name")
                                                                                                       .date(LocalDate.now())
                                                                                                       .currency(
                                                                                                               Currency.USD)
                                                                                                       .coins(10000L)
                                                                                                       .category(
                                                                                                               "default")
                                                                                                       .build()));
    }

    @Test
    void testValidatePurchaseWithBlankName() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(Purchase.builder()
                                                                                                       .purchaseId(null)
                                                                                                       .purchaseListId(
                                                                                                               100L)
                                                                                                       .name("    ")
                                                                                                       .date(LocalDate.now())
                                                                                                       .currency(
                                                                                                               Currency.USD)
                                                                                                       .coins(10000L)
                                                                                                       .category(
                                                                                                               "default")
                                                                                                       .build()));
    }

    @Test
    void testValidatePurchaseWithNullCurrency() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(Purchase.builder()
                                                                                                       .purchaseId(null)
                                                                                                       .purchaseListId(
                                                                                                               100L)
                                                                                                       .name("purchase-name")
                                                                                                       .date(LocalDate.now())
                                                                                                       .currency(null)
                                                                                                       .coins(10000L)
                                                                                                       .category(
                                                                                                               "default")
                                                                                                       .build()));
    }

    @Test
    void testValidatePurchaseWithNullDate() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(Purchase.builder()
                                                                                                       .purchaseId(null)
                                                                                                       .purchaseListId(
                                                                                                               100L)
                                                                                                       .name("purchase-name")
                                                                                                       .date(null)
                                                                                                       .currency(
                                                                                                               Currency.USD)
                                                                                                       .coins(10000L)
                                                                                                       .category(
                                                                                                               "default")
                                                                                                       .build()));
    }

    @Test
    void testValidatePurchaseWithEmptyCategory() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(Purchase.builder()
                                                                                                       .purchaseId(null)
                                                                                                       .purchaseListId(
                                                                                                               100L)
                                                                                                       .name("purchase-name")
                                                                                                       .date(LocalDate.now())
                                                                                                       .currency(
                                                                                                               Currency.USD)
                                                                                                       .coins(10000L)
                                                                                                       .category("   ")
                                                                                                       .build()));
    }

    @Test
    void testValidatePurchaseWithIncorrectCoins() {
        assertThrows(PurchaseValidationException.class, () -> ValidationUtils.validatePurchase(Purchase.builder()
                                                                                                       .purchaseId(null)
                                                                                                       .purchaseListId(
                                                                                                               100L)
                                                                                                       .name("purchase-name")
                                                                                                       .date(LocalDate.now())
                                                                                                       .currency(
                                                                                                               Currency.USD)
                                                                                                       .coins(-10000L)
                                                                                                       .build()));
    }

    @Test
    void testValidatePurchaseListWithNull() {
        assertThrows(PurchaseListValidationException.class, () -> ValidationUtils.validatePurchaseList(null));
    }

    @Test
    void testValidatePurchaseListWithIncorrectUserId() {
        assertThrows(PurchaseListValidationException.class, () -> ValidationUtils.validatePurchaseList(
                PurchaseList.builder().listId(null).listUserId(-1L).name("list-name").build()));
    }

    @Test
    void testValidatePurchaseListWithBlankName() {
        assertThrows(PurchaseListValidationException.class, () -> ValidationUtils.validatePurchaseList(
                PurchaseList.builder().listId(null).listUserId(1L).name("   ").build()));
    }

    @Test
    void testValidateParamCommon() {
        assertThrows(ParamValidationException.class,
                     () -> ValidationUtils.validateParamCommon(() -> true, "Error happened"));
    }
}
