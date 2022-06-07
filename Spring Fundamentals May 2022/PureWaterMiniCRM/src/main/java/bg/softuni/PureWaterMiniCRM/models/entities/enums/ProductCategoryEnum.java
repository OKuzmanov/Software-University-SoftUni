package bg.softuni.PureWaterMiniCRM.models.entities.enums;

import java.math.BigDecimal;

public enum ProductCategoryEnum {
     HALF_LITRE(BigDecimal.valueOf(0.85)),
     LITRE_AND_HALF(BigDecimal.valueOf(1.50)),
     TEN_LITRES(BigDecimal.valueOf(3.65)),
     NINETEEN_LITRES((BigDecimal.valueOf(4.99)));

     private final BigDecimal price;

     ProductCategoryEnum(BigDecimal price) {
          this.price = price;
     }

     public BigDecimal getPrice() {
          return price;
     }
}
