package guru.springframework.spring7restmvc.bootstrap;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import guru.springframework.spring7restmvc.repositories.BeerRepository;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class BootstrapController implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final BeerRepository beerRepository;

    @Override
    public void run(String... args) throws Exception {
        populateBeerDb();
        populateCustomerDb();
    }

    public void populateCustomerDb() {
        Customer customer1 = Customer.builder()
                .name("Customer1")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .name("Customer2")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .name("Customer3")
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);
    }

    public void populateBeerDb() {
        Beer beer1 = Beer.builder()
                .beerName("Beer1")
                .beerStyle(BeerStyle.LAGER)
                .upc("1244")
                .price(new BigDecimal(10))
                .quantityOnHand(500)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .beerName("Beer2")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("1436")
                .price(new BigDecimal(11))
                .quantityOnHand(500)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .beerName("Beer3")
                .beerStyle(BeerStyle.IPA)
                .upc("6756")
                .price(new BigDecimal(14))
                .quantityOnHand(500)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerRepository.save(beer1);
        beerRepository.save(beer2);
        beerRepository.save(beer3);
    }
}
