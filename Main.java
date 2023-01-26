package tp_sys;

import java.util.Scanner;
import java.util.ArrayList;
public class Main {
	public static void main(String[] args) {
		
	    /* ########## partie declaration ########## */
	    ArrayList<Processus> processus = new ArrayList<>();
	    int nombre_processus,start_time = 0, complete = 0 , vide = 0;
	    ArrayList<Integer> SJF_sans_preemption = new ArrayList<>();
	    /* ########## stocker les processus ########## */
	    Scanner input = new Scanner(System.in);
	    System.out.print("Combien voulez vous ordonnancer de processus ? :  ");
	    nombre_processus = input.nextInt();
	    //remplir arraylist, mettre les processus avec leurs ID,date d'arrivee et temps d'execution....
	    for(int i=0 ; i < nombre_processus ; i++)
        {
            System.out.print ("entrer la date d'arrive du processus  " + (i+1) + " : ");
            int  date_arr = input.nextInt();
            System.out.print ("entrer le temps d'execution du processus " + (i+1) + " : ");
            int temps_exec = input.nextInt();
            processus.add(new Processus (i+1, date_arr, temps_exec , false));
        }
        input.close();
        /* -------------------- Fin ---------------------- */
        /* ########## Ordonnancement SJF sans preemption ########## */
        while(complete < nombre_processus)
        {
            //remplir "index_SJF" par les indices de processus qui arrivent et n'a pas executés
            ArrayList<Integer> index_SJF = new ArrayList<>();
            for (int i=0; i<nombre_processus; i++)
            {
                if ((processus.get(i).Date_arrivee <= start_time) && (!processus.get(i).statut_termine) )
                    index_SJF.add(i);
            }
            //si aucune processus arrivee 
            if (index_SJF.isEmpty()){
                start_time++;
                vide++;
            }else
	            {
	                //cherche a le temps d'execution le plus petit
	                int min = processus.get(index_SJF.get(0)).temps_execution;
	                int index_min = index_SJF.get(0);
	                for (int i=1; i < index_SJF.size(); i++)
	                {
	                    if (min > processus.get(index_SJF.get(i)).temps_execution)
	                    {
	                        min = processus.get(index_SJF.get(i)).temps_execution;
	                        index_min=index_SJF.get(i);
	                        
	                    }
	                }
	                processus.get(index_min).Date_DebutExecution = start_time;
	                processus.get(index_min).Date_finExecution = start_time + processus.get(index_min).temps_execution;
	                start_time += processus.get(index_min).temps_execution;
	                processus.get(index_min).statut_termine = true; // processus terminee
	                processus.get(index_min).SJF = complete+1;
	                processus.get(index_min).temps_reponse = processus.get(index_min).Date_finExecution - processus.get(index_min).Date_arrivee;
	                processus.get(index_min).temps_attente = processus.get(index_min).temps_reponse - processus.get(index_min).temps_execution;
	                SJF_sans_preemption.add(index_min+1);
	                complete++;
	            }   
        }  
            
        //affichage du tableau de processus avec ses detailes et ordonnancement JSF sans preemption
        System.out.println("\n---------------------------------\nOrdonnancement SJF sans preemption :\n---------------------------------\n");
        System.out.println("\nprocessus\tDate d'arrivee\t\t\ttemps d'execution\t     priorité\t    date debut d'execution\t    date fin d'execution\t    temps de reponse\t    temps d'attente");
        for(int i=0;i<nombre_processus;i++)
            System.out.println("P"+processus.get(i).ID+"\t\t\t"+processus.get(i).Date_arrivee+"\t\t\t\t"+processus.get(i).temps_execution+"\t\t\t"+processus.get(i).SJF+"\t\t\t"+processus.get(i).Date_DebutExecution+
            		"\t\t\t"+processus.get(i).Date_finExecution+"\t\t\t"+processus.get(i).temps_reponse+"\t\t\t"+processus.get(i).temps_attente);
        
        System.out.print("\nOrdonnancement SJF sans preemption comme suit :  ");
        for(int i=0;i<SJF_sans_preemption.size();i++)
            System.out.print("P"+SJF_sans_preemption.get(i)+"\t");
        /* -------------------- Fin ---------------------- */
        
        
        /* ########## Ordonnancement SJF avec preemption ########## */
        
        //reintialiser les variables et le statut_termine de processus
        start_time=0;complete=0;vide=0;
        for(int i=0;i<nombre_processus;i++)
            processus.get(i).statut_termine = false;
        
        //cree une arrayliste pour ordonner notre processus
        ArrayList<Integer> SJF_avec_preemption = new ArrayList<>();
        ArrayList<Integer> preemption = new ArrayList<>();
        
        //cree & remplir une arrayliste par les temps_execution de processus
        ArrayList<Integer> SJF_avec_preemption_execution = new ArrayList<>();
        for(int i=0;i<nombre_processus;i++)
        	SJF_avec_preemption_execution.add(processus.get(i).temps_execution);
                
        SJF_avec_preemption.add(-1);
        while(complete < nombre_processus) 
        {
        	//remplir "index_SJF" par les indices de processus qui arrivent et n'a pas terminés leur temps d'execution
        	ArrayList<Integer> index_SJF = new ArrayList<>();
	        for (int i=0; i<nombre_processus; i++)
	        {
	        	  if ((processus.get(i).Date_arrivee <= start_time) && (!processus.get(i).statut_termine) )
		              	index_SJF.add(i);
	        }
	        //si aucune processus arrivee ---> plus un a star time  ou  ordonner les processus
	        if (index_SJF.isEmpty()){
	        	if(SJF_avec_preemption.get(SJF_avec_preemption.size() - 1) != 0) {
	        		SJF_avec_preemption.add(0);
	        		preemption.add(start_time);
	        	}   
	        	start_time++;
                vide++;
            }   
	        else
	        {
	        	//cherches a le temps d'execution petit
	        	int min = processus.get(index_SJF.get(0)).temps_execution;
	        	int index_min = index_SJF.get(0);
	        	for (int i=1; i < index_SJF.size(); i++)
	        	{
	        		if ( (min > processus.get(index_SJF.get(i)).temps_execution) )
	        		{
		              	min = processus.get(index_SJF.get(i)).temps_execution;
		              	index_min=index_SJF.get(i);
		              	
	        		}
	        	}
	        	//index ---> SJF avec preemption
	        	if ((index_min+1) != SJF_avec_preemption.get(SJF_avec_preemption.size() - 1))
	        	{
	        		SJF_avec_preemption.add(index_min+1);
	        		preemption.add(start_time);
	        	}
	        	
	        	if(SJF_avec_preemption_execution.get(index_min) == processus.get(index_min).temps_execution) 
	        	{
	        		processus.get(index_min).Date_DebutExecution = start_time;
	        	}
	        	
	        	SJF_avec_preemption_execution.set(index_min, SJF_avec_preemption_execution.get(index_min)-1);
	        	start_time++;
	        	
	        	if(SJF_avec_preemption_execution.get(index_min) == 0) 
	        	{
	        		processus.get(index_min).Date_finExecution = start_time;
	        		processus.get(index_min).statut_termine = true;
	        		processus.get(index_min).temps_reponse = processus.get(index_min).Date_finExecution - processus.get(index_min).Date_arrivee;
	        		processus.get(index_min).temps_attente = processus.get(index_min).temps_reponse - processus.get(index_min).temps_execution;
	        		complete++;
	        	}
	        }
        }
        int Max = 0;
        for (int i = 0; i < processus.size();i++)
        {
        	Max = Max + processus.get(i).temps_execution;
        }
        preemption.add(Max+vide);
        //affichage du tableau de processus avec ses detailes et ordonnancement JSF avec preemption
        System.out.println("\n\n---------------------------------\nOrdonnancement SJF avec preemption :\n---------------------------------\n");
        System.out.println("\nprocessus\tDate d'arrivee\t\t\ttemps d'execution\t    date debut d'execution\t    date fin d'execution\t    temps de reponse\t    temps d'attente");
        for(int i=0;i<nombre_processus;i++)
            System.out.println("P"+processus.get(i).ID+"\t\t\t"+processus.get(i).Date_arrivee+"\t\t\t\t"+processus.get(i).temps_execution+"\t\t\t\t"+processus.get(i).Date_DebutExecution+"\t\t\t"+processus.get(i).Date_finExecution
            		+"\t\t\t"+processus.get(i).temps_reponse+"\t\t\t"+processus.get(i).temps_attente);
        //methode 1 d'affichage
        System.out.println("\nOrdonnancement SJF avec preemption comme suit :  ");
        for(int i=1;i<SJF_avec_preemption.size();i++){
        	if (SJF_avec_preemption.get(i) == 0) {
        		System.out.println(preemption.get(i-1)+"\tVIIIDE!!!\t"+preemption.get(i));
        	}else
        		System.out.println(preemption.get(i-1)+"\tP"+SJF_avec_preemption.get(i)+"\t\t"+preemption.get(i));
        }
        //methode 2 d'affichage
        System.out.println("\nOrdonnancement SJF avec preemption comme suit :  ");
        System.out.print("0");
        for(int i=1;i<SJF_avec_preemption.size();i++){
        	if (SJF_avec_preemption.get(i) == 0) {
        		System.out.print("\tVIIIDE!!!\t"+preemption.get(i));
        	}else
        		System.out.print("\tP"+SJF_avec_preemption.get(i)+"\t"+preemption.get(i));
        }
        //calculer le temps de reponse et attente total
        int Temps_Reponse_Total = 0 , Temps_Attente_Total = 0;
        for(int i=0;i<processus.size();i++) {
        	Temps_Reponse_Total = Temps_Reponse_Total + processus.get(i).temps_reponse;
        	Temps_Attente_Total = Temps_Attente_Total + processus.get(i).temps_attente;
        }
        //calculer le temps de reponse et attente moyen
        float Temps_Reponse_Moyen = 0.0f , Temps_Attente_Moyen = 0.0f;
        Temps_Reponse_Moyen = Temps_Reponse_Total / nombre_processus;
        Temps_Attente_Moyen = Temps_Attente_Total / nombre_processus;
        System.out.println("\nle temps de reponse moyen egale a : "+ Temps_Reponse_Moyen);
        System.out.println("le temps d'attente moyen egale a : "+ Temps_Attente_Moyen);
        
        /* -------------------- Fin ---------------------- */         
	}
}