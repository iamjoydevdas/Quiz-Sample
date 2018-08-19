package com.devoteam.dls.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devoteam.dls.dao.EmployeeRepository;
import com.devoteam.dls.domain.Employee;
import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.service.QuizzerService;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository userRepository;
    
    @Autowired
    private QuizzerService quizzerService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	List<Quizzer> quizzers = quizzerService.fetchAllQuizzer();
    	for(Quizzer quizzer : quizzers) {
    		System.out.println(quizzer);
    	}
        final Employee user = userRepository.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("Username Not Found Exception : " + username);
        }
        return new SecurityUserDetails(user);
    }
}
