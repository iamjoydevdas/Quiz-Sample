package com.devoteam.dls.view;

import com.devoteam.dls.dao.EmployeeRepository;
import com.devoteam.dls.domain.Employee;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by basakpie on 2017. 5. 11..
 */
@Secured({"ROLE_ADMIN"})
@SpringView(name = AdminView.VIEW_NAME)
public class AdminView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "admin";

    @Autowired
    EmployeeRepository userRepository;

    @PostConstruct
    public void init() {
        addComponent(new Label("Hello, this is admin view."));

        List<Employee> users = userRepository.findAll();

        Grid<Employee> grid = new Grid<>();
        grid.setSizeFull();
        grid.setItems(users);
        grid.addColumn(Employee::getUsername).setCaption("Name");
        grid.addColumn(Employee::getPassword).setCaption("Password");

        addComponent(grid);
        setExpandRatio(grid, 1f);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}

