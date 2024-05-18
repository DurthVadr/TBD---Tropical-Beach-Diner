package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller {
    private MainMenu mainMenu;
    private GameScreen gameScreen;

    public Controller(MainMenu mainMenu, GameScreen gameScreen) {
        this.mainMenu = mainMenu;
        this.gameScreen = gameScreen;

        mainMenu.StartGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuAction(e);
            }
        });

        mainMenu.ExitGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainMenuAction(e);
            }
        });

        for (JButton button : gameScreen.tableAreaButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameScreenAction(e);
                }
            });
        }

        for (JButton button : gameScreen.kitchenAreaButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameScreenAction(e);
                }
            });
        }
    }
}
