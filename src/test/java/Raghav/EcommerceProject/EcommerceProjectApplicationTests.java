package Raghav.EcommerceProject;

import Raghav.EcommerceProject.entities.Customer;
import Raghav.EcommerceProject.entities.Role;
import Raghav.EcommerceProject.entities.User;
import Raghav.EcommerceProject.repositories.CustomerRepository;
import Raghav.EcommerceProject.repositories.RoleRepository;
import Raghav.EcommerceProject.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EcommerceProjectApplicationTests {


	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void contextLoads() {

		Role role = new Role();
		role.setId(1);
		role.setAuthority("Admin");

		roleRepository.save(role);

		User user = new User();
		user.setId(1);
		user.setEmail("abc");
		user.setFirst_name("raghav");
		user.setMiddle_name("d");
		user.setLast_name("gupta");
		user.setPassword("password");
		user.setActive(true);
		user.setDeleted(false);
		user.setLocked(false);
		user.setExpired(false);
		user.setInvalid_attempt_count(1);
		userRepository.save(user);

		Customer customer = new Customer();
		customer.setId(1);
		customer.setUser(user);
		customer.setContact("987654");
		customer.setEmail("abv@gmau");
		customer.setPhoneNumber("9877");
		customer.setPassword("abcd");
		customer.setConfirmPassword("abcd");
		customer.setFirstName("aser");
		customer.setLastName("tesr");

		customerRepository.save(customer);

	}

}
