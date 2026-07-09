package util;

import javax.swing.*;

public class MensagemUtil {
    public static void erro(JFrame frame, String mensagem) { JOptionPane.showMessageDialog(frame, mensagem, "Erro", JOptionPane.ERROR_MESSAGE); }
    public static void sucesso(JFrame frame, String mensagem) { JOptionPane.showMessageDialog(frame, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE); }
}
