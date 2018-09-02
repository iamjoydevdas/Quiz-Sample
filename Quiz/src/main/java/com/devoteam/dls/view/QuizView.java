package com.devoteam.dls.view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.security.SecurityContextUtils;
import com.devoteam.dls.service.CacheService;
import com.devoteam.dls.service.QuizzerService;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
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

	@Autowired
	private CacheService cacheService;
	
	public static final String VIEW_NAME = "quiz";
    private HorizontalLayout mainLayout;
    private Panel panel;
    private Panel dashBoardPanel;
    private Panel questionPanel;
    private VerticalLayout panelLayout;
    private VerticalLayout dashBoardPanelLayout;
    private VerticalLayout questionLayout;
    
    @Autowired
    private QuizzerService quizzerService;

    @PostConstruct
    public void init() {
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
    	dashBoardPanelLayout.setMargin(true);
    	dashBoardPanelLayout.setWidth("-1px");
    	dashBoardPanelLayout.setHeight("-1px");
    	
    	Button userNameButton = new Button();
    	userNameButton.setCaption("Joydev");
    	userNameButton.setWidth("890px");
    	dashBoardPanelLayout.addComponent(userNameButton);
    	
    	VerticalLayout userDetailsVerticalLayout = new VerticalLayout();
    	userDetailsVerticalLayout.setStyleName("wrapping");
    	userDetailsVerticalLayout.setSpacing(true);
    	userDetailsVerticalLayout.setMargin(true);
    	userDetailsVerticalLayout.setWidth("-1px");
    	userDetailsVerticalLayout.setHeight("-1px");
    	
    	Label totalPlayed = new Label("Total Played : 10");
    	Label totalWin = new Label("Total Win : 8");
    	Label totalLose = new Label("Total Lose : 2");
    	
    	userDetailsVerticalLayout.addComponent(totalPlayed);
    	userDetailsVerticalLayout.addComponent(totalWin);
    	userDetailsVerticalLayout.addComponent(totalLose);
    	
    	Panel userDetailsPanel = new Panel(userDetailsVerticalLayout);
    	userDetailsPanel.setStyleName("light");
    	userDetailsPanel.setWidth("890px");
    	userDetailsPanel.setHeight("200px");
    	
    	dashBoardPanelLayout.addComponent(userDetailsPanel);
    	
    	
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
    	
    	questionLayout = new VerticalLayout();
    	questionLayout.setStyleName("wrapping");
    	questionLayout.setSpacing(true);
    	questionLayout.setMargin(true);
    	questionLayout.setWidth("-1px");
    	questionLayout.setHeight("-1px");
    	
    	HorizontalLayout answerLayoutOne = new HorizontalLayout();
    	HorizontalLayout answerLayoutTwo = new HorizontalLayout();
    	
    	
    	Button questionButton = new Button();
    	questionButton.setWidth("890px");
    	questionButton.setHeight("140px");
    	questionButton.setCaption("Which one is primitive datatype?");
    	
    	Button answerOneButton = new Button();
    	answerOneButton.setWidth("440px");
    	answerOneButton.setCaption("Object");
    	
    	Button answerTwoButton = new Button();
    	answerTwoButton.setWidth("440px");
    	answerTwoButton.setCaption("String");
    	
    	Button answerThreeButton = new Button();
    	answerThreeButton.setWidth("440px");
    	answerThreeButton.setCaption("Integer");
    	
    	Button answerFourButton = new Button();
    	answerFourButton.setWidth("440px");
    	answerFourButton.setCaption("int");
    	
    	answerOneButton.addClickListener(event->{
    		questionButton.setCaption("Which one is nonprimitive datatype?");
    		answerOneButton.setCaption("int");
    		answerTwoButton.setCaption("String");
    		answerThreeButton.setCaption("boolean");
    		answerFourButton.setCaption("long");
    	});
    	
    	answerTwoButton.addClickListener(event->{
    		questionButton.setCaption("Which one is nonprimitive datatype?");
    		answerOneButton.setCaption("int");
    		answerTwoButton.setCaption("String");
    		answerThreeButton.setCaption("boolean");
    		answerFourButton.setCaption("long");
    	});
    	
    	answerThreeButton.addClickListener(event->{
    		questionButton.setCaption("Which one is nonprimitive datatype?");
    		answerOneButton.setCaption("int");
    		answerTwoButton.setCaption("String");
    		answerThreeButton.setCaption("boolean");
    		answerFourButton.setCaption("long");
    	});
    	
    	answerFourButton.addClickListener(event->{
    		questionButton.setCaption("Which one is nonprimitive datatype?");
    		answerOneButton.setCaption("int");
    		answerTwoButton.setCaption("String");
    		answerThreeButton.setCaption("boolean");
    		answerFourButton.setCaption("long");
    	});
    	
    	answerLayoutOne.addComponent(answerOneButton);
    	answerLayoutOne.addComponent(answerTwoButton);
    	answerLayoutTwo.addComponent(answerThreeButton);
    	answerLayoutTwo.addComponent(answerFourButton);
    	
    	questionLayout.addComponent(questionButton);
    	questionLayout.addComponent(answerLayoutOne);
    	questionLayout.addComponent(answerLayoutTwo);
    	
    	questionPanel = new Panel(questionLayout);
    	questionPanel.setStyleName("light");
    	questionPanel.setCaption("Quiz");
    	questionPanel.setWidth("920px");
    	questionPanel.setHeight("405px");
    	
    	List<Quizzer> quizzers = quizzerService.fetchAllQuizzerExceptSelf(SecurityContextUtils.getUser().getUsername());
    	
    	for(Quizzer quizzer : quizzers){
    		Button userButton;
    		if(isQuizzerActive(quizzer.getQuizzer_ID())) {
    			userButton = new Button();
    			userButton.setCaptionAsHtml(true);
    			userButton.setCaption( quizzer.getEmployee().getUsername() + "<span style=\'color: " + "lime" + " !important; left:3px; top:2px;position:relative;\'>" + VaadinIcons.DOT_CIRCLE.getHtml()  + "</span>" );
    			userButton.addStyleName(ValoTheme.BUTTON_TINY);
    			
    		} else {
    			userButton = new Button(quizzer.getEmployee().getUsername());
    			//userButton.setCaption(quizzer.getEmployee().getUsername());
    		}
    		
    		//userButton.setStyleName("primary");
    		userButton.setWidth("250px");
    		userButton.setHeight("-1px");
    		userButton.setData(quizzer);
    		
    		userButton.addClickListener(event-> {
    			if(isQuizzerActive(quizzer.getQuizzer_ID())) {
    				createWindow();
    			}
    			
    		});
    		panelLayout.addComponent(userButton);
    	}
    	
    	
    	//panel.
    	mainLayout.addComponent(panel);
    	mainLayout.addComponent(dashBoardPanel);
    	addComponent(mainLayout);
    	
    	
    }
    
    boolean isQuizzerActive(Long quizzer_ID) {
    	List<Quizzer> activeQuizzers = cacheService.getQuizzers();
		for(Quizzer q : activeQuizzers) {
			if(q.getQuizzer_ID() == quizzer_ID) {
				return true;
			}
		}
		//return true;
		return false;
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
		
		ComboBox<String> questionTypeComboBox = new ComboBox<>("Select a quistion type");
		questionTypeComboBox.setEmptySelectionAllowed(false);
		questionTypeComboBox.setWidth("220px");
		questionTypeComboBox.setHeight("-1px");
		questionTypeComboBox.setItems("...","Java", "Vaadin");
		questionTypeComboBox.setSelectedItem("...");
		questionTypeComboBox.addStyleName(ValoTheme.COMBOBOX_SMALL);
		
		windowLayout.addComponent(questionLabel);
		windowLayout.addComponent(questionTypeComboBox);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth("-1px");
		buttonLayout.setHeight("-1px");
		
		Button yesButton = new Button();
		yesButton.setCaption("Send");
		yesButton.setStyleName("primary");
		
		Button noButton = new Button();
		noButton.setCaption("Cancel");
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
			if("...".equals(questionTypeComboBox.getValue().toString())){
				Notification.show("Please select a valid data", Type.WARNING_MESSAGE);
				return;
			}
			mainLayout.removeComponent(dashBoardPanel);
			mainLayout.addComponent(questionPanel);
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