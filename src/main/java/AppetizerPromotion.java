import java.util.List;

/**
 * Promotion strategy for appetizer items.
 * Applies a 20% discount to any appetizer item.
 */
public class AppetizerPromotion implements Promotion {
    
    private static final double DISCOUNT_RATE = 0.20;
    private static final String PROMOTION_CODE = "APPETIZER_SPECIAL_20";
    
    @Override
    public boolean isApplicable(MenuItem item, List<String> modifiers) {
        return item != null && "APPETIZER".equals(item.getCategory());
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

