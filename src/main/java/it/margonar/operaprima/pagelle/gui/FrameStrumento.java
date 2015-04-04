package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Strumento;
import it.margonar.operaprima.pagelle.repository.CorsiDAO;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component
public class FrameStrumento extends JFrame {

	private JTextField nome;
	private JButton submit;

	@Autowired
	CorsiDAO corsiDAO;

	private Strumento strumento;

	FrameStrumento() {}

	public void draw() {
		if (strumento == null) strumento = new Strumento();
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		JPanel input = new JPanel(new SpringLayout());
		input.add(new JLabel("Nome"));
		nome = new JTextField(30);
		nome.setText(strumento.getNome());
		input.add(nome);
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(input, 1, 2, 6, 6, 6, 6);

		pan.add(input);
		pan.add(Box.createVerticalGlue());

		submit = getButton();
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan.add(submit);

		setContentPane(pan);
		setGui();

	}

	private JButton getButton() {
		submit = new JButton("Salva Strumento");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!validate()) {
					JOptionPane.showMessageDialog(null,
							"Inserire nome strumento");
					return;
				}

				strumento.setNome(nome.getText());
				strumento = corsiDAO.save(strumento);
				closeWindow();
			}

			private boolean validate() {
				return nome.getText() != null && !(nome.getText().equals(""));
			}
		});
		return submit;
	}

	private void setGui() {
		setVisible(true);
		setSize(250, 100);

		setTitle("Strumento");
	}

	private void closeWindow() {
		dispose();
	}

	public void setStrumento(Strumento s) {
		this.strumento = s;
	}

}
