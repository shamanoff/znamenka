package ru.click.crm.represent.converter.domain;


import org.springframework.stereotype.Component;
import ru.click.core.represent.converter.ApiConverter;
import ru.click.crm.represent.domain.ProductApi;
import ru.click.core.model.Product;

/**
 * <p>
 * <p>
 * Создан 22.08.2016
 * <p>
 *
 * @author Евгений Уткин (Eugene Utkin)
 */
@Component
public class ProductApiConverter implements ApiConverter<Product, ProductApi> {

    @Override
    public Class<ProductApi> getApiType() {
        return ProductApi.class;
    }

    @Override
    public Class<Product> getEntityType() {
        return Product.class;
    }

    // TODO: 22.08.2016
    @Override
    public Product convertTo(ProductApi source) {
       throw new UnsupportedOperationException();
    }

    @Override
    public ProductApi convert(Product source) {
        ProductApi p = new ProductApi();
        p.setName(source.getProductName());
        p.setId(source.getId());
        p.setPrice(source.getPrice());

        return p;
    }
}
