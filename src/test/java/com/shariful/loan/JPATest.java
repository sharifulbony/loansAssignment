package com.shariful.loan;

//import com.sharifulbony.api.product.ProductEntity;
//import com.sharifulbony.api.product.ProductRepository;
import com.shariful.loan.dtos.Data;
import com.shariful.loan.dtos.Input;
import com.shariful.loan.services.LoanProcessService;
import org.junit.Test;
import org.junit.runner.RunWith;

//


import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)

public class JPATest {
//

    LoanProcessService loanProcessService=new LoanProcessService(customerService);

//
//    @Test
//    public void whenFindByName_thenReturnProduct() {
//        // given
//        ProductEntity sampleTestProduct = new ProductEntity("sampleTestProduct");
//        entityManager.persist(sampleTestProduct);
//        entityManager.flush();
//
////         when
//        ProductEntity found = productRepository.findByName(sampleTestProduct.getName());
//
//        // then
//        assertThat(found.getName())
//                .isEqualTo(sampleTestProduct.getName());
//    }
//
    @Test
    public void checkLoanInsert() {
        // given
        Input input=new Input("12-1234-123", (double) 200,"a");
        loanProcessService.insert(input);

        assert(Data.allData.get("12-1234-123").getId())
                .equals(input.getCustomerId());
    }
}
