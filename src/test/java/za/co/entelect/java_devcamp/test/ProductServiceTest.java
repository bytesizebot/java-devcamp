package za.co.entelect.java_devcamp.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.entelect.java_devcamp.dto.ProductDto;
import za.co.entelect.java_devcamp.entity.Product;
import za.co.entelect.java_devcamp.repository.ProductRepository;
import za.co.entelect.java_devcamp.service.ProductService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

//    @Test
//    public void getAllProducts_shouldReturnAllProducts(){
//        Product p1 = Product.builder()
//                .ProductId(1L)
//                .Name("Product")
//                .Description("Description of the second product")
//                .Price(1000.00F)
//                .ImageUrl("http://image.com")
//                .build();
//        Product p2 = Product.builder()
//                .ProductId(2L)
//                .Name("Product")
//                .Description("Description of the product")
//                .Price(1000.00F)
//                .ImageUrl("http://image.com")
//                .build();
//        when(productRepository.findAll()).thenReturn(List.of(p1,p2));
//
//        List<ProductDto> products = productService.getProducts();
//
//        assertEquals(2, products.size());
//        verify(productRepository, times(1)).findAll();
//    }

//    @Test
//    public void getProductByid_shouldReturnProductIfExists(){
//        Product p2 = Product.builder()
//                .ProductId(1L)
//                .Name("Product")
//                .Description("Description of the product")
//                .Price(1000.00F)
//                .ImageUrl("http://image.com")
//                .build();
//
//        when(productRepository.findById(1L))
//                .thenReturn(Optional.of(p2));
//
//        ProductDto foundProduct = productService.getProductById(1L);
//
//        assertNotNull(foundProduct);
//        assertEquals("Product", foundProduct.Name());
//        verify(productRepository, times(1)).findById(1L);
//
//    }

    @Test
    public void getProductById_shouldThrowExceptionIfNotFound() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productService.getProductById(1L));

        verify(productRepository).findById(1L);
    }
}
