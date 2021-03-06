package com.devoteam.dls.ui;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.vaadin.spring.security.VaadinSecurity;

import com.devoteam.dls.domain.OnlineStatus;
import com.devoteam.dls.security.SecurityContextUtils;
import com.devoteam.dls.service.CacheService;
import com.devoteam.dls.view.AccessDeniedView;
import com.devoteam.dls.view.AdminView;
import com.devoteam.dls.view.ErrorView;
import com.devoteam.dls.view.QuizView;
import com.devoteam.dls.view.UserView;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@Push
@Theme("Demo")
@SpringUI(path = "/")
@SpringViewDisplay
public class MainUI extends UI implements ViewDisplay {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	VaadinSecurity vaadinSecurity;

	@Autowired
	SpringViewProvider springViewProvider;

	@Autowired
	SpringNavigator springNavigator;

	Panel springViewDisplay;
	
	private String loggedInUser;
	
	@Autowired
	CacheService cacheService;

	@PostConstruct
	public void init() {
		springNavigator.setErrorView(ErrorView.class);
		springViewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
		springViewDisplay = new Panel();
		springViewDisplay.setSizeFull();
	}

	@Override
	protected void init(VaadinRequest request){
		loggedInUser = SecurityContextUtils.getUser().getUsername();
		getPage().setTitle("Dashboard");

		//final CssLayout navigationBar = new CssLayout();
		final HorizontalLayout navigationBar = new HorizontalLayout();
		navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		navigationBar.addComponent(createNavigationButton("User View", UserView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Admin View", AdminView.VIEW_NAME));
		navigationBar.addComponent(createNavigationButton("Quizzer", QuizView.VIEW_NAME));
	//	navigationBar.addComponent(createNavigationButton("Test", QuizView2.VIEW_NAME));
		navigationBar.addComponent(new Button("Logout", e -> { 
			cacheService.removeQuizzer(loggedInUser);
			vaadinSecurity.logout();
		}));

		final VerticalLayout root = new VerticalLayout();
		root.setSizeFull();
		root.addComponents(new Label(SecurityContextUtils.getUser().getUsername() + " : " + LocalDateTime.now()));
		root.addComponent(navigationBar);
		root.addComponent(springViewDisplay);
		root.setExpandRatio(springViewDisplay, 1.0f);
		root.setComponentAlignment(navigationBar, Alignment.TOP_LEFT);

		setContent(root);
	}

	@Override
	public void showView(View view) {
		springViewDisplay.setContent((Component)view);
	}

	private Button createNavigationButton(String caption, final String viewName) {
		Button button = new Button(caption);
		button.addClickListener(event -> {
			if(viewName == "quiz") { 
				cacheService.updateOnlineStatus(loggedInUser, OnlineStatus.AVAILIABLE);
			}else {
				cacheService.updateOnlineStatus(loggedInUser, OnlineStatus.AWAY);
			}
			getUI().getNavigator().navigateTo(viewName);
		});
		return button;
	}

}
