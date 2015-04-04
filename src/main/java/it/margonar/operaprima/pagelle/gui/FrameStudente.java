package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.StudenteDAO;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FrameStudente extends JFrame {

	@Autowired
	StudenteDAO studenteDAO;
	@Autowired
	Main mainFrame;

	private JTextField cognome;
	private JTextField nome;
	private JTextField dataNascita;
	private JTextField dataIscrizione;

	private JButton submit;

	private Studente studente;
	private SimpleDateFormat dateFormat;

	private boolean canDelete = true;

	FrameStudente() {}

	public void draw() {
		canDelete = true;
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		if (studente == null) {
			studente = new Studente();
			canDelete = false;
		}

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 0;
		pan.add(new JLabel("Cognome"), c);

		cognome = new JTextField(30);
		cognome.setText(studente.getCognome());
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 2;
		pan.add(cognome, c);

		c.gridx = 0;
		c.gridy = 1;
		pan.add(new JLabel("Nome"), c);

		nome = new JTextField(30);
		nome.setText(studente.getNome());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		pan.add(nome, c);

		c.gridx = 0;
		c.gridy = 2;
		pan.add(new JLabel("Data Nascita"), c);

		dataNascita = new JTextField(30);
		if (studente.getDataNascita() != null) dataNascita.setText(dateFormat
				.format(studente.getDataNascita()));
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		pan.add(dataNascita, c);

		c.gridx = 0;
		c.gridy = 3;
		pan.add(new JLabel("Data Iscrizione"), c);

		dataIscrizione = new JTextField(30);
		if (studente.getDataIscrizione() != null) dataIscrizione
				.setText(dateFormat.format(studente.getDataIscrizione()));
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 2;
		pan.add(dataIscrizione, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		submit = new JButton("Salva Studente");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				studente.setCognome(cognome.getText());
				studente.setNome(nome.getText());
				try {
					studente.setDataNascita(convertDate(dataNascita.getText()));
					studente.setDataIscrizione(convertDate(dataIscrizione
							.getText()));

				} catch (ParseException e) {}
				studente = studenteDAO.save(studente);
				mainFrame.updateEventOccurred(null);
				closeWindow();
			}

			private Date convertDate(String dataString) throws ParseException {
				Date convertedDate = dateFormat.parse(dataString);
				return convertedDate;
			}
		});

		JButton delete = new JButton("Elimina Studente");
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				studenteDAO.delete(studente);
				mainFrame.updateEventOccurred(null);
				closeWindow();
			}
		});
		delete.setEnabled(canDelete);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(submit);
		buttonPanel.add(delete);
		pan.add(buttonPanel, c);

		setContentPane(pan);
		setGui();

	}

	private void setGui() {
		setVisible(true);
		setSize(500, 200);
		if (studente == null) setTitle("Nuovo Studente");
		else setTitle("Modifica Studente");
	}

	private void closeWindow() {
		dispose();
	}

	@Autowired
	public void setMain(Main frame) {
		this.mainFrame = frame;
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

}
