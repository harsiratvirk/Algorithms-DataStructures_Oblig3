package no.oslomet.cs.algdat;

@FunctionalInterface
public interface Oppgave<T> {
    void utførOppgave(T t);
}