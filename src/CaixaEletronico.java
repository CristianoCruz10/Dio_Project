import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CaixaEletronico extends JFrame {
    private double saldo = 0.00;
    private List<String> extrato = new ArrayList<>();
    private JTextArea extratoTextArea;
    private JTextField valorField;

    public CaixaEletronico() {
        setTitle("Caixa Eletrônico");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        extratoTextArea = new JTextArea(10, 30);
        extratoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(extratoTextArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel botoesPanel = new JPanel();
        JButton saqueButton = new JButton("Saque");
        JButton depositoButton = new JButton("Depósito");
        valorField = new JTextField(10);
        JButton extratoButton = new JButton("Extrato");

        saqueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarSaque();
            }
        });

        depositoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDeposito();
            }
        });

        extratoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirExtrato();
            }
        });

        botoesPanel.add(saqueButton);
        botoesPanel.add(depositoButton);
        botoesPanel.add(valorField);
        botoesPanel.add(extratoButton);

        add(botoesPanel, BorderLayout.SOUTH);
    }

    private void realizarSaque() {
        try {
            double valor = Double.parseDouble(valorField.getText());
            if (valor > 0 && valor <= saldo) {
                saldo -= valor;
                extrato.add("Saque: -" + formatarValor(valor) + " - Novo saldo: " + formatarValor(saldo));
                atualizarExtrato();
            } else {
                JOptionPane.showMessageDialog(this, "Valor de saque inválido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido.");
        }
        valorField.setText("");
    }

    private void realizarDeposito() {
        try {
            double valor = Double.parseDouble(valorField.getText());
            if (valor > 0) {
                saldo += valor;
                extrato.add("Depósito: " + formatarValor(valor) + " - Novo saldo: " + formatarValor(saldo));
                atualizarExtrato();
            } else {
                JOptionPane.showMessageDialog(this, "Valor de depósito inválido.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um valor válido.");
        }
        valorField.setText("");
    }

    private void exibirExtrato() {
        atualizarExtrato();
    }

    private void atualizarExtrato() {
        extratoTextArea.setText("");
        for (String entrada : extrato) {
            extratoTextArea.append(entrada + "\n");
        }
    }

    private String formatarValor(double valor) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CaixaEletronico().setVisible(true);
            }
        });
    }
}
