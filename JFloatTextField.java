package nutrition_tracker;

import java.awt.event.KeyEvent;

import javax.swing.JTextField;

/**
 * A {@link JTextField} that skips all non-digit keys. The user is able to enter whole numbers or floats.
 * 
 * @author Michi Gysel <michi@scythe.ch>; edited by Jacob Heddings
 * 
 */
public class JFloatTextField extends JTextField {
    private static final long serialVersionUID = 1L;

    @Override
    public void processKeyEvent(KeyEvent ev) {
    	char inChar = ev.getKeyChar();
        if (Character.isDigit(inChar) || ev.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            super.processKeyEvent(ev);
        }
        if (inChar == '.') {
        	if (getText().indexOf(".") == -1) {
        		super.processKeyEvent(ev);
        	}
        }
        ev.consume();
        return;
    }

    /**
     * As the user is not even able to enter a dot ("."), only integers (whole numbers) may be entered.
     */
    public float getNumber() {
        float result = 0;
        String text = getText();
        if (text != null && !"".equals(text)) {
            result = Float.valueOf(text);
        } else {
        	result = 0;
        }
        return result;
    }

}