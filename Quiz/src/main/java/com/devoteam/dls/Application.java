package com.devoteam.dls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.devoteam.dls.dao.EmployeeRepository;

/**
 * Created by basakpie on 2017-05-15.
 */
@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class)
public class Application implements CommandLineRunner {

    @Autowired
    EmployeeRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    
    private static final Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Override
    public void run(String... args) throws Exception {
//        Arrays.stream(RoleType.values()).forEach(x -> {
//            String name = x.name().split("_")[1].toLowerCase();
//
//            User user = new User();
//            user.setUsername(name);
//            user.setPassword(passwordEncoder.encode("1234"));
    		LOG.info("checking if pwd hash uses salt");
    		LOG.info("the following 2 hash should be different");
    		LOG.info(passwordEncoder.encode("123"));
    		LOG.info(passwordEncoder.encode("123"));
    		System.out.println(passwordEncoder.encode("123"));
//    	
//            Role role = new Role();
//            role.setType(x);
//            role.setUser(user);
//
//            user.setRoles(Arrays.asList(role));
//            userRepository.save(user);
//        });
    }
}
