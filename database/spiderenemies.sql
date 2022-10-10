-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Set 08, 2022 alle 15:54
-- Versione del server: 10.4.24-MariaDB
-- Versione PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `spiderenemies`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `chatbot`
--

CREATE TABLE `chatbot` (
  `id` int(3) NOT NULL,
  `comando` varchar(10) NOT NULL,
  `testo` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `chatbot`
--

INSERT INTO `chatbot` (`id`, `comando`, `testo`) VALUES
(1, 'help', 'Scegli un livello. Ogni livello ha un ambientazione diversa e 3 nemici normali che variano per ogni livello. Per ogni nemico colpito, guadagnerai 150 punti, se il nemico è un nemico bonus guadagnerai 200 punti, invece, nel caso sia un alleato, perderai 150 punti. Inoltre, ogni volta che aumenterai il tuo punteggio di 3000 punti, verrà aggiunto un secondo al tempo corrente.'),
(2, 'game', 'Il gioco permette di guadagnare dei coins chiamati \"Spider-Coins\", essi possono essere utilizzati per acquistare dei cursori, una skin o abilitare la modalità giorno nel livello bonus.'),
(3, 'info', 'Gioco creato da Gianfranco Iaria, per l\'esame di programmazione 2 e ingegneria del software.');

-- --------------------------------------------------------

--
-- Struttura della tabella `scores`
--

CREATE TABLE `scores` (
  `ID` int(11) NOT NULL,
  `name` varchar(20) NOT NULL,
  `points` int(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `scores`
--

INSERT INTO `scores` (`ID`, `name`, `points`) VALUES
(1, 'Test1', 0),
(2, 'Test2', 0),
(3, 'gianfranco', 18800),
(4, 'anto_83', 17400),
(5, 'mike123', 19600),
(6, 'spider13', 19500),
(7, 'game47', 21000),
(8, 'Test', 1000);

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `chatbot`
--
ALTER TABLE `chatbot`
  ADD PRIMARY KEY (`id`);

--
-- Indici per le tabelle `scores`
--
ALTER TABLE `scores`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `chatbot`
--
ALTER TABLE `chatbot`
  MODIFY `id` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `scores`
--
ALTER TABLE `scores`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
