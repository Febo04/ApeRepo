# 🎲 ApeGame - Il Gioco dei Dadi

## 📜 Regole del Gioco

- **Partecipanti**: Minimo 2 bot per partita
- **Numero di round**: 1000 (configurabile)
- **Azioni disponibili**: 
  - **DICHIARA**: Lancia i dadi, vedi il risultato e dichiara un valore
  - **CONTROLLA**: Sfida la dichiarazione del bot precedente verificando il valore reale dei dadi
- **Sistema di punteggio**: 
  - Penalità per dichiarazioni errate
  - Penalità per controlli sbagliati
  - Il bot con meno penalità vince il round!
- **Regole Speciali**
  - -Se si fa un giro completo di declare 21 tutti i giocatori perdono.
- **Vincitore**: Il bot con il **punteggio totale più basso** dopo tutti i round è il campione! 🏆

## 🏗️ Struttura del Progetto

```
ApeGame/
├── src/ApeBot/
│   ├── ApeGame.java          # Entry point - avvia la simulazione
│   ├── Game.java             # Logica del gioco
│   ├── Bot.java              # Classe base astratta per i bot
│   ├── HonestBot.java        # Bot onesto - dichiara sempre il vero
│   ├── RandomBot.java        # Bot casuale - scelte random
│   ├── ActionType.java       # Enum per i tipi di azione
│   ├── PublicAction.java     # Log pubblico del gioco
│   ├── GameInfo.java         # Stato del gioco visibile ai bot
│   ├── DiceUtils.java        # Utility per i dadi
│   └── module-info.java      # Descriptor del modulo Java
└── bin/                       # Bytecode compilato
```

## 🤖 Bot Disponibili

### HonestBot
**Strategia**: Sempre sincero e leale
- ✅ Dichiara sempre il valore vero
- ✅ Sceglie sempre di DICHIARA
- ✅ Guarda sempre i dadi
- ✅ Sempre disponibile per i reroll

### RandomBot
**Strategia**: Imprevedibile e audace
- 🎲 Decide casualmente tra DICHIARA e CONTROLLA
- 🎲 Sceglie random se lanciare di nuovo
- 🎲 Random nel guardare i dadi
- 🎲 Dichiara numeri casuali

## 🚀 Come Iniziare

### Prerequisiti
- Java 11 o superiore
- Eclipse IDE (opzionale)

### Compilazione

**Con Eclipse:**
1. Importa il progetto in Eclipse
2. Build automatico (Project → Build Project)

**Da linea di comando:**
```bash
cd c:\Users\feboc\eclipse-workspace\ApeGame
javac -d bin src/ApeBot/*.java
```

### Esecuzione della Simulazione

**Con Eclipse:**
1. Tasto destro su `ApeGame.java`
2. Run As → Java Application

**Da linea di comando:**
```bash
cd bin
java ApeBot.ApeGame
```

## 📊 Output della Simulazione

La simulazione mostra:
1. **Log di gioco**: Tutte le azioni round per round
2. **Classifica finale**: I punteggi di penalità di ogni bot
3. **Statistiche**: Numero di controlli effettuati e loro accuratezza

Esempio:
```
Lanciamo 10 round tra 2 bot...
1: Onesto dichiara 5
2: Randomino controlla e vince
3: Onesto dichiara 3
...

Risultati (penalità = numero di round persi):
Onesto -> 2
Randomino -> 8
```

## 🎨 Come Creare un Tuo Bot

### Passo 1: Crea una nuova classe
```java
public class MioBot extends Bot {
    public MioBot(String name) { 
        super(name); 
    }
    
    @Override
    public ActionType chooseAction() {
        // La tua strategia qui
        return ActionType.DECLARE;
    }
    
    @Override
    public boolean wantsReroll() {
        // Vuoi rilancia?
        return true;
    }
    
    @Override
    public boolean wantsSeeDice() {
        // Vuoi vedere i dadi?
        return true;
    }
    
    @Override
    public int declared() {
        // Che numero dichiari?
        return actual;
    }
}
```

### Passo 2: Aggiungi il bot alla simulazione
In `ApeGame.java`, aggiungi il tuo bot alla lista:
```java
bots.add(new MioBot("Nome del Mio Bot"));
```

### Passo 3: Personalizza il gioco
In `ApeGame.java` puoi modificare:
- `rounds`: numero di round da giocare
- Aggiungere/rimuovere bot dalla lista
- Cambiare i nomi dei bot

## 🎯 Classi Principali

- **Bot**: Classe base astratta che definisce l'interfaccia del bot
- **Game**: Gestisce lo stato del gioco, i round, i punteggi e il log
- **GameInfo**: Fornisce ai bot le informazioni visibili dello stato del gioco
- **PublicAction**: Registra le azioni pubbliche per il log e il replay
- **ActionType**: Enum per i tipi di azione disponibili

## 🔧 Pattern di Design

- **Strategy Pattern**: Ogni bot implementa una strategia diversa
- **Template Method**: La classe `Bot` astratta definisce la struttura
- **Facade**: La classe `Game` fornisce un'interfaccia semplice

Divertiti a creare il tuo bot campione! 🏆
