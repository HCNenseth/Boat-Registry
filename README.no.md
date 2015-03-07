# Boat Registry
Skole oppgave Mars 2015

# Dialogbokser
I skrivende stund er nettopp Java 8u40 sluppet og en av de merkbare nye endringene er et offisielt
dialog API. Det hadde vært interessant å utforske dette videre, men siden jeg ikke ønsker å binde
denne applikasjonen opp mot en så ny JDK falt valget på å lage noen egenkomponerte dialogbokser.
Det gjør nytten sin for nå. Les mer om dette [her](http://www.oracle.com/technetwork/java/javase/8u40-relnotes-2389089.html)

# Intellij IDEA
Det er ingen hemmelighet at denne applikasjonen er utviklet med Intellij og SceneBuilder. Kompilering
av prosjektet er derfor minst smertefult gjennom dette IDE miljøet.

# Testing
Mange av classemetodene har testkode skrevet med JUnit 4.12. Dette for av åpenbare grunner.

# Kjøring
Som nevnt over kan kompilering av applikasjonen utenfor IDEA miljøet by på utfordinger. Det er derfor
en jar fil vedlagt. Denne kan kjøres som normal med "java -jar BoatRegistry.jar", eller med fil som
"java -jar BoatRegistry.jar demo.dat"

# JDK
Denne applikasjonen krever JDK > 1.8 grunnet bruk av lambda setninger og JavaFX.
