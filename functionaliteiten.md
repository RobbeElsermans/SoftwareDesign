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

* OK persoon toevoegen
* OK persoon verwijderen en schulden droppen/ doorgeven
* OK uniform ticket toevoegen met personen
* OK variabel ticket toevoegen met personen
* OK bereken al de kosten
* OK verwijder al de tickets


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
* OK Singleton:		Database en controller van database
* OK Observer:		Alert tonen wanneer de database geupdated wordt
* OK Factory:		De tickets (airplane, resto, vervoer, ...)
* OK Abstract Factory: 	Het soort ticket (normale verdeling, of specifieke verdeling)
* OK MVC:		GUI

* OK Startegy:	De basis van ticketen (parent->Ticket, childs->restoTicket, AirplaneTicket, ...)
* OK Template Method:	Database abstractie
