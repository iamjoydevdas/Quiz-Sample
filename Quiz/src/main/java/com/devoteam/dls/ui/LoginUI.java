package com.devoteam.dls.ui;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.AuthenticationException;
import org.vaadin.spring.security.shared.VaadinSharedSecurity;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@SpringUI(path = "/login")
@Theme("Demo")
public class LoginUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	VaadinSharedSecurity vaadinSecurity;
	
    @Autowired
    private CacheManager cacheManager;
    
	private TextField userName;

	private PasswordField passwordField;

	private CheckBox rememberMe;

	private Button login;

	private Label loginFailedLabel;
	private Label loggedOutLabel;

	@Override
	protected void init(VaadinRequest request) {

		getPage().setTitle("Learning service Devoteam Munich");
		createLoginButton(request);
	}

	private void createLoginButton(VaadinRequest request) {
		
		VerticalLayout mainLayout = new VerticalLayout();
		Button loginButton = new Button("Login");

		loginButton.addClickListener(clickEvent ->{
		    jumpToLoginView(request);
		});	
		mainLayout.addComponent(loginButton);
		mainLayout.setComponentAlignment(loginButton, Alignment.TOP_RIGHT);
		setContent(mainLayout);
		
	}

	private void jumpToLoginView(VaadinRequest request) {
		FormLayout loginForm = new FormLayout();
		loginForm.setSizeUndefined();

		userName = new TextField("Username");
		passwordField = new PasswordField("Password");
		rememberMe = new CheckBox("Remember me");
		login = new Button("Login");
		loginForm.addComponent(userName);
		loginForm.addComponent(passwordField);
		loginForm.addComponent(rememberMe);
		loginForm.addComponent(login);
		login.addStyleName(ValoTheme.BUTTON_PRIMARY);
		login.setDisableOnClick(true);
		login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		login.addClickListener(e -> login());

		VerticalLayout loginLayout = new VerticalLayout();
		loginLayout.setSpacing(true);
		loginLayout.setSizeUndefined();

		if (request.getParameter("logout") != null) {
			loggedOutLabel = new Label("You have been logged out!");
			loggedOutLabel.addStyleName(ValoTheme.LABEL_SUCCESS);
			loggedOutLabel.setSizeUndefined();
			loginLayout.addComponent(loggedOutLabel);
			loginLayout.setComponentAlignment(loggedOutLabel, Alignment.BOTTOM_CENTER);
		}

		loginLayout.addComponent(loginFailedLabel = new Label());
		loginLayout.setComponentAlignment(loginFailedLabel, Alignment.BOTTOM_CENTER);
		loginFailedLabel.setSizeUndefined();
		loginFailedLabel.addStyleName(ValoTheme.LABEL_FAILURE);
		loginFailedLabel.setVisible(false);

		loginLayout.addComponent(loginForm);
		loginLayout.setComponentAlignment(loginForm, Alignment.TOP_CENTER);

		VerticalLayout rootLayout = new VerticalLayout(loginLayout);
		rootLayout.setSizeFull();
		rootLayout.setComponentAlignment(loginLayout, Alignment.MIDDLE_CENTER);
		setContent(rootLayout);
		setSizeFull();
	}

	@Cacheable
	private void login() {
		try {
			vaadinSecurity.login(userName.getValue(), passwordField.getValue(), rememberMe.getValue());
			/*Cache cache = cacheManager.getCache("quizzers");
			cache.put(new Element(userName.getValue(),""));
			Element e = cache.get(userName.getValue());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e.getObjectValue().toString());*/
		} catch (AuthenticationException ex) {
			userName.focus();
			userName.selectAll();
			passwordField.setValue("");
			loginFailedLabel.setValue(String.format("Login failed: %s", ex.getMessage()));
			loginFailedLabel.setVisible(true);
			if (loggedOutLabel != null) {
				loggedOutLabel.setVisible(false);
			}
		} catch (Exception ex) {
			Notification.show("An unexpected error occurred", ex.getMessage(), Notification.Type.ERROR_MESSAGE);
			LoggerFactory.getLogger(getClass()).error("Unexpected error while logging in", ex);
		} finally {
			login.setEnabled(true);
		}
	}
}
