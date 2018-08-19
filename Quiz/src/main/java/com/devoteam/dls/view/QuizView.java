package com.devoteam.dls.view;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.security.SecurityContextUtils;
import com.devoteam.dls.service.QuizzerService;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

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
    		Label label = new Label();
    		label.setCaption(quizzer.getEmployee().getUsername());
    		label.setStyleName("primary");
    		label.setWidth("250px");
    		label.setHeight("-1px");
    		panelLayout.addComponent(label);
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