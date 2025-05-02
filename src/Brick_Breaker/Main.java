package Brick_Breaker;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
          JFrame o = new JFrame();
          Gameplay gp =  new Gameplay();
          o.setBounds(10,10,700,600);
          o.setTitle("Brick Breaker");
          o.setResizable(false);
          o.setVisible(true);
          o.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          o.add(gp);
    }
}