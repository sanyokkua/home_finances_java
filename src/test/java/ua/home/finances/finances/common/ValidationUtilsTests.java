package ua.home.finances.finances.common;

import org.junit.jupiter.api.Test;
import ua.home.finances.finances.common.exceptions.*;
import ua.home.finances.finances.db.models.AppUser;
import ua.home.finances.finances.db.models.Purchase;
import ua.home.finances.finances.db.models.PurchaseList;
import ua.home.finances.finances.db.models.UserAuthInfo;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilsTests {
    @Test
    void testValidateUserAuthWithNull() {
        assertThrows(UserAuthInfoValidationException.class, () -> ValidationUtils.validateUserAuth(null));
    }

    @Test
    void testValidateUserAuthWithBlankPassword() {
        assertThrows(UserAuthInfoValidationException.class, () -> ValidationUtils.validateUserAuth(
                UserAuthInfo.builder().userId(null).email("user@email.com").password("   ").build()));
    }

    @Test
    void testValidateUserAuthWithIncorrectEmail() {
        assertThrows(ParamValidationException.class, () -> ValidationUtils.validateUserAuth(
                UserAuthInfo.builder().userId(null).email("user---email.com").password("password").build()));
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
    void testValidateAppUserWithNull() {
        assertThrows(AppUserValidationException.class, () -> ValidationUtils.validateAppUser(null));
    }

    @Test
    void testValidateAppUserWithIncorrectId() {
        assertThrows(AppUserValidationException.class, () -> ValidationUtils.validateAppUser(
                AppUser.builder().userId(null).nickname("nickname").build()));
    }

    @Test
    void testValidateAppUserWithBlankNickname() {
        assertThrows(AppUserValidationException.class,
                     () -> ValidationUtils.validateAppUser(AppUser.builder().userId(1L).nickname("     ").build()));
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
