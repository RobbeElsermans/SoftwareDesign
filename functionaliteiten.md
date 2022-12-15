# overview

## Database

> We hebben een persoon die een
* ID
* Naam
* Achternaam heeft.

> We hebben een ticket dat een
* ID
* Prijs
* De betaler
* De verbruiker
* Mogelijks extra uitleg

## GUI

* persoon toevoegen
* persoon verwijderen en schulden droppen/ doorgeven
* uniform ticket toevoegen met personen
* variabel ticket toevoegen met personen
* bereken al de kosten


## Functionaliteit

* bereken de totale kost voor elke groep personen (BEGIN MET 2 PERSONEN MAX)
	* Bereken de totale schuld tussen ieder
	* Vergelijk schulden tussen ieder en betaal (indien mogelijk) af
	vb. 	Tom->Robbe = 20, Robbe->Tom = 10 dus dan moet Tom 10 betalen aan Robbe.
	vb.	Tom->Robbe = 10, Robbe->Fernando = 20, Fernando->Tom = 5
		Fernando geeft shulden van Tom aan robbe
		Robbe->Fernando = 15 & Robbe->Tom = 5
	ZIE WHATSAPP


## design patterns
* OK Singleton:	Database en controller van database
* OK Observer:	Alert tonen wanneer de database geupdated wordt
* OK Factory:	De tickets (airplane, resto, vervoer, ...)
* OK Abstract Factory: Het soort ticket (normale verdeling, of specifieke verdeling)
* OK MVC:		GUI

* Proxy: Wanneer we een ticket aanmaken, deze sturen naar de proxy zodat de proxy de data verder afhandeld 
* OK Startegy:	De basis van ticketen (parent->Ticket, childs->restoTicket, AirplaneTicket, ...)
* Facade:	De eindberekening afzonderne van het programma (Black box)
* Iterator: 	Misschien voor tickets?
* OK Template Method:	Database abstractie
* Composite:	Berekenen van de schulden
