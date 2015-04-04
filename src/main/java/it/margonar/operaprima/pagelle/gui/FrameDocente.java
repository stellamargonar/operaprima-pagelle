package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.repository.DocenteDAO;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
public class FrameDocente extends JFrame {

	@Autowired
	DocenteDAO docenteDAO;

	private JTextField nome;
	private JTextField cognome;
	private JButton submit;

	private Docente docente;

	FrameDocente() {}

	public void draw() {
		if (docente == null) docente = new Docente();

		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		JPanel input = new JPanel(new SpringLayout());

		input.add(new JLabel("Nome"));
		nome = new JTextField(30);
		nome.setText(docente.getNome());
		input.add(nome);

		input.add(new JLabel("Cognome"));
		cognome = new JTextField(30);
		cognome.setText(docente.getCognome());
		input.add(cognome);

		SpringUtilities.makeCompactGrid(input, 2, 2, 6, 6, 6, 6);

		pan.add(input);

		submit = getButton();
		submit.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan.add(submit);

		setContentPane(pan);
		setGui();

	}

	private JButton getButton() {
		submit = new JButton("Salva Docente");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!validate()) {
					JOptionPane.showMessageDialog(null,
							"Inserire nome e cognome docente");
					return;
				}

				docente.setNome(nome.getText());
				docente.setCognome(cognome.getText());
				docente = docenteDAO.save(docente);
				closeWindow();
			}

			private boolean validate() {
				return nome.getText() != null && !(nome.getText().equals(""))
						&& cognome.getText() != null
						&& !(cognome.getText().equals(""));
			}
		});
		return submit;
	}

	private void setGui() {
		setVisible(true);
		setSize(250, 120);

		setTitle("Docente");
	}

	private void closeWindow() {
		dispose();
	}

	public void setDocente(Docente d) {
		this.docente = d;
	}
}
