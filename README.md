# Rekursjon

Imperativ (løkke-) versjon er ofte mer effektiv, rekursjon er ofte lettere å forstå. 
For å bruke rekursjon må vi lete etter en del av jobben som likner på hele jobben, men er mindre.

### Grunnregler
1. Det rekursive kallet må være mindre enn det opprinnelige oppdraget som ble gitt.
(i.e se rekursiv versjon av Fibonacci)
2. Alle rekursive metoder må ha en "basecase" - en situasjon så enkel at den kan bli besvart uten rekursjon.


### Indirekte rekursjon
Det er også rekursjon dersom vi har ein samling av metodar som
kallar kvarandre «sirkulært»
a kallar b kallar c kallar a

    public void a(){ ... b(); ... }
    public void b(){ ... c(); ... }
    public void c(){ ... a(); ... }
    
Dette er ikkje så vanleg. Og slett ikkje med meire enn to metodar i «sirkelen».
Det er tilstrekkeleg at ein av metodane har basistilfelle, det bryt sirkelen.
