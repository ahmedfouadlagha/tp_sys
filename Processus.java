package tp_sys;

public class Processus {
	final int ID;
	int Date_arrivee;
	int temps_execution;
	int Date_DebutExecution;
	int Date_finExecution;
	boolean statut_termine;
	int temps_reponse;
	int temps_attente;
	int SJF;
	
	public Processus(int ID, int Date_arrivee, int Temps_execution, boolean Statut_termine) {
		super();
		this.ID = ID;
		this.Date_arrivee = Date_arrivee;
		this.temps_execution = Temps_execution;
		this.Date_finExecution = 0;
		this.statut_termine = Statut_termine;
		Date_DebutExecution = 0;
		SJF = 0;
	}
}



