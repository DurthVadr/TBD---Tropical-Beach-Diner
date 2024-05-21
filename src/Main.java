import GUI.MainMenu;

import java.awt.GraphicsEnvironment;
public class Main {

    
    public static void main(String[] args) {

        //list all available fonts if you want to use something else

        // GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // String[] fontNames = ge.getAvailableFontFamilyNames();
        // for (String fontName : fontNames) {
        //     System.out.println(fontName);
        // }


      
        // Initialize the GUI components
        MainMenu mainMenu = new MainMenu();

        // Show the main menu initially
        mainMenu.setVisible(true);
    }
}
