package no.oslomet.cs.algdat;

import java.util.*;

public class SøkeBinærTre<T>  implements Beholder<T> {

    // En del kode er ferdig implementert, hopp til linje 91 for Oppgave 1

    private static final class Node<T> { // En indre nodeklasse
        private T verdi; // Nodens verdi
        private Node<T> venstre, høyre, forelder; // barn og forelder

        // Konstruktører
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> f) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            forelder = f;
        }
        private Node(T verdi, Node<T> f) {
            this(verdi, null, null, f);
        }

        @Override
        public String toString() {return verdi.toString();}
    }

    private final class SBTIterator implements Iterator<T> {
        Node<T> neste;
        public SBTIterator() {
            neste = førstePostorden(rot);
        }

        public boolean hasNext() {
            return (neste != null);
        }

        public T next() {
            Node<T> denne = neste;
            neste = nestePostorden(denne);
            return denne.verdi;
        }
    }

    public Iterator<T> iterator() {
        return new SBTIterator();
    }

    private Node<T> rot;
    private int antall;
    private int endringer;

    private final Comparator<? super T> comp;

    public SøkeBinærTre(Comparator<? super T> c) {
        rot = null; antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;
        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }
        return false;
    }

    public int antall() { return antall; }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot);
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() { return antall == 0; }

    // Oppgave 1
    public boolean leggInn(T verdi) {
        // Sjekker om verdien er gyldig
        if (verdi == null) throw new NullPointerException("Ulovlig nullverdi!");

        Node<T> p = rot; // peker, starter i roten
        Node<T> q = null; // refererer til forelderen til p
        int cmp = 0; // hjelpevariabel
        // Søker riktig posisjon (til p er ute av treet)
        while (p != null) {
            q = p; // forelder til p
            cmp = comp.compare(verdi,p.verdi); // Sammenligner verdi med eksisterende noder
            p = cmp < 0 ? p.venstre : p.høyre; // mindre enn; venstre, større enn; høyre
        }
        // Riktig posisjon funnet (p==null)
        p = new Node<>(verdi, q); // Oppretter ny node, q er forelder
        // Tomt tre, p blir rotnode
        if (q == null)
            rot = p;
        // Mindre enn forelder, p venstrebarn
        else if (cmp < 0)
            q.venstre = p;
        else
            // Større enn forelder: p høyrebarn
            q.høyre = p;

        endringer++;
        antall++;
        return true; // Vellykket innsetting
    }

    // Oppgave 2. Antall duplikater
    public int antall(T verdi){
        if (verdi == null) return 0; // Ugyldig verdi, ingen forekomster av null

        Node<T> p = rot;
        int antallVerdi = 0;
        // Så lenge vi har en node å besøke
        while (p != null) {
            int cmp = comp.compare(verdi,p.verdi); // Sammenligner verdi med eksisterende noder
            // verdi er mindre enn nodens verdi, flytter til venstrebarn
            if (cmp < 0)
                p = p.venstre;
            else {
                // Verdi funnet. Flytter til høyrebarn (siden flere forekomster ligger her)
                if (cmp == 0)
                    antallVerdi++; // øke teller
                p = p.høyre;
            }
        }
        return antallVerdi;
    }

    // Oppgave 3
    private Node<T> førstePostorden(Node<T> p) {
        while (true) {
            // Hvis det finnes venstrebarn, gå dit
            if (p.venstre != null)
                p = p.venstre;
                // Hvis ikke, gå til høyre
            else if (p.høyre != null)
                p = p.høyre;
            else return p;
        }
    }

    private Node<T> nestePostorden(Node<T> p) {
        Node<T> forelder = p.forelder;
        // p er rotnoden, finnes ingen neste node i postorden
        if (forelder == null) {
            return null;
        }
        // p er venstrebarn, sjekker om det finnes et høyrebarn
        if (p == forelder.venstre && forelder.høyre != null) {
            return førstePostorden(forelder.høyre); // Returner første node i høyre undertre
        }
        // p er høyrebarn eller ikke finnes et høyrebarn, returner forelder til p
        return forelder;
    }

    // Oppgave 4
    public void postOrden(Oppgave<? super T> oppgave) {
        // Første noden i postorden
        Node<T> p = førstePostorden(rot);
        // Så lenge vi har en node å besøke
        while (p != null) {
            oppgave.utførOppgave(p.verdi); // utfører oppgave på nåværende node
            p = nestePostorden(p); // neste node i postorden
        }
    }

    public void postOrdenRekursiv(Oppgave<? super T> oppgave) {
        postOrdenRekursiv(rot, oppgave); // Ferdig implementert
    }

    private void postOrdenRekursiv(Node<T> p, Oppgave<? super T> oppgave) {
        // Hvis venstrebarn, metoden kaller seg selv med venstrebarn som parameter
        if (p.venstre != null)
            postOrdenRekursiv(p.venstre,oppgave);
        // Hvis høyrebarn, metoden kaller seg selv med høyrebarn som parameter
        if (p.høyre != null)
            postOrdenRekursiv(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    // Oppgave 5
    public boolean fjern(T verdi) {
        if (verdi == null) return false; // Sjekker om angitt verdi er ugyldig

        // Søker etter noden
        Node<T> p = rot;
        Node<T> q = null; // forelder til p
        while (p != null) {
            int cmp = comp.compare(verdi,p.verdi); // sammenligner
            if (cmp < 0) { // til venstre
                q = p;
                p = p.venstre;
            } else if (cmp > 0) { // til høyre
                q = p;
                p = p.høyre;
            } else break; // søkte verdien ligger i p
        }
        // Verdien finnes ikke
        if (p == null) return false;

        // Fjerner noden
        // Tilfelle 1) p har ingen barn (en bladnode) og 2) p har ett barn (venstre/høyre barn)
        if (p.venstre == null || p.høyre == null) {
            // b blir satt til å være referansen til enten venstrebarnet, høyrebarnet eller null
            Node<T> b = p.venstre != null ? p.venstre : p.høyre;
            // Hvis p er roten, erstattes den med b (nodens venstre,høyrebarn ellr null)
            if (p == rot)
                rot = b; // b blir nye roten
            // p er venstrebarn, forelder q peker på nodens barn b istedenfor p
            else if (p == q.venstre)
                q.venstre = b;
            // p er høyrebarn, q til å peke på b
            else q.høyre = b;

            if (b != null) b.forelder = q; // Oppdaterer forelder
        }

        // Tilfelle 3) p har to barn
        else {
            Node<T> s = p;
            Node<T> r = p.høyre; // neste i inorden, minste node i høyre undertre
            while (r.venstre != null) {
                s = r; // s er forelder til r (minste noden)
                r = r.venstre;
            }
            p.verdi = r.verdi; // p blir erstattet med verdien i minste noden
            // Hvis s er lik p, fjerner r ved at s.venstre/s.høyre settes lik r.høyre
            if (s != p)
                s.venstre = r.høyre;
            else s.høyre = r.høyre;
        }
        endringer++;
        antall--;
        return true;
    }

    public int fjernAlle(T verdi) {
        int antallFjernet = 0; // Antall fjernede forekomster
        // Fortsett å fjerne så lenge fjern() returnerer true
        while (fjern(verdi)) {
            antallFjernet++; // Øk teller for hver gang en verdi fjernes
        }
        return antallFjernet;
    }

    public void nullstill() {
        if (!tom()) nullstill(rot); // nullstiller
        rot = null;
        antall = 0;
    }

    private void nullstill(Node<T> p) {
        if (p.venstre != null) {
            nullstill(p.venstre); // venstre subtre
            p.venstre = null; // nuller peker
        }
        if (p.høyre != null) {
            nullstill(p.høyre); // høyre subtre
            p.høyre = null; // nuller peker
        }
        p.verdi = null; // nuller verdien
        p.forelder = null;
    }
}