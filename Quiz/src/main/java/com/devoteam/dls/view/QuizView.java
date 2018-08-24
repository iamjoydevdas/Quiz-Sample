package com.devoteam.dls.view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.security.SecurityContextUtils;
import com.devoteam.dls.service.QuizzerService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@SpringView(name = QuizView.VIEW_NAME)
public class QuizView extends VerticalLayout implements View {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String VIEW_NAME = "quiz";
    private HorizontalLayout mainLayout;
    private Panel panel;
    private Panel dashBoardPanel;
    private VerticalLayout panelLayout;
    private VerticalLayout dashBoardPanelLayout;
   // private Label userOneButton;
  /*  private Label userTwoButton;
    private Label userThreeButton;*/
    
    @Autowired
    private QuizzerService quizzerService;

    @PostConstruct
    public void init() {
    	/*Icon logo = new Icon(VaadinIcon.VAADIN_H);
    	logo.setSize("100px");
    	logo.setColor("orange");*/
    	
    	mainLayout = new HorizontalLayout();
    	mainLayout.setStyleName("wrapping");
    	mainLayout.setSpacing(false);
    	mainLayout.setMargin(false);
    	mainLayout.setWidth("-1px");
    	mainLayout.setHeight("-1px");
    	
    	panelLayout = new VerticalLayout();
    	panelLayout.setStyleName("wrapping");
    	panelLayout.setSpacing(false);
    	panelLayout.setMargin(false);
    	panelLayout.setWidth("-1px");
    	panelLayout.setHeight("-1px");
    	
    	dashBoardPanelLayout = new VerticalLayout();
    	dashBoardPanelLayout.setStyleName("wrapping");
    	dashBoardPanelLayout.setSpacing(false);
    	dashBoardPanelLayout.setMargin(false);
    	dashBoardPanelLayout.setWidth("-1px");
    	dashBoardPanelLayout.setHeight("-1px");
    	
    	panel = new Panel(panelLayout);
    	panel.setStyleName("light");
    	panel.setCaption("All User");
    	panel.setWidth("-1px");
    	panel.setHeight("405px");
    	
    	dashBoardPanel = new Panel(dashBoardPanelLayout);
    	dashBoardPanel.setStyleName("light");
    	dashBoardPanel.setCaption("Dashboard");
    	dashBoardPanel.setWidth("920px");
    	dashBoardPanel.setHeight("405px");
    	
    	/*userOneButton = new Label();
    	userOneButton.setCaption("Joydev");
    	userOneButton.setStyleName("primary");
    	userOneButton.setWidth("250px");
    	userOneButton.setHeight("-1px");
    	
    	userTwoButton = new Label();
    	userTwoButton.setCaption("Utpal");
    	userTwoButton.setStyleName("primary");
    	userTwoButton.setWidth("250px");
    	userTwoButton.setHeight("-1px");
    	
    	userThreeButton = new Label();
    	userThreeButton.setCaption("Sampad");
    	userThreeButton.setStyleName("primary");
    	userThreeButton.setWidth("250px");
    	userThreeButton.setHeight("-1px");*/
    	
    	List<Quizzer> quizzers = quizzerService.fetchAllQuizzerExceptSelf(SecurityContextUtils.getUser().getUsername());
    	
    	for(Quizzer quizzer : quizzers){
    		Button userButton;
    		if(quizzer.getQuizzer_status().getStatus() == 1) {
    			//userButton.setCaption(VaadinIcons.CIRCLE);
    			//userButton.setIcon(VaadinIcons.CIRCLE);
    			userButton = new Button();
    			userButton.setCaptionAsHtml(true);
    			userButton.setCaption( quizzer.getEmployee().getUsername() + "<span style=\'color: " + "lime" + " !important; left:3px; top:2px;position:relative;\'>" + VaadinIcons.DOT_CIRCLE.getHtml()  + "</span>" );
    			userButton.addStyleName(ValoTheme.BUTTON_TINY);
    			//userButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
    			//userButton.setIcon(VaadinIcons.CIRCLE, quizzer.getEmployee().getUsername());
    			
    		} else {
    			userButton = new Button(quizzer.getEmployee().getUsername());
    			//userButton.setCaption(quizzer.getEmployee().getUsername());
    		}
    		
    		//userButton.setStyleName("primary");
    		userButton.setWidth("250px");
    		userButton.setHeight("-1px");
    		userButton.setData(quizzer);
    		
    		userButton.addClickListener(event-> {
    			createWindow();
    		});
    		panelLayout.addComponent(userButton);
    	}
    	
    	
    	/*panelLayout.addComponent(userOneButton);
    	panelLayout.addComponent(userTwoButton);
    	panelLayout.addComponent(userThreeButton);*/
    	
    	//panel.
    	mainLayout.addComponent(panel);
    	mainLayout.addComponent(dashBoardPanel);
    	addComponent(mainLayout);
    	
    	
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent view) {
    	/*userOneButton.addClickListener(event-> {
    		panelLayout.addComponent(new Label("New"));
    	});*/
    }
    
	private void createWindow() {
		VerticalLayout windowLayout = new VerticalLayout();
		windowLayout.setMargin(true);
		windowLayout.setSpacing(true);
		windowLayout.setWidth("-1px");
		windowLayout.setHeight("-1px");
		
		Label questionLabel = new Label();
		questionLabel.setValue("Do you want to send request?");
		
		windowLayout.addComponent(questionLabel);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth("-1px");
		buttonLayout.setHeight("-1px");
		
		Button yesButton = new Button();
		yesButton.setCaption("Yes");
		yesButton.setStyleName("primary");
		
		Button noButton = new Button();
		noButton.setCaption("No");
		noButton.setStyleName("primary");
		
		buttonLayout.addComponent(yesButton);
		buttonLayout.addComponent(noButton);
		windowLayout.addComponent(buttonLayout);
		windowLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		
		
		
		Window quistenWindow = new Window("Confirm");
		quistenWindow.setModal(true);
		quistenWindow.setClosable(true);
		quistenWindow.setWidth("244px");
		quistenWindow.setHeight("-1px");
		quistenWindow.center();
		quistenWindow.setContent(windowLayout);
		UI.getCurrent().addWindow(quistenWindow);
		
		yesButton.addClickListener(event-> {
			quistenWindow.close();
		});
		
		noButton.addClickListener(event-> {
			quistenWindow.close();
		});
	}

}

 

/*package com.devoteam.dls.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.util.Iterator;

import javax.annotation.PostConstruct;

*//**
 * Created by basakpie on 2017. 5. 11..
 *//*
@SpringView(name = QuizView.VIEW_NAME)
public class QuizView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "quiz";

    @PostConstruct
    public void init() {
    	// addComponent(new Label("Hello, this is quiz view."));
    	// AbstractOrderedLayout layout = new UserViewDesign();
    	//layout.setComponentAlignment(layout, Alignment.TOP_LEFT);
    	UserViewDesign uv = new UserViewDesign();
    	uv.addComponent(new Label("HIIIIII"));
    	Iterator<Component> i = uv.iterator();
    	while(i.hasNext()){
    		Component c = i.next();
    		System.out.println(c.getId());
    	}
    	System.out.println(uv.getComponent(0).getId());
    	addComponent(uv);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
*/