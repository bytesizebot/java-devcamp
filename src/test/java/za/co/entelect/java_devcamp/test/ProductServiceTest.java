package za.co.entelect.java_devcamp.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.entelect.java_devcamp.dto.ProductDto;
import za.co.entelect.java_devcamp.entity.Product;
import za.co.entelect.java_devcamp.mapper.ProductMapper;
import za.co.entelect.java_devcamp.repository.EligibilityRepository;
import za.co.entelect.java_devcamp.repository.ProductRepository;
import za.co.entelect.java_devcamp.repository.QualifyingAccountsRepository;
import za.co.entelect.java_devcamp.repository.QualifyingCustomerTypesRepository;
import za.co.entelect.java_devcamp.service.ProductService;
import za.co.entelect.java_devcamp.webclient.CISWebService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private QualifyingCustomerTypesRepository qualifyingCustomerTypesRepository;
    @Mock
    private QualifyingAccountsRepository qualifyingAccountsRepository;
    @Mock
    private EligibilityRepository eligibilityRepository;
    @Mock
    private ProductMapper productMapper;
    @Mock
    private CISWebService cisWebService;

    private Product mockProductEntity;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        productService = new ProductService(productRepository, qualifyingAccountsRepository, qualifyingCustomerTypesRepository, productMapper, cisWebService, eligibilityRepository);

        mockProductEntity = Product.builder()
                .productId(1L)
                .Name("Product")
                .Description("Description of the second product")
                .Price(1000.00F)
                .ImageUrl("http://image.com")
                .build();
    }

    @Test
    public void testGetAllProducts_WhenListOfProductsExists_thenReturnAllProducts() {
        Product p2 = Product.builder()
                .productId(2L)
                .Name("Product")
                .Description("Description of the product")
                .Price(1000.00F)
                .ImageUrl("http://image.com")
                .build();
        when(productRepository.findAll()).thenReturn(List.of(mockProductEntity, p2));

        List<ProductDto> products = productService.getProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById_WhenProductIdIsPresent_ThenReturnProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(mockProductEntity));

        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals("Product", foundProduct.getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetProductById_WhenProductIdIsNotPresent_thenThrowExceptionIfNotFound() {

        when(productRepository.findById(6L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productService.getProductById(6L));

        verify(productRepository).findById(6L);
    }

//    To do: check eligibility tests
//    @Test
//    void testCheckCustomerEligibility_WhenCustomerIsEligibleForProduct_ThenReturnTrueResult() {
//
//    }
//
//    @Test
//    void testCheckCustomerEligibility_WhenCustomerIsNotEligibleForProduct_ThenReturnFalseResult() {
//
//    }
}
