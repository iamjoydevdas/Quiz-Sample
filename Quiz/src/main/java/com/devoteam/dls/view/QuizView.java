package com.devoteam.dls.view;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.devoteam.dls.dao.PlayingRepo;
import com.devoteam.dls.dao.Receiver;
import com.devoteam.dls.dao.Sender;
import com.devoteam.dls.domain.AnswerCheck;
import com.devoteam.dls.domain.OnlineQuizzers;
import com.devoteam.dls.domain.PlayingStats;
import com.devoteam.dls.domain.Questions;
import com.devoteam.dls.domain.QuizSet;
import com.devoteam.dls.domain.Quizzer;
import com.devoteam.dls.domain.Requests;
import com.devoteam.dls.domain.Summary;
import com.devoteam.dls.push.Broadcaster;
import com.devoteam.dls.push.Broadcaster.BroadcastListener;
import com.devoteam.dls.security.SecurityContextUtils;
import com.devoteam.dls.service.CacheService;
import com.devoteam.dls.service.QuizzerService;
import com.vaadin.annotations.Push;
import com.vaadin.data.converter.StringToBooleanConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.communication.PushMode;
import com.vaadin.shared.ui.grid.ColumnResizeMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Panel;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.renderers.HtmlRenderer;
import com.vaadin.ui.renderers.Renderer;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@Push(PushMode.MANUAL)
@SpringView(name = QuizView.VIEW_NAME)
public class QuizView extends VerticalLayout implements View, BroadcastListener, AnswerCheck  {

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
    private Panel resultPanel;
    private VerticalLayout panelLayout = new VerticalLayout();
    private VerticalLayout dashBoardPanelLayout;
    private VerticalLayout resultDashBoardLayout;
    private VerticalLayout questionLayout;
    
    private Sender loggedInUser = new Sender();
    private int questionIndex = 0;
    private Button questionButton;
    private Button answerOneButton;
    private Button answerTwoButton;
    private Button answerThreeButton;
    private Button answerFourButton;
    private Button nextQuestionButton;
    private List<Questions> questionList;
    private ProgressBar bar = new ProgressBar(0.0f);
    private Timer timer;
    private boolean isClickedAnswer = false;
    
    @Autowired
    private QuizzerService quizzerService;

    @Autowired
    private PlayingRepo playingRepo;
    
	static int i = 1;
    
    @PostConstruct
    public void init() {
    	//check the user he or she has to have permission to play the quiz
		if (true) {
			bar.setVisible(false);
	    	bar.setWidth("350px");
	    	bar.setHeight("15px");
	    	Broadcaster.register(UI.getCurrent(), this);
	    	loggedInUser.setSenderId(SecurityContextUtils.getUser().getUsername());
	    	loggedInUser.setSenderName(SecurityContextUtils.getUser().getUsername());
	    	loggedInUser.setTimestamp(new Timestamp(System.currentTimeMillis()));
	    	mainLayout = new HorizontalLayout();
	    	mainLayout.setStyleName("wrapping");
	    	mainLayout.setSpacing(false);
	    	mainLayout.setMargin(false);
	    	mainLayout.setWidth("-1px");
	    	mainLayout.setHeight("-1px");
	    	initializeQizzerMainDashboard();
	    	addComponent(mainLayout);
		} else {
			Label label = new Label("this is AccessDeniedView.");
			label.addStyleName(ValoTheme.LABEL_FAILURE);
			addComponent(label);
		}
    	
    }
    
    
    private void initializeQizzerMainDashboard() {
    	
    	panelLayout.setStyleName("wrapping");
    	panelLayout.setSpacing(false);
    	panelLayout.setMargin(false);
    	panelLayout.setWidth("251px");
    	panelLayout.setHeight("-1px");
    	
    	dashBoardPanelLayout = new VerticalLayout();
    	dashBoardPanelLayout.setStyleName("wrapping");
    	dashBoardPanelLayout.setSpacing(false);
    	dashBoardPanelLayout.setMargin(true);
    	dashBoardPanelLayout.setWidth("-1px");
    	dashBoardPanelLayout.setHeight("-1px");
    	
    	Button userNameButton = new Button();
    	userNameButton.setCaption(SecurityContextUtils.getUser().getUsername());
    	userNameButton.setWidth("890px");
    	userNameButton.setStyleName("danger");
    	dashBoardPanelLayout.addComponent(userNameButton);
    	
    	VerticalLayout userDetailsVerticalLayout = new VerticalLayout();
    	userDetailsVerticalLayout.setStyleName("wrapping");
    	userDetailsVerticalLayout.setSpacing(true);
    	userDetailsVerticalLayout.setMargin(true);
    	userDetailsVerticalLayout.setWidth("-1px");
    	userDetailsVerticalLayout.setHeight("-1px");
    	
    	PlayingStats ps = playingRepo.getPlayingStats(SecurityContextUtils.getUser().getUsername());
    	Label totalPlayed = new Label("Total Played : "+ps.getTotalPlayed());
    	Label totalWin = new Label("Total Win : "+ps.getTotalWin());
    	Label totalLose = new Label("Total Lose : "+ps.getTotalLoss());
    	
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
    	
    	System.out.println("Callingggggggggggggggggggggggg   "+SecurityContextUtils.getUser().getUsername());
    	//quizzerService.getPlayingStats();
    	updateUserListDetails();
    	
    	mainLayout.addComponent(panel);
    	mainLayout.addComponent(dashBoardPanel);
    	
    }
    
    private void updateUserListDetails() {
    	panelLayout.removeAllComponents();
    	
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>    Sender " + loggedInUser.toString());
    	List<Quizzer> quizzers = quizzerService.fetchAllQuizzerExceptSelf(loggedInUser.getSenderId());
    	
    	for(Quizzer quizzer : quizzers){
    		Button userButton;
    		OnlineQuizzers onlineUsers = isQuizzerActive(quizzer.getQuizzer_ID());
    		if(onlineUsers != null && null != onlineUsers.getOnlineStatus()) {
    			userButton = new Button();
    			userButton.setCaptionAsHtml(true);
    			switch(onlineUsers.getOnlineStatus()) {
    			case AVAILIABLE:
    				userButton.setCaption( quizzer.getEmployee().getUsername() + "<span style=\'color: " + "lime" + " !important; left:3px; top:2px;position:relative;\'>" + VaadinIcons.DOT_CIRCLE.getHtml()  + "</span>" + VaadinIcons.PENCIL.getHtml() );
    				break;
    			case BUSY:
    				userButton.setCaption( quizzer.getEmployee().getUsername() + "<span style=\'color: " + "red" + " !important; left:3px; top:2px;position:relative;\'>" + VaadinIcons.DOT_CIRCLE.getHtml()  + "</span>" );
    				break;
    			case AWAY:
    				userButton.setCaption( quizzer.getEmployee().getUsername() + "<span style=\'color: " + "yellow" + " !important; left:3px; top:2px;position:relative;\'>" + VaadinIcons.DOT_CIRCLE.getHtml()  + "</span>" );
    				break;
    			}
    			userButton.addStyleName(ValoTheme.BUTTON_TINY);
    			
    		} else {
    			userButton = new Button();
    			userButton.setCaptionAsHtml(true);
    			userButton.setCaption(quizzer.getEmployee().getUsername() + "<span>"+ VaadinIcons.PENCIL.getHtml() + "</span>");
    			userButton.addStyleName(ValoTheme.BUTTON_TINY);
    		}
    		
    		//userButton.setStyleName("primary");
    		userButton.setWidth("250px");
    		userButton.setHeight("-1px");
    		userButton.setData(quizzer);
    		
    		userButton.addClickListener(event-> {
    			if(isQuizzerActive(quizzer.getQuizzer_ID()) != null) {
    				Receiver receiver = new Receiver();
    				receiver.setReceiverId(quizzer.getEmployee().getUsername());
    				receiver.setReceiverName(quizzer.getEmployee().getUsername());
    				System.out.println("=============================> Receiver "+receiver.toString());
    				createWindow(receiver);
    			}
    			
    		});
    		panelLayout.addComponent(userButton);
    	}
    }
    
    private void questionAnswerDashboard(Object o){
    	questionLayout = new VerticalLayout();
    	questionLayout.setStyleName("wrapping");
    	questionLayout.setSpacing(true);
    	questionLayout.setMargin(true);
    	questionLayout.setWidth("-1px");
    	questionLayout.setHeight("-1px");
    	
    	HorizontalLayout answerLayoutOne = new HorizontalLayout();
    	HorizontalLayout answerLayoutTwo = new HorizontalLayout();
    	
   //	questionList = quizzerService.getQuestions(1);
    	questionIndex=1;
    	
    //	System.out.println("Question count...----------------------> "+questionList.size());
    	Questions question =  playingRepo.getQuestion((Requests)o); //questionList.get(questionIndex);
    		questionButton = new Button();
        	questionButton.setWidth("890px");
        	questionButton.setHeight("140px");
        	questionButton.setCaption(question.getQuestion());
        	questionButton.setStyleName("primary");
        	
        	answerOneButton = new Button();
        	answerOneButton.setWidth("440px");
        	answerOneButton.setCaption(question.getAnswer1());
        	answerOneButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        	
        	answerTwoButton = new Button();
        	answerTwoButton.setWidth("440px");
        	answerTwoButton.setCaption(question.getAnswer2());
        	answerTwoButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        	
        	answerThreeButton = new Button();
        	answerThreeButton.setWidth("440px");
        	answerThreeButton.setCaption(question.getAnswer3());
        	answerThreeButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        	
        	answerFourButton = new Button();
        	answerFourButton.setWidth("440px");
        	answerFourButton.setCaption(question.getAnswer4());
        	answerFourButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        	
        	nextQuestionButton = new Button();
        	nextQuestionButton.setCaption("Next");
        	nextQuestionButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        	nextQuestionButton.setEnabled(false);
        	
    	answerOneButton.addClickListener(event->{
    		registerAnswer((Requests)o, answerOneButton.getCaption());
    		isClickedAnswer = true;
    		timer.cancel();
            timer.purge();
            answerOneButton.setStyleName(ValoTheme.BUTTON_DANGER);
            answerOneButton.setEnabled(false);
            answerTwoButton.setEnabled(false);
            answerThreeButton.setEnabled(false);
            answerFourButton.setEnabled(false);
            nextQuestionButton.setEnabled(true);
            bar.setVisible(false);
    	});
    	
    	answerTwoButton.addClickListener(event->{
    		registerAnswer((Requests)o, answerTwoButton.getCaption());
    		isClickedAnswer = true;
    		timer.cancel();
            timer.purge();
            answerTwoButton.setStyleName(ValoTheme.BUTTON_DANGER);
            answerOneButton.setEnabled(false);
            answerTwoButton.setEnabled(false);
            answerThreeButton.setEnabled(false);
            answerFourButton.setEnabled(false);
            nextQuestionButton.setEnabled(true);
            bar.setVisible(false);
        	
    	});
    	
    	answerThreeButton.addClickListener(event->{
    		registerAnswer((Requests)o, answerThreeButton.getCaption());
    		isClickedAnswer = true;
    		timer.cancel();
            timer.purge();
    		
            answerThreeButton.setStyleName(ValoTheme.BUTTON_DANGER);
            answerOneButton.setEnabled(false);
            answerTwoButton.setEnabled(false);
            answerThreeButton.setEnabled(false);
            answerFourButton.setEnabled(false);
            nextQuestionButton.setEnabled(true);
            bar.setVisible(false);
    	});
    	
    	answerFourButton.addClickListener(event->{
    		registerAnswer((Requests)o, answerFourButton.getCaption());
    		isClickedAnswer = true;
    		timer.cancel();
            timer.purge();
            answerFourButton.setStyleName(ValoTheme.BUTTON_DANGER);
            answerOneButton.setEnabled(false);
            answerTwoButton.setEnabled(false);
            answerThreeButton.setEnabled(false);
            answerFourButton.setEnabled(false);
            nextQuestionButton.setEnabled(true);
            bar.setVisible(false);
    	});
    	
    	nextQuestionButton.addClickListener(event->{
         	bar.setValue(0.0f);
     		commonQuestion((Requests)o);
     		answerOneButton.setEnabled(true);
            answerTwoButton.setEnabled(true);
            answerThreeButton.setEnabled(true);
            answerFourButton.setEnabled(true);
            bar.setVisible(true);
    	});
    	
    	answerLayoutOne.addComponent(answerOneButton);
    	answerLayoutOne.addComponent(answerTwoButton);
    	answerLayoutTwo.addComponent(answerThreeButton);
    	answerLayoutTwo.addComponent(answerFourButton);
    	
    	questionLayout.addComponent(questionButton);
    	questionLayout.addComponent(answerLayoutOne);
    	questionLayout.addComponent(answerLayoutTwo);
    	questionLayout.addComponent(nextQuestionButton);
    	questionLayout.setComponentAlignment(nextQuestionButton, Alignment.BOTTOM_RIGHT);
    	bar.setVisible(true);
    	questionLayout.addComponent(bar);
    	questionLayout.setComponentAlignment(bar, Alignment.BOTTOM_LEFT);
    	
    	questionPanel = new Panel(questionLayout);
    	questionPanel.setStyleName("light");
    	questionPanel.setCaption("Quiz");
    	questionPanel.setWidth("920px");
    	questionPanel.setHeight("405px");
    	updateProgressBar((Requests)o);
    	
    }
    
    void registerAnswer(Requests req, String answer) {
    	if(req.getSender().equals(loggedInUser.getSenderId())) {
    		playingRepo.registerSenderAnswer(req, answer);
    	}else {
    		playingRepo.registerReceiverAnswer(req, answer);
    	}
    }
    
    private void updateProgressBar(Requests req) {
    	
    	UI ui = UI.getCurrent();
    	timer = new Timer();
		TimerTask t = new TimerTask() {
			
			@Override
			public void run() {
				
				 ui.access(() -> {
			            final float newValue;
			            if (bar.getValue() >= 1.0f) {//(progress == maxProgress) {
			                ui.setPollInterval(-1);
			                bar.setEnabled(true);
			                newValue = 0f;
			                bar.setVisible(!bar.isIndeterminate());
			                //Notification.show("Finished");
			                timer.cancel();
			                timer.purge();	
			                commonQuestion(req);
			            } else {
			                newValue =  bar.getValue() + 0.10f;// (float) progress / maxProgress;
			            }
			            bar.setValue(newValue);
			            //Notification.show("Value changed:", Float.toString(newValue), Notification.Type.TRAY_NOTIFICATION);
			        });
			}
		};
		
		
		timer.schedule(t, 0, 1000);
    	
    }
    
    private void commonQuestion(Requests req) {
    	if(!isClickedAnswer) {
    		registerAnswer(req, "Taal pakao");
    		try {
				Notification.show("Your answer taking as wrong", Type.TRAY_NOTIFICATION);
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
    	questionIndex++;
    	if(questionIndex < 4) {
    		Questions question =  playingRepo.getQuestion(req); 
    		answerOneButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    		answerTwoButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    		answerThreeButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    		answerFourButton.setStyleName(ValoTheme.BUTTON_FRIENDLY);
    		questionButton.setCaption(question.getQuestion());
    		answerOneButton.setCaption(question.getAnswer1());
    		answerTwoButton.setCaption(question.getAnswer2());
    		answerThreeButton.setCaption(question.getAnswer3());
    		answerFourButton.setCaption(question.getAnswer4());
    		nextQuestionButton.setEnabled(false);
    		updateProgressBar(req);
    	} else {
    		timer.cancel();
            timer.purge();
    		populateResultDashBoard(req);
    	}
    }
    
	private void populateResultDashBoard(Requests request) {
		List<Summary> summary = playingRepo.getSummary(request);
		resultDashBoardLayout = new VerticalLayout();
		resultDashBoardLayout.setStyleName("wrapping");
		resultDashBoardLayout.setSpacing(true);
		resultDashBoardLayout.setMargin(true);
		resultDashBoardLayout.setWidth("-1px");
		resultDashBoardLayout.setHeight("-1px");

		Grid<Summary> grid = new Grid<>();
		grid.setWidth("850px");
		grid.setHeight("-1px");
		grid.setHeightByRows(6);
		grid.setColumnResizeMode(ColumnResizeMode.SIMPLE);
		grid.setResponsive(true);
		grid.setItems(summary);
		grid.addColumn(Summary::getQuestion).setCaption("Question");
		grid.addColumn(Summary::getAnswer).setCaption("Answer");
		grid.addColumn(Summary::getSenderAnswer, new HtmlRenderer()).setCaption(request.getSender());
		grid.addColumn(Summary::getReceiverAnswer, new HtmlRenderer()).setCaption(request.getReceiver());
		
		Button gotoDashBoard = new Button();
		gotoDashBoard.setCaption("Goto DashBoard");
		gotoDashBoard.addClickListener(event -> {
			
			mainLayout.removeComponent(resultPanel);
			mainLayout.removeComponent(panelLayout);
			mainLayout.removeComponent(panel);
			initializeQizzerMainDashboard();
		});
		
		resultDashBoardLayout.addComponent(grid);
		resultDashBoardLayout.addComponent(gotoDashBoard);
		resultPanel = new Panel(resultDashBoardLayout);
		resultPanel.setStyleName("light");
		resultPanel.setCaption("Result");
		resultPanel.setWidth("900px");
		resultPanel.setHeight("405px");
		questionIndex = 0;

		mainLayout.removeComponent(questionPanel);
		mainLayout.addComponent(resultPanel);
	}
    
	OnlineQuizzers isQuizzerActive(Long quizzer_ID) {
    	List<OnlineQuizzers> activeQuizzers = cacheService.getQuizzers();
		for(OnlineQuizzers q : activeQuizzers) {
			if(q.getQuizzer_ID() == quizzer_ID) {
				return q;
			}
		}
		//return true;
		return null;
    }
    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent view) {
    	/*userOneButton.addClickListener(event-> {
    		panelLayout.addComponent(new Label("New"));
    	});*/
    }
    
	private void createWindow(Receiver receiver) {
		VerticalLayout windowLayout = new VerticalLayout();
		windowLayout.setMargin(true);
		windowLayout.setSpacing(true);
		windowLayout.setWidth("-1px");
		windowLayout.setHeight("-1px");
		
		Label questionLabel = new Label();
		questionLabel.setValue("Do you want to send request?");
		List<QuizSet> quizSet = quizzerService.getQuizSet();
		ComboBox<QuizSet> questionTypeComboBox = new ComboBox<>("Select a quistion type");
		questionTypeComboBox.setItemCaptionGenerator(QuizSet::getQuizSetName);
		questionTypeComboBox.setEmptySelectionAllowed(false);
		questionTypeComboBox.setWidth("220px");
		questionTypeComboBox.setHeight("-1px");
		questionTypeComboBox.setItems(quizSet);
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
			if(null == questionTypeComboBox.getValue()){
				Notification.show("Please select a valid data", Type.WARNING_MESSAGE);
				return;
			}
		
			cacheService.setPushCache(loggedInUser, receiver);
			// todo
			Requests request = new Requests();
			request.setSender(loggedInUser.getSenderId());
			request.setReceiver(receiver.getReceiverId());
			request.setRequestTime(new Date());
			request.setChallengeType(((QuizSet)questionTypeComboBox.getValue()).getQuizId());
			
			playingRepo.sendPlayingRequest(request);
			// TODO set sender entry
		//	Broadcaster.broadcast("challenge "+loggedInUser.getSenderName());
			Broadcaster.broadcast("challenge", request);
			quistenWindow.close();
		});
		
		noButton.addClickListener(event-> {
			quistenWindow.close();
		});
	}
	
	private void confirmSkipWindow() {
		VerticalLayout windowLayout = new VerticalLayout();
		windowLayout.setMargin(true);
		windowLayout.setSpacing(true);
		windowLayout.setWidth("-1px");
		windowLayout.setHeight("-1px");
		
		Label questionLabel = new Label();
		questionLabel.setValue("You have an active session. \nDo you want to continue with the old session or you want to play a new game?");
		
		windowLayout.addComponent(questionLabel);
		
		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setSpacing(true);
		buttonLayout.setWidth("-1px");
		buttonLayout.setHeight("-1px");
		
		Button continuePlay = new Button();
		continuePlay.setCaption("Continue play");
		continuePlay.setStyleName("primary");
		
		Button newGame = new Button();
		newGame.setCaption("new Game");
		newGame.setStyleName("primary");
		
		buttonLayout.addComponent(continuePlay);
		buttonLayout.addComponent(newGame);
		windowLayout.addComponent(buttonLayout);
		windowLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
		
		
		
		Window quistenWindow = new Window("Confirm");
		quistenWindow.setModal(true);
		quistenWindow.setClosable(true);
		quistenWindow.setWidth("-1px");
		quistenWindow.setHeight("-1px");
		quistenWindow.center();
		quistenWindow.setContent(windowLayout);
		UI.getCurrent().addWindow(quistenWindow);
		
		continuePlay.addClickListener(event-> {
			quistenWindow.close();
		});
		
		newGame.addClickListener(event-> {
			quistenWindow.close();
		});
	}

	private void createConfirmWindow(String senderName, Object o) {
		VerticalLayout vLayout = new VerticalLayout();
		vLayout.setMargin(true);
		vLayout.setSpacing(true);
		vLayout.setWidth("-1px");
		vLayout.setHeight("-1px");
		
		Label qLabel = new Label();
		qLabel.setValue(senderName + " challenged you in Java.");
		
		vLayout.addComponent(qLabel);
		
		HorizontalLayout hLayout = new HorizontalLayout();
		hLayout.setSpacing(true);
		hLayout.setWidth("-1px");
		hLayout.setHeight("-1px");
		
		Button playButton = new Button();
		playButton.setCaption("Play");
		playButton.setStyleName("primary");
		
		Button denyButton = new Button();
		denyButton.setCaption("Deny");
		denyButton.setStyleName("primary");
		
		hLayout.addComponent(playButton);
		hLayout.addComponent(denyButton);
		vLayout.addComponent(hLayout);
		vLayout.setComponentAlignment(hLayout, Alignment.MIDDLE_CENTER);
		
		Window confirmRequestWindow = new Window("Quizzer");
		confirmRequestWindow.setModal(true);
		confirmRequestWindow.setClosable(true);
		confirmRequestWindow.setWidth("244px");
		confirmRequestWindow.setHeight("-1px");
		confirmRequestWindow.center();
		confirmRequestWindow.setContent(vLayout);
		UI.getCurrent().addWindow(confirmRequestWindow);
		
		playButton.addClickListener(event-> {
			Requests request = (Requests) o;
			playingRepo.acceptedChallenge(request);
			Broadcaster.broadcast("PlayQuiz", request);
			confirmRequestWindow.close();
		});
		
		denyButton.addClickListener(event-> {
			// todo deny
			Requests request = (Requests) o;
			playingRepo.denySession(request);
			confirmRequestWindow.close();
		});
	}
	
	@Override
	public void receiveBroadcast(UI ui, String message) {
		System.out.println("In receive");
		ui.access(new Runnable() {

			@Override
			public void run() {
				System.out.println("Message" + message + "::" + loggedInUser + "'");
				if ("Update user".equals(message)) {
					System.out.println("------------------------------------------------------------" + message);
					// Notification.show(message, Type.WARNING_MESSAGE);
					updateUserListDetails();
				} else if ("PlayQuiz".equals(message)) {
					/*questionAnswerDashboard(new Object());
					mainLayout.removeComponent(dashBoardPanel);
					if(null != resultPanel) {
						mainLayout.removeComponent(resultPanel);
					}
					mainLayout.addComponent(questionPanel);*/
				} else {
					Sender sender = cacheService.getPushCache(loggedInUser.getSenderId());
					if (sender != null) {
						System.out.println(sender.toString());
				//		createConfirmWindow(sender.getSenderName());
					}
					ui.push();
				}

			}
		});
		
	}


	@Override
	public void receiveBroadcast(UI ui, String message, Object o) {
		ui.access(new Runnable() {
			@Override
			public void run() {
				switch(message) {
				case "challenge":
					Sender sender = cacheService.getPushCache(loggedInUser.getSenderId());
					if (sender != null) {
						System.out.println(sender.toString());
						createConfirmWindow(sender.getSenderName(), o);
					}
					break;
				case "PlayQuiz":
					questionAnswerDashboard(o);
					mainLayout.removeComponent(dashBoardPanel);
					if(null != resultPanel) {
						mainLayout.removeComponent(resultPanel);
					}
					mainLayout.addComponent(questionPanel);
				}
				ui.push();
			}
		});
	}


	@Override
	public String checkAnswerValue(boolean v) {
		
		return v? "Right" : "Wrong";
	}
}