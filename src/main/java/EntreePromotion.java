import java.util.List;

/**
 * Promotion strategy for entree items with multiple modifiers.
 * Applies a 15% discount to entrees that have 2 or more modifiers.
 */
public class EntreePromotion implements Promotion {
    
    private static final double DISCOUNT_RATE = 0.15;
    private static final int MIN_MODIFIERS_REQUIRED = 2;
    private static final String PROMOTION_CODE = "PREMIUM_ENTREE_15";
    
    @Override
    public boolean isApplicable(MenuItem item, List<String> modifiers) {
        return item != null 
            && "ENTREE".equals(item.getCategory())
            && modifiers != null 
            && modifiers.size() >= MIN_MODIFIERS_REQUIRED;
    }
    
    @Override
    public double calculateDiscount(MenuItem item, List<String> modifiers) {
        if (isApplicable(item, modifiers)) {
            return DISCOUNT_RATE;
        }
        return 0.0;
    }
    
    @Override
    public String getPromotionCode() {
        return PROMOTION_CODE;
    }
}

