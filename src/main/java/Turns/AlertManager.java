package Turns;

import javax.swing.*;

import static Turns.TurnManager.currentTurn;

public class AlertManager {
    public static int nTurn = 1;

    public static void setCurrentTurn(int currentTurn) {
        TurnManager.currentTurn = currentTurn;
    }

    public static void alert() {
        alert(currentTurn);
    }

    public static void alert(int alerta) {
        switch (alerta) {
            case -1:
                JOptionPane.showMessageDialog(null, "Jugador 1 coloca tus palomas", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case -2:
                JOptionPane.showMessageDialog(null, "Jugador 2 coloca tus palomas", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case -3:
                JOptionPane.showMessageDialog(null, "No se puede colocar la paloma por encima del gato", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case -4:
                JOptionPane.showMessageDialog(null, "No se puede colocar la paloma sobre una casilla ocupada", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 0:
                JOptionPane.showMessageDialog(null, "Movimiento ilegal", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 1:
                JOptionPane.showMessageDialog(null, "Turno del jugador 1", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Turno del jugador 2", "", JOptionPane.INFORMATION_MESSAGE);
                break;
            case 3:
                JOptionPane.showMessageDialog(null, "No te puedes comer a tu propia paloma", "", JOptionPane.INFORMATION_MESSAGE);
            default:
                break;
        }
    }
}