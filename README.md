[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/teLsEufN)

# Obligatorisk Oppgave 3 i DATS2300 - Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i DATS2300 - Algoritmer og datastrukturer. Den er innlevert av følgende studenter:
* 385516, hakau9101@oslomet.no

## Oppgavebeskrivelser

# Oppgave 1
I oppgave 1 sjekket jeg først om den innsendte verdien var ugyldig og kastet en passende Exception hvis ja. 
Deretter initialiserte jeg en peker p og en forelderpeker q til null. En while-løkke ble brukt for å finne 
riktig posisjon ved å sammenligne verdien med nodene ved hjelp av en komparator. Avhengig av resultatet (mindre/større)
flyttet jeg p til venstre eller høyre.
Når riktig plass var funnet (p==null), opprettet jeg en ny node og koblet den til foreldernoden q.
Hvis treet var tomt, ble nye noden roten, ellers som venstre eller høyre barn av q. Til slutt oppdaterte jeg antall noder 
og endringer, og returnerte true for en vellykket innlegging.

# Oppgave 2
For å komme frem til metoden, startet jeg med å håndtere ugyldige input ved å returnere 0 dersom verdien er null. 
Deretter initialiserte jeg en peker p til rotnoden og en teller antallVerdi til 0 for å holde styr på antall forekomster. 
Jeg brukte en while-løkke for å traversere treet, der verdien ble sammenlignet med nodenes verdier ved hjelp av en komparator. 
Hvis verdien var mindre enn nodens verdi, flyttet jeg til venstre; hvis den var lik, økte jeg telleren og flyttet til høyre. 
Til sist returnerte jeg telleren

# Oppgave 3
For metoden førstePostorden: Jeg startet med å traversere så langt ned i treet som mulig, først til venstrebarn. Hvis 
venstre barn ikke fantes, gikk jeg til høyre. Dette sikrer at vi til slutt når en løvnode, som er den første i postorden. 
Når jeg nådde en node uten barn, returnerte jeg den som resultat.
nestePostorden: For å finne neste node i postorden, begynte jeg med å sjekke om p er venstre barn. Hvis ja, og forelderen
har et høyre barn, returneres den første noden i det høyre undertreet. Hvis p er høyre barn, eller det ikke finnes et 
høyre barn, returneres forelderen. Hvis p er rotnoden, returneres null fordi det ikke er noen noder igjen i postorden.


# Oppgave 4
I oppgave 4 skulle jeg implementere postorden-traversering av et binært tre, både iterativt og rekursivt.
Iterativ: Jeg fant først den første noden i postorden ved å kalle førstePostorden(rot) (gir startnode for traverseringen).
Deretter opprettet jeg en while-løkke som fortsetter så lenge det finnes noder igjen å besøke. Her kalte jeg 
utførOppgave(p.verdi) for å utføre oppgaven på verdien av den nåværende noden. Til slutt oppdaterte 
jeg p ved å bruke nestePostorden(p) for å navigere til den neste noden i postorden.
Rekursiv: Jeg sjekket først om det er et venstrebarn. Hvis ja, kalte jeg metoden rekursivt med venstrebarnet som parameter.
Deretter sjekket jeg for høyrebarnet på samme måte. Etter å ha besøkt begge barna, ble oppgaven utført på den nåværende noden.

# Oppgave 5
For metoden fjern(T verdi) sjekket jeg først om den angitte indeksen var gyldig.
Jeg starter med å lete etter noden som skal fjernes, ved å sammenligne verdien med hver node ved hjelp av en komparator. 
Hvis verdien er mindre, går jeg til venstre, og hvis den er større, går jeg til høyre. Hvis noden ikke finnes, returneres false.

Deretter håndterte jeg de spesifikke tilfellene der en node kan fjernes:
Noden har ingen eller ett barn: Setter en referanse til dette barnet, eller null hvis ingen barn finnes. Jeg kobler så
om treet slik at forelderen til noden (q) peker på barnenoden, eller oppdaterer rotnoden hvis noden er roten.
Noden har to barn: Finner den minste noden i høyre undertre (neste i inorden). Jeg kopierer verdien fra denne noden til 
noden som skal fjernes, og fjerner deretter denne minste noden fra treet ved å koble om dens forelder til å peke på dens høyrebarn.
Til slutt oppdaterer jeg telleren for antall noder og antall endringer i treet, samt true.

fjernAlle(T verdi): Metoden benytter den eksisterende fjern(T verdi)-metoden, som fjerner én verdi om gangen. Den er brukt
i while-løkken som kalles så lenge metoden returnerer true. En teller holder oversikt over antall ganger verdien fjernes, 
og oppdateres for hver vellykket fjerning. 

Metoden nullstill() tilbakestiller det binære søketreet ved å først sjekke om treet er tomt; hvis ikke, kaller rekursive 
metoden nullstill(Node<T> p) med roten som argument. Denne metoden navigerer gjennom hele treet, og nullstiller pekere 
til venstre og høyre subtrær, samt verdien og forelderpeker i hver node. Når alle noder er nullstilt, setter nullstill()
roten til null og antall noder til 0, som indikerer at treet er helt tømt.