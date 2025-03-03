import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BandeiraCartaoCredito {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Validador de Cartão de Crédito");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Digite o número do cartão: ");
        JTextField inputField = new JTextField(20);
        JButton validateButton = new JButton("Validar");
        JLabel resultLabel = new JLabel();

        frame.add(label);
        frame.add(inputField);
        frame.add(validateButton);
        frame.add(resultLabel);

        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String numeroCartao = inputField.getText().replaceAll("\\s", "");

                if (!numeroCartao.matches("\\d+")) {
                    resultLabel.setText("Erro: Apenas números são permitidos.");
                    return;
                }

                if (!validarLuhn(numeroCartao)) {
                    resultLabel.setText("Número de cartão inválido.");
                    return;
                }

                String bandeira = identificarBandeira(numeroCartao);
                if (bandeira == null) {
                    resultLabel.setText("Bandeira não reconhecida.");
                } else {
                    resultLabel.setText("Cartão válido: " + bandeira);
                }
            }
        });

        frame.setVisible(true);
    }

    public static boolean validarLuhn(String numero) {
        int soma = 0;
        boolean alternar = false;

        for (int i = numero.length() - 1; i >= 0; i--) {
            int digito = Character.getNumericValue(numero.charAt(i));

            if (alternar) {
                digito *= 2;
                if (digito > 9) digito -= 9;
            }

            soma += digito;
            alternar = !alternar;
        }

        return soma % 10 == 0;
    }

    public static String identificarBandeira(String numero) {
        int comprimento = numero.length();

        if (numero.startsWith("4") && comprimento == 16) {
            return "Visa";
        } else if ((numero.startsWith("51") || numero.startsWith("52") || numero.startsWith("53") ||
                numero.startsWith("54") || numero.startsWith("55") ||
                (numero.startsWith("2221") || numero.startsWith("2720"))) && comprimento == 16) {
            return "MasterCard";
        } else if ((numero.startsWith("34") || numero.startsWith("37")) && comprimento == 15) {
            return "American Express";
        } else if ((numero.startsWith("300") || numero.startsWith("301") || numero.startsWith("302") ||
                numero.startsWith("303") || numero.startsWith("304") || numero.startsWith("305") ||
                numero.startsWith("36") || numero.startsWith("38")) && comprimento == 14) {
            return "Diners Club";
        } else if ((numero.startsWith("6011") || numero.startsWith("65") ||
                (numero.startsWith("644") || numero.startsWith("649"))) && comprimento == 16) {
            return "Discover";
        } else if ((numero.startsWith("2014") || numero.startsWith("2149")) && comprimento == 15) {
            return "EnRoute";
        } else if (numero.startsWith("8699") && comprimento == 15) {
            return "Voyager";
        } else if (numero.startsWith("35") && comprimento == 16) {
            return "JCB";
        } else if (numero.startsWith("606282") && comprimento == 16) {
            return "HiperCard";
        } else if (numero.startsWith("50") && comprimento == 16) {
            return "Aura";
        }

        return null;
    }
}
