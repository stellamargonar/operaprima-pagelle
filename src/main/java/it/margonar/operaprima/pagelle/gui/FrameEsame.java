package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Esame;
import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.PagellaDAO;

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
public class FrameEsame extends JFrame {

	@Autowired
	PagellaDAO pagellaDAO;

	private JTextField data;
	private JButton submit;

	private Studente studente;
	private SimpleDateFormat dateFormat;

	private Esame esame;

	FrameEsame() {}

	public void draw() {
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		esame = pagellaDAO.findByStudente(studente, Esame.class);
		if (esame == null) {
			esame = new Esame();
		}

		JPanel pan = new JPanel();
		pan.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 2;
		pan.add(new JLabel("Data"), c);

		data = new JTextField(30);
		if (esame.getData() != null) data.setText(dateFormat.format(esame
				.getData()));
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		pan.add(data, c);

		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 3;
		submit = new JButton("Salva Esame");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				try {
					esame.setData(convertDate(data.getText()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				esame.setStudente(studente);

				esame = pagellaDAO.save(esame);
				closeWindow();
			}

			private Date convertDate(String dataString) throws ParseException {
				Date convertedDate = dateFormat.parse(dataString);
				return convertedDate;
			}
		});

		JButton delete = new JButton("Elimina Esame");
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (esame.getStudente() != null) pagellaDAO.delete(esame);
				closeWindow();
			}
		});
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
		setTitle("Esame Studente " + studente.getCognome() + " "
				+ studente.getNome());
	}

	private void closeWindow() {
		dispose();
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

}
