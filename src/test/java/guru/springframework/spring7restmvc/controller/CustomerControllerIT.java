package guru.springframework.spring7restmvc.controller;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    @Transactional
    @Rollback
    void testDeleteByIdFound() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customer.getId())).isEmpty();
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteByIDNotFound() {
        Customer customer = customerRepository.findAll().get(0);
        customerRepository.deleteById(customer.getId());
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(customer.getId()));

    }

    @Transactional
    @Rollback
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("customer666")
                .build();
        ResponseEntity responseEntity = customerController.handlePost(customerDTO);

        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuid = UUID.fromString(location[4]);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().get("Location")).isNotNull();

        Customer customer = customerRepository.findById(uuid).get();

        assertThat(customer).isNotNull();
    }

    @Transactional
    @Rollback
    @Test
    void updateById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setName("Updated Name");
        ResponseEntity responseEntity = customerController.updateCustomerByID(customer.getId(), customerDTO);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(customerDTO.getId()).get()).isNotNull();
        assertThat(customerRepository.findById(customerDTO.getId()).get().getName()).isEqualTo(customerDTO.getName());
    }

    @Transactional
    @Rollback
    @Test
    void updateByIdNotFound() {
        Customer customer = customerRepository.findAll().get(0);
        customerRepository.deleteById(customer.getId());
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setName("Updated Name");
        assertThrows(NotFoundException.class, () -> customerController.updateCustomerByID(customer.getId(), customerDTO));
    }

    @Transactional
    @Rollback
    @Test
    void patch() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.patchCustomerById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));

        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);
    }

    @Transactional
    @Rollback
    @Test
    void patchNotFound() {
        Customer customer = customerRepository.findAll().get(0);
        customerRepository.deleteById(customer.getId());
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        final String customerName = "UPDATED";
        customerDTO.setName(customerName);

        assertThrows(NotFoundException.class, () -> customerController.patchCustomerById(customer.getId(), customerDTO));
    }

    @Rollback
    @Transactional
    @Test
    void testListAllEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(0);
    }

    @Test
    void testListAll() {
        List<CustomerDTO> dtos = customerController.listAllCustomers();

        assertThat(dtos.size()).isEqualTo(3);
    }

    @Test
    void testGetByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
    }

    @Test
    void testGetById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}










