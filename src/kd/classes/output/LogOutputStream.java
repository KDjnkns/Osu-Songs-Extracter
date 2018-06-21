package kd.classes.output;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;

public class LogOutputStream extends OutputStream {
    private JTextArea textArea;
     
    public LogOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }
     
    @Override
    public void write(int b) throws IOException {
        textArea.append(String.valueOf((char)b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}