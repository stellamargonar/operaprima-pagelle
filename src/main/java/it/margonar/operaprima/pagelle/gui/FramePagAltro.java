package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.PagellaAltro;
import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.DocenteDAO;
import it.margonar.operaprima.pagelle.repository.PagellaDAO;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Component
public class FramePagAltro extends JFrame {

	private JTextField anno;
	private JTextArea repertorio;
	private JTextArea note;

	private JComboBox<String> sedeBox;
	private JComboBox<Docente> docenteBox;

	private JButton submit;

	private Studente studente;
	private PagellaAltro pa;

	@Autowired
	PagellaDAO pagellaDAO;
	@Autowired
	DocenteDAO docenteDAO;

	public FramePagAltro() {}

	FramePagAltro(Studente stu) {
		this.studente = stu;
	}

	public void draw() {
		pa = pagellaDAO.findByStudente(studente, PagellaAltro.class);
		if (pa == null) {
            pa = new PagellaAltro();
        }

		JPanel pan = new JPanel();
		pan.setLayout(new BorderLayout());

		pan.add(getInputPane(), BorderLayout.CENTER);
		pan.add(getButtonPane(), BorderLayout.SOUTH);

		setContentPane(pan);
		setGui();

	}

	private Component getLabel(String string) {
		JLabel label = new JLabel(string);
		return label;
	}

	private Component getInputPane() {
		JPanel pan = new JPanel(new SpringLayout());

		pan.add(getLabel("Docente"));
		pan.add(getDocenteBox());

		pan.add(getLabel("Sede"));
		pan.add(getSedeBox());

		pan.add(getLabel("Anno"));
		pan.add(getAnnoText());

		pan.add(getLabel("Repertorio"));
		pan.add(getRepertorioText());

		pan.add(getLabel("Complessivo"));
		pan.add(getNoteText());

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(pan, 5, 2, 6, 6, 6, 6);
		return pan;
	}

	private Component getDocenteBox() {
		Vector<Docente> lista = new Vector<Docente>(docenteDAO.findAll());
		docenteBox = new JComboBox<Docente>(lista);
		if (pa.getDocente() != null) {
			boolean cond = true;
			Enumeration<Docente> e = lista.elements();
			while (e.hasMoreElements() && cond) {
				Docente docente = e.nextElement();
				if (docente.equals(pa.getDocente())) {
					docenteBox.setSelectedItem(docente);
					cond = false;
				}
			}
		}
		return docenteBox;
	}

	private Component getSedeBox() {
        String[] sedi = { "Ala", "Avio", "Brentonico", "Mori", "Pannone" };
		sedeBox = new JComboBox<String>(sedi);

		if (pa.getSede() != null) {
            for (int i = 0; i < sedi.length; i++) {
                if (sedi[i].equals(pa.getSede())) {
                    sedeBox.setSelectedItem(sedi[i]);
                }
            }
        }

		return sedeBox;
	}

	private Component getAnnoText() {
		anno = new JTextField(30);
		if (pa != null) {
            anno.setText(Integer.toString(pa.getAnno()));
        }
		return anno;
	}

	private Component getRepertorioText() {
		repertorio = new JTextArea(4, 50);
		repertorio.setLineWrap(true);
		if (pa != null) {
            repertorio.setText(pa.getRepertorio());
        }
		return new JScrollPane(repertorio,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getNoteText() {
		note = new JTextArea(4, 50);

		if (pa != null) {
            note.setText(pa.getNote());
        }
		note.setFont(Font.getFont(Font.SANS_SERIF));
		note.setLineWrap(true);
		return new JScrollPane(note, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}

	private Component getButtonPane() {
		JPanel pan = new JPanel();

		submit = new JButton("Salva Pagella");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (!validate()) {
					JOptionPane.showMessageDialog(null,
							"Anno = " + anno.getText()
									+ " non e' un numero intero");
					return;
				}

				boolean cond = pa == null;
				if (cond) {
                    pa = new PagellaAltro();
                }

				pa.setStudente(studente);
				pa.setDocente((Docente) docenteBox.getSelectedItem());
				pa.setSede((String) sedeBox.getSelectedItem());
				pa.setAnno(Integer.parseInt(anno.getText()));
				pa.setRepertorio(repertorio.getText());
				pa.setNote(note.getText());

				pa = pagellaDAO.save(pa);
				closeWindow();
				return;

			}

			private boolean validate() {
				try {
					int i = Integer.parseInt(anno.getText());
					i++;
				} catch (NumberFormatException ex) {
					return false;
				}
				return true;

			}
		});

		JButton delete = new JButton("Elimina");
		delete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (pa.getStudente() != null) {
                    pagellaDAO.delete(pa);
                }
				closeWindow();
			}
		});

		pan.add(submit);
		pan.add(delete);
		return pan;
	}

	private void setGui() {
		setVisible(true);
		setSize(500, 400);
		setTitle("Pagella Altro: " + studente.getCognome() + " "
				+ studente.getNome());
	}

	private void closeWindow() {
		dispose();
	}

	public void setStudente(Studente studente) {
		this.studente = studente;
	}

}
