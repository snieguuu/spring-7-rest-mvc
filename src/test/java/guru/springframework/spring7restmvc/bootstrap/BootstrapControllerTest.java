package guru.springframework.spring7restmvc.bootstrap;

import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class BootstrapControllerTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BootstrapController bootstrapController;

    @BeforeEach
    void setUp() {
        bootstrapController = new BootstrapController(customerRepository, beerRepository);
    }

    @Test
    void run() throws Exception {
        bootstrapController.run(null);

        Assertions.assertThat(beerRepository.count()).isEqualTo(3);
        Assertions.assertThat(customerRepository.count()).isEqualTo(3);
    }
}