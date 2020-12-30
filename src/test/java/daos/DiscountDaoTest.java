package daos;

import model.Discount;
import model.Hall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountDaoTest {
    private String discountName = "studencka";
    private float discountValue = 20f;
    private DiscountDao dao = new DiscountDao();

    @Test
    public void testAddingAndGettingToDatabase() {
        // given
        Discount discount = new Discount(discountName, discountValue);
        // when
        dao.addDiscount(discount);
        Discount gotDiscount = dao.getDiscount(discountName);
        // then
        assertEquals(discount, gotDiscount);
        assertTrue(dao.doesDiscountExist(discountName));
    }

    @Test
    public void testRemovingFromDatabase() {
        // given
        Discount discountToRemove = new Discount("studencka2", 20.0f);
        dao.addDiscount(discountToRemove);
        // when
        dao.removeDiscount(discountToRemove);
        // then
        assertFalse(dao.doesDiscountExist(discountToRemove.getName()));
    }
}