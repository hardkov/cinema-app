package daos;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import model.Discount;
import model.Hall;

import java.util.List;

import static daos.DaoConstants.discountPath;
import static daos.DaoConstants.hallPath;

public class DiscountDao {

    Firestore db;
    GenericDao<Discount> discountGenericDao;

    public DiscountDao() {
        this.db = FirestoreDatabase.getInstance().getDb();
        this.discountGenericDao = new GenericDao<>(Discount.class);
    }

    public boolean addDiscount(Discount discount) {
        return discountGenericDao.addObject(discountPath, discount.getName(), discount);
    }

    public boolean removeDiscount(Discount discount) {
        return discountGenericDao.removeObject(discountPath, discount.getName());
    }

    public boolean doesDiscountExist(String discountName) {
        return getDiscount(discountName) != null;
    }

    public Discount getDiscount(String discountName) {
        return discountGenericDao.getObject(discountPath, discountName);
    }

    public DocumentReference getDiscountReference(Discount discount) {
        return db.collection(discountPath).document(discount.getName());
    }

    public List<Discount> getAllDiscounts() {
        return discountGenericDao.getAllObjects(discountPath);
    }

    public boolean updateDiscount(Discount discount) {
        return discountGenericDao.updateObject(discountPath, discount.getName(), discount);
    }
}
