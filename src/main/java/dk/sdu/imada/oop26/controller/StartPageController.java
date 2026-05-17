package dk.sdu.imada.oop26.controller;

import dk.sdu.imada.oop26.view.StartPageView;
import dk.sdu.imada.oop26.view.ViewHandler;

public class StartPageController {

    private final StartPageView view;
    private final ViewHandler viewHandler;

    public StartPageController(StartPageView view, ViewHandler viewHandler) {
        this.view = view;
        this.viewHandler = viewHandler;
        initBindings();
    }

    private void initBindings() { // Set the action for the start button to open the Pacman screen
        view.setStartButtonAction(e -> viewHandler.openPacmanScreen());
    }
}
