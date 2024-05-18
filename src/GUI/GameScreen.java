package GUI;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
public class GameScreen {
    List<JButton> tableAreaButtons;
    List<JButton> kitchenAreaButtons;
    JLabel informationText;
    JLabel scoreLabel;

    public GameScreen(List<JButton> tableAreaButtons, List<JButton> kitchenAreaButtons, JLabel informationText, JLabel scoreLabel) {
        this.tableAreaButtons = tableAreaButtons;
        this.kitchenAreaButtons = kitchenAreaButtons;
        this.informationText = informationText;
        this.scoreLabel = scoreLabel;

        for (JButton button : tableAreaButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    tableButtonClicked(button);
                }
            });
        }

        for (JButton button : kitchenAreaButtons) {
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    itemsButtonClicked(button);
                }
            });
        }
    }
    public void setScoreLabel(JLabel textLabel) {
        this.scoreLabel = textLabel;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

}
