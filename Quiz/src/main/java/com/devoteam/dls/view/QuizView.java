package com.devoteam.dls.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@SpringView(name = QuizView.VIEW_NAME)
public class QuizView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "quiz";

    @PostConstruct
    public void init() {
      // addComponent(new Label("Hello, this is quiz view."));
      // AbstractOrderedLayout layout = new UserViewDesign();
       //layout.setComponentAlignment(layout, Alignment.TOP_LEFT);
       addComponent(new UserViewDesign());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
