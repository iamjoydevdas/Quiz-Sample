package com.devoteam.dls.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devoteam.dls.dao.EmployeeRepository;
import com.devoteam.dls.domain.Employee;
import com.devoteam.dls.domain.OnlineQuizzers;
import com.devoteam.dls.domain.OnlineStatus;
import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.push.Broadcaster;
import com.devoteam.dls.push.Broadcaster.BroadcastListener;
import com.devoteam.dls.service.CacheService;
import com.devoteam.dls.service.QuizzerService;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@Service
public class SecurityUserDetailsService implements UserDetailsService, BroadcastListener {

    @Autowired
    private EmployeeRepository userRepository;
    
    @Autowired
    private QuizzerService quizzerService;
    
    @Autowired
    private CacheService cacheService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Broadcaster.register(UI.getCurrent(), this);
        final Employee user = userRepository.findByUsername(username);
        if(user==null) {
            throw new UsernameNotFoundException("Username Not Found Exception : " + username);
        }
        Quizzer quizzer = quizzerService.fetchQuizzer(username);
       
        if(quizzer != null) {
        	OnlineQuizzers onlineQuizer = new OnlineQuizzers();
        	onlineQuizer.setQuizzer_ID(quizzer.getQuizzer_ID());
        	onlineQuizer.setEmployee(quizzer.getEmployee());
        	//onlineQuizer.setOnlineStatus(OnlineStatus.AVAILIABLE);
        	cacheService.setQuizzer(onlineQuizer);
        }
        Broadcaster.broadcast("Update user");
        System.out.println(cacheService.getQuizzers());
        return new SecurityUserDetails(user);
    }
/*
	@Override
	public void receiveBroadcast(UI ui, String message) {
		System.out.println("Users loading");
		ui.access(new Runnable() {
			
	        @Override
	        public void run() {
	        	System.out.println("Message"+message+"::"+SecurityContextUtils.getUser().getUsername()+"'");
	            //  addValue(message);
	        	//Notification.show(message+"  '"+SecurityContextUtils.getUser().getUsername()+"'", Type.WARNING_MESSAGE);
	         //   ui.push();
	            Window quistenWindow = new Window("Update " + message + SecurityContextUtils.getUser().getUsername());
	    		quistenWindow.setModal(true);
	    		quistenWindow.setClosable(true);
	    		quistenWindow.setWidth("244px");
	    		quistenWindow.setHeight("-1px");
	    		quistenWindow.center();
	    		UI.getCurrent().addWindow(quistenWindow);
	        }
	    });
		
		
	}*/

	@Override
	public void receiveBroadcast(UI ui, String message) {
		// TODO Auto-generated method stub
		
	}
}
