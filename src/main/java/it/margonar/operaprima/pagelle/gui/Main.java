package it.margonar.operaprima.pagelle.gui;

import it.margonar.operaprima.pagelle.model.Docente;
import it.margonar.operaprima.pagelle.model.Esame;
import it.margonar.operaprima.pagelle.model.Laboratorio;
import it.margonar.operaprima.pagelle.model.Orchestra;
import it.margonar.operaprima.pagelle.model.PagellaAltro;
import it.margonar.operaprima.pagelle.model.PagellaCoro;
import it.margonar.operaprima.pagelle.model.PagellaFormazione;
import it.margonar.operaprima.pagelle.model.PagellaLaboratorio;
import it.margonar.operaprima.pagelle.model.PagellaOrchestra;
import it.margonar.operaprima.pagelle.model.PagellaStrumento;
import it.margonar.operaprima.pagelle.model.Strumento;
import it.margonar.operaprima.pagelle.model.Studente;
import it.margonar.operaprima.pagelle.repository.CorsiDAO;
import it.margonar.operaprima.pagelle.repository.DocenteDAO;
import it.margonar.operaprima.pagelle.repository.PagellaDAO;
import it.margonar.operaprima.pagelle.repository.StudenteDAO;
import it.margonar.operaprima.pagelle.utils.PDF;
import it.margonar.operaprima.pagelle.utils.Summary;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Main extends JFrame {

	private JPanel pan;
	private JPanel panList;
	private JMenuBar menuBar;
	private JTextField text;
	FilteringJList lista;

	@Autowired
	StudenteDAO studenteDAO;
	@Autowired
	PagellaDAO pagellaDAO;
	@Autowired
	DocenteDAO docenteDAO;
	@Autowired
	CorsiDAO corsiDAO;

	@Autowired
	FrameDocente frameDocente;
	@Autowired
	FrameLaboratorio frameLaboratorio;
	@Autowired
	FrameOrchestra frameOrchestra;
	@Autowired
	FramePagAltro framePagAltro;
	@Autowired
	FramePagCoro framePagCoro;
	@Autowired
	FramePagFormazione framePagFormazione;
	@Autowired
	FramePagLaboratorio framePagLaboratorio;
	@Autowired
	FramePagOrchestra framePagOrchestra;
	@Autowired
	FramePagStrumento framePagStrumento;
	@Autowired
	FrameStrumento frameStrumento;
	@Autowired
	FrameStudente frameStudente;
	@Autowired
	FrameEsame frameEsame;

	@Autowired
	PDF pdf;
	@Autowired
	Log logger;

	public void init() {
		System.out.println("here");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// pannello
		pan = new JPanel();
		pan.setLayout(new BorderLayout());

		panList = new JPanel();
		panList.setLayout(new BorderLayout());

		menuBar = new JMenuBar();

		// Menu 1
		JMenu nuovo = new JMenu("Nuovo");
		JMenuItem bstu = new JMenuItem("Studente");
		JMenuItem bdoc = new JMenuItem("Docente");
		JMenuItem bstru = new JMenuItem("Strumento");
		JMenuItem blab = new JMenuItem("Laboratorio");
		JMenuItem borc = new JMenuItem("Orchestra");

		nuovo.add(bstu);
		nuovo.add(bdoc);
		nuovo.add(bstru);
		nuovo.add(blab);
		nuovo.add(borc);

		menuBar.add(nuovo);

		// Menu 2
		JMenu pagelle = new JMenu("Pagella");
		JMenuItem bPagS = new JMenuItem("Strumento");
		JMenuItem bPagFor = new JMenuItem("Formazione");
		JMenuItem bPagLab = new JMenuItem("Laboratorio");
		JMenuItem bPagOr = new JMenuItem("Orchestra");
		JMenuItem bPagCoro = new JMenuItem("Coro");
		JMenuItem bPagAltro = new JMenuItem("Altro");
		JMenuItem bPagEsame = new JMenuItem("Esame");

		pagelle.add(bPagS);
		pagelle.add(bPagFor);
		pagelle.add(bPagLab);
		pagelle.add(bPagOr);
		pagelle.add(bPagCoro);
		pagelle.add(bPagAltro);
		pagelle.add(bPagEsame);
		menuBar.add(pagelle);

		// Menu 3
		JMenu stampa = new JMenu("Stampa");
		JMenuItem print = new JMenuItem("Singola Pagella");
		JMenuItem allPagelle = new JMenuItem("Tutte Pagelle");

		stampa.add(print);
		stampa.add(allPagelle);
		menuBar.add(stampa);

		// MENU 4
		JMenu modifica = new JMenu("Modifica");
		JMenuItem mstu = new JMenuItem("Modifica Studente");
		JMenuItem mDocente = new JMenuItem("Modifica Docente");
		JMenuItem mStrumento = new JMenuItem("Modifica Strumento");
		JMenuItem mLaboratorio = new JMenuItem("Modifica Laboratorio");
		JMenuItem mOrchestra = new JMenuItem("Modifica Orchestra");

		modifica.add(mstu);
		modifica.add(mDocente);
		modifica.add(mStrumento);
		modifica.add(mLaboratorio);
		modifica.add(mOrchestra);
		menuBar.add(modifica);

		// action listener
		bstu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameStudente.setStudente(null);
				frameStudente.draw();
			}
		});
		// action listener
		mstu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) {
					frameStudente.setStudente(null);
					frameStudente.setStudente(select);
					frameStudente.draw();

				}
			}
		});

		bstru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameStrumento.setStrumento(null);
				frameStrumento.draw();
			}
		});

		bdoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameDocente.setDocente(null);
				frameDocente.draw();
			}
		});

		borc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameOrchestra.setOrchestra(null);
				frameOrchestra.draw();
			}
		});

		blab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameLaboratorio.setLaboratorio(null);
				frameLaboratorio.draw();
			}
		});

		// pagelle
		bPagS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				logger.debug("selezionato: " + select);
				if (select != null) {
					framePagStrumento.setStudente(select);
					framePagStrumento.draw();
				}
			}
		});

		bPagFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) {
					framePagFormazione.setStudente(select);
					framePagFormazione.draw();
				}
			}
		});

		bPagOr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) {
					framePagOrchestra.setStudente(select);
					framePagOrchestra.draw();
				}
			}
		});

		bPagCoro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) {
					framePagCoro.setStudente(select);
					framePagCoro.draw();
				}
			}
		});
		bPagAltro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) {
					framePagAltro.setStudente(select);
					framePagAltro.draw();
				}
			}
		});

		bPagLab.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) {
					framePagLaboratorio.setStudente(select);
					framePagLaboratorio.draw();
				}
			}
		});

		// stampa
		allPagelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getAllPdf();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Studente select = getSelected(lista);
				if (select != null) try {
					Summary sum = buildSummary(select);
					pdf.createPDF(sum);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		mDocente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Docente> all = docenteDAO.findAll();
				if (all != null && !all.isEmpty()) {
					String[] docenti = new String[all.size()];
					int i = 0;
					for (Docente d : all) {
						docenti[i++] = d.getCognome() + " " + d.getNome();
					}

					String selectDocente = (String) JOptionPane
							.showInputDialog(Main.this, "Seleziona un docente",
									"Modifica Docente",
									JOptionPane.QUESTION_MESSAGE, null,
									docenti, docenti[0]);
					if (selectDocente != null) {
						Docente selected = docenteDAO.findByCognome(
								selectDocente.split(" ")[0]).get(0);
						frameDocente.setDocente(selected);
						frameDocente.draw();
					}
				}
			}
		});

		mStrumento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Strumento> all = corsiDAO.findAll(Strumento.class);
				if (all != null && !all.isEmpty()) {
					String[] strumenti = new String[all.size()];
					int i = 0;
					for (Strumento s : all) {
						strumenti[i++] = s.getNome();
					}

					String selected = (String) JOptionPane.showInputDialog(
							Main.this, "Seleziona uno Strumento",
							"Modifica Strumento", JOptionPane.QUESTION_MESSAGE,
							null, strumenti, strumenti[0]);
					if (selected != null) {
						Strumento select = corsiDAO.findByName(selected,
								Strumento.class).get(0);
						frameStrumento.setStrumento(select);
						frameStrumento.draw();
					}
				}
			}
		});

		mOrchestra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Orchestra> all = corsiDAO.findAll(Orchestra.class);
				if (all != null && !all.isEmpty()) {
					String[] list = new String[all.size()];
					int i = 0;
					for (Orchestra s : all) {
						list[i++] = s.getNome();
					}

					String selected = (String) JOptionPane.showInputDialog(
							Main.this, "Seleziona un'Orchestra",
							"Modifica Orchestra", JOptionPane.QUESTION_MESSAGE,
							null, list, list[0]);
					if (selected != null) {
						Orchestra select = corsiDAO.findByName(selected,
								Orchestra.class).get(0);
						frameOrchestra.setOrchestra(select);
						frameOrchestra.draw();
					}
				}
			}
		});

		mLaboratorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Laboratorio> all = corsiDAO.findAll(Laboratorio.class);
				if (all != null && !all.isEmpty()) {
					String[] list = new String[all.size()];
					int i = 0;
					for (Laboratorio s : all) {
						list[i++] = s.getNome();
					}

					String selected = (String) JOptionPane.showInputDialog(
							Main.this, "Seleziona un Laboratorio",
							"Modifica Laboratorio",
							JOptionPane.QUESTION_MESSAGE, null, list, list[0]);
					if (selected != null) {
						Laboratorio select = corsiDAO.findByName(selected,
								Laboratorio.class).get(0);
						frameLaboratorio.setLaboratorio(select);
						frameLaboratorio.draw();
					}
				}
			}
		});

		bPagEsame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Studente select = getSelected(lista);
				if (select != null) {
					frameEsame.setStudente(select);
					frameEsame.draw();
				}
			}
		});

		// add component
		setContentPane(pan);
		setJMenuBar(menuBar);

		// filter list
		lista = new FilteringJList();
		JScrollPane pane = new JScrollPane(lista);
		panList.add(pane, BorderLayout.CENTER);
		text = new JTextField();
		lista.installJTextField(text);
		panList.add(text, BorderLayout.NORTH);
		addElementToList();

		// add panel
		pan.add(panList, BorderLayout.CENTER);
		setGui();

	}

	protected Studente getSelected(JList lista2) {
		Studente select = (Studente) lista.getSelectedValue();
		if (select == null) {
			JOptionPane.showMessageDialog(null, "Seleziona uno studente");
			return null;
		} else return select;
	}

	private void addElementToList() {
		// Lista
		for (Studente studente : studenteDAO.findAll()) {
			lista.addElement(studente);
		}
	}

	private void setGui() {
		setVisible(true);
		setSize(400, 400);
		setTitle("OPERAPRIMA - Gestione Pagelle");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void updateList() {
		lista.setNewModel();
		lista.installJTextField(text);
		addElementToList();
	}

	public static void main(String[] args) {
		System.out.println("here1");
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"META-INF/applicationContext.xml");
		System.out.println("here2");
		Main bean = context.getBean(Main.class);
		System.out.println("here3");
		bean.init();

	}

	public Summary buildSummary(Studente studente) {
		Summary sum = new Summary();
		sum.setStudente(studente);
		sum.setPs(retrievePagella(PagellaStrumento.class, studente));
		sum.setPf(retrievePagella(PagellaFormazione.class, studente));
		sum.setPl(retrievePagella(PagellaLaboratorio.class, studente));
		sum.setPo(retrievePagella(PagellaOrchestra.class, studente));
		sum.setPc(retrievePagella(PagellaCoro.class, studente));
		sum.setPa(retrievePagella(PagellaAltro.class, studente));
		sum.setEsame(retrievePagella(Esame.class, studente));
		return sum;
	}

	private <T> T retrievePagella(Class<T> c, Studente s) {
		return pagellaDAO.findByStudente(s, c);
	}

	private ArrayList<Summary> getAllSummary() {
		ArrayList<Summary> lista = new ArrayList<Summary>();

		for (Studente s : studenteDAO.findAll()) {
			lista.add(buildSummary(s));
		}
		return lista;
	}

	public String getAllPdf() throws DocumentException, SQLException,
			MalformedURLException, IOException,
			com.itextpdf.text.DocumentException {
		ArrayList<Summary> lista = getAllSummary();
		for (int i = 0; i < lista.size(); i++) {
			pdf.createPDF(lista.get(i));
		}
		return null;
	}

	public void updateEventOccurred(Object o) {
		updateList();
	}

}
