-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : lun. 17 avr. 2023 à 19:24
-- Version du serveur : 10.4.21-MariaDB
-- Version de PHP : 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `projet_6`
--

-- --------------------------------------------------------

--
-- Structure de la table `banktransaction`
--

CREATE TABLE `banktransaction` (
  `id` int(11) NOT NULL,
  `amount` double DEFAULT NULL,
  `bank_account` varchar(255) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `banktransaction`
--

INSERT INTO `banktransaction` (`id`, `amount`, `bank_account`, `currency`, `date_time`, `user_id`) VALUES
(1, -100, 'JONDOE', 'EUR', '2023-04-03 15:13:50.017371', 4),
(2, -0.05, 'JONDOE', 'USD', '2023-04-03 15:14:56.871928', 4),
(3, 100, 'JONDOE', 'USD', '2023-04-03 15:15:03.134680', 4),
(4, -100, 'SOULREAPER', 'JPY', '2023-04-03 17:48:36.688835', 7),
(5, -22, 'SOULREAPER', 'USD', '2023-04-03 17:52:52.395728', 7),
(6, -22, 'SOULREAPER', 'USD', '2023-04-03 17:52:53.604997', 7),
(7, -2, 'SOULREAPER', 'USD', '2023-04-03 17:52:54.867745', 7),
(8, -2, 'SOULREAPER', 'USD', '2023-04-03 17:52:56.064708', 7),
(9, -0.07, 'JOSELUIS', 'USD', '2023-04-04 11:06:20.029904', 6),
(10, -2, 'SOULREAPER', 'USD', '2023-04-04 11:51:10.307552', 7),
(11, 500, 'SOULREAPER', 'USD', '2023-04-04 11:51:15.556434', 7),
(12, -200, 'SOULREAPER', 'JPY', '2023-04-04 13:19:40.306324', 7),
(20, 100, 'TESTEST', 'USD', '2023-04-04 14:50:04.917507', 14),
(21, -100, 'TESTEST', 'USD', '2023-04-04 17:07:32.999700', 14),
(22, 100, 'TESTEST', 'USD', '2023-04-04 17:07:33.041701', 14),
(23, -100, 'TESTEST', 'USD', '2023-04-04 18:03:34.237818', 14),
(24, 100, 'TESTEST', 'USD', '2023-04-04 18:03:34.288416', 14),
(25, -0.05, 'JOSELUIS', 'USD', '2023-04-05 08:54:46.795591', 6),
(26, 0.05, 'JOSELUIS', 'USD', '2023-04-05 08:54:51.299359', 6),
(27, -100, 'JOSELUIS', 'USD', '2023-04-05 09:05:26.601719', 6),
(28, 100, 'JOSELUIS', 'USD', '2023-04-05 09:05:39.396678', 6),
(29, -600, 'JOSELUIS', 'USD', '2023-04-05 09:05:57.599094', 6),
(30, 600, 'JOSELUIS', 'USD', '2023-04-05 09:06:03.843146', 6);

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

CREATE TABLE `roles` (
  `roles_id` int(11) NOT NULL,
  `role_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`roles_id`, `role_name`) VALUES
(1, 'USER'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Structure de la table `transaction`
--

CREATE TABLE `transaction` (
  `id` int(11) NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `date_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fees` decimal(38,2) DEFAULT NULL,
  `userdestination_id` int(11) NOT NULL,
  `usersource_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `transaction`
--

INSERT INTO `transaction` (`id`, `amount`, `currency`, `date_time`, `description`, `fees`, `userdestination_id`, `usersource_id`, `user_id`) VALUES
(1, '49.75', 'USD', '2023-03-31 16:01:28.503253', 'Lolo', '0.25', 3, 4, 4),
(2, '19.90', 'USD', '2023-03-31 16:05:27.435364', 'Lolo', '0.10', 3, 4, 4),
(3, '9.95', 'JPY', '2023-03-31 16:05:54.985229', 'Lolo', '0.05', 5, 4, 4),
(4, '49.75', 'USD', '2023-03-31 16:06:58.684380', 'Lolo', '0.25', 4, 6, 6),
(5, '9.95', 'USD', '2023-03-31 16:07:36.097082', 'Lolo', '0.05', 4, 6, 6),
(6, '49.75', 'USD', '2023-04-03 12:07:26.520337', 'Lolo', '0.25', 1, 6, NULL),
(7, '9.95', 'USD', '2023-04-03 12:53:10.197193', 'Lolo', '0.05', 5, 4, NULL),
(8, '19.90', 'USD', '2023-04-03 12:54:07.846360', 'Lolo', '0.10', 2, 4, NULL),
(9, '49.75', 'USD', '2023-04-03 12:54:18.652162', 'dddd', '0.25', 2, 4, NULL),
(10, '199.00', 'USD', '2023-04-03 16:48:47.033954', 'Remboursement Airbnb', '1.00', 3, 7, NULL),
(11, '398.00', 'USD', '2023-04-03 16:49:57.955297', 'Remboursement Vol Cuba', '2.00', 4, 7, NULL),
(12, '99.50', 'GBP', '2023-04-04 11:51:30.258637', 'Remboursement Airbnb', '0.50', 6, 7, NULL),
(13, '2.98', 'USD', '2023-04-04 12:02:15.268266', 'x', '0.02', 5, 7, NULL),
(14, '199.00', 'USD', '2023-04-04 12:02:19.626797', 'ssss', '1.00', 6, 7, NULL),
(15, '199.00', 'USD', '2023-04-04 12:02:24.559727', 'sss', '1.00', 5, 7, NULL),
(16, '199.00', 'USD', '2023-04-04 12:49:16.702001', 'Lolo', '1.00', 6, 7, NULL),
(17, '0.08', 'USD', '2023-04-04 13:18:05.218272', 'dd', '0.01', 4, 7, NULL),
(18, '199.00', 'USD', '2023-04-04 14:03:56.390854', 'Remboursement Airbnb', '1.00', 3, 9, NULL),
(19, '0.05', 'USD', '2023-04-05 08:54:40.177120', 'Remboursement Airbnb', '0.01', 4, 6, NULL),
(20, '19.90', 'JPY', '2023-04-05 09:04:32.290127', 'Remboursement Airbnb', '0.10', 1, 6, NULL),
(21, '99.50', 'USD', '2023-04-05 09:06:41.821608', 'Remboursement Vol Cuba', '0.50', 3, 6, NULL),
(22, '99.50', 'USD', '2023-04-05 09:35:35.719352', 'TEST ENVOI 100', '0.50', 6, 7, NULL),
(23, '199.00', 'USD', '2023-04-05 09:38:27.510156', 'Lolo', '1.00', 4, 6, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `amount` decimal(38,2) DEFAULT NULL,
  `bank_account` varchar(255) DEFAULT NULL,
  `currency` varchar(3) DEFAULT NULL,
  `date_time_inscription` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`user_id`, `amount`, `bank_account`, `currency`, `date_time_inscription`, `email`, `enabled`, `first_name`, `last_name`, `password`) VALUES
(1, '149.93', 'JONDOE', 'USD', NULL, 'jon@doe.com', NULL, 'Jon', 'Doe', '$2a$10$KuU1R.skqxWRAyuLs54kluSNszP4JtMjesbpuPelmOizfbCsjliTC'),
(2, '107.10', 'JANEDOE', 'EUR', NULL, 'jane@Doe.com', NULL, 'Jane', 'Doe', '$2a$10$kL0wAKmXu6tBL9NuEiP/RuU4HELzJF2JLRLtb2bGuAWBFR3annHau'),
(3, '767.15', 'JANEJUNGLE', 'USD', NULL, 'joselct17@gmail.com', NULL, 'Ca', 'Jo', '$2a$10$CeM18LE0sVbNwvy0znbVzOAUiBuc2KAuoS0lgmr0ZCMpd.dY1oiPu'),
(4, '684.68', 'JONDOE', 'USD', NULL, 'libita65@hotmail.fr', NULL, 'Ana', 'Kedinger', '$2a$10$X87QzvlDkRnAJw13ASq8ROWulYefO.bZFO2DmBsn2Z0XLi5nm91oq'),
(5, '412.02', 'ddddd', 'USD', NULL, 'tim@tom.com', NULL, 'Tim', 'Ton', '$2a$10$7ofntDnf9b5ZaQb6KNx1..Jt5jo9bHCoxRGRmO1z.AGueZadPy8TW'),
(6, '327.47', 'JOSELUIS', 'USD', NULL, 'jose@luis.com', NULL, 'Jose', 'Luis', '$2a$10$qu9nGbHsPTCeFWEEpJSwcOYbSxjTEkwv0uTMGd4juF.OXEwUTI2mu'),
(7, '9003.18', 'SOULREAPER', 'USD', '2023-04-03 16:48:02.056806', 'bankai@bleach.com', b'1', 'Ichigo', 'Kurosaki', '$2a$10$zNieks7jR9sp95aDiP306.b5E08whYY7OXx77hMo/LU.5eZg.6Or2'),
(8, NULL, '1AX123456789', NULL, '2023-04-04 13:55:05.024419', 'johndoe@mail.com', b'1', 'john', 'doe', '$2a$10$kqP9LA1Iu4xGQpHZsSyccOiSdbpkTGee47Z1F7nn4gE5ISJPXD5Sa'),
(9, '0.00', 'LUISJOE', 'USD', '2023-04-04 14:03:03.199548', 'luis@jose.com', b'1', 'Luis', 'Jose', '$2a$10$dF1aBTMaCq3mGCi89rocQeo/GDkP8gu9AU8/ZsV4QETgQXOdU0x8q'),
(10, '200.00', '1AX123456789', 'USD', '2023-04-04 14:06:56.805088', 'nan@mail.com', b'1', 'john', 'doe', '$2a$10$G7sfTQ.hCTSJ9UPNeLFPzO0pr7i5as.arEJ8XqKWr1ozcytYEt3zG'),
(11, '200.00', '1AX123456789', 'USD', '2023-04-04 14:24:03.582169', 'dddd@mail.com', b'1', 'john', 'doe', '$2a$10$cHmegFLHkE0jBoli7YviyexV.mXrJNX96SzsAatI2.5qg5jHe62lq'),
(12, '200.00', '1AX123456789', 'USD', '2023-04-04 14:25:52.720292', 'dddddddd@mail.com', b'1', 'john', 'doe', '$2a$10$LC4nzmPS8atlfBzJlfEA/eqU.7vOMshn.kY2qRSOG32Km9OlXiszq'),
(13, '200.00', 'eeeeee', 'USD', '2023-04-04 14:26:42.127207', 'jose@oui.com', b'1', 'Cardona', 'Jose', '$2a$10$/yxsZj8sdF63q1fkf3T9OOJc9OIkh04sQWgJarnRqtKY9eI0W.05y'),
(14, '1100.00', 'TESTEST', 'USD', '2023-04-04 14:30:19.369862', 'test@test.com', b'1', 'Test', 'Test', '$2a$10$MBNe2YbHc.Ne.IcxLH8IUOJ8PLfiuZJisJn.ZHKv.Dfy2X4TaU5iq'),
(15, '200.00', '1AX123456789', 'USD', '2023-04-05 09:45:30.648834', 'BiBoy@mail.com', b'1', 'Big', 'Boy', '$2a$10$EJvkCh5wqeMEpYMOZYoeEeZx1PPCqbvoiyKXbqaatOLb6syyv0BrK');

-- --------------------------------------------------------

--
-- Structure de la table `user_connections`
--

CREATE TABLE `user_connections` (
  `user_id` int(11) NOT NULL,
  `connection_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user_connections`
--

INSERT INTO `user_connections` (`user_id`, `connection_id`) VALUES
(4, 1),
(4, 2),
(4, 3),
(4, 5),
(4, 6),
(6, 1),
(6, 3),
(6, 4),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(9, 3);

-- --------------------------------------------------------

--
-- Structure de la table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `roles_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `roles_id`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1),
(5, 1),
(6, 1),
(6, 2),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 1),
(14, 1),
(15, 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `banktransaction`
--
ALTER TABLE `banktransaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKnxws7rrdcfbwt1yngquqfaiib` (`user_id`);

--
-- Index pour la table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`roles_id`);

--
-- Index pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK56f9rnpd6nefnk255kduaai0u` (`userdestination_id`),
  ADD KEY `FKfuehva90xvwxjdxyjeoat3yk2` (`usersource_id`),
  ADD KEY `FKsg7jp0aj6qipr50856wf6vbw1` (`user_id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Index pour la table `user_connections`
--
ALTER TABLE `user_connections`
  ADD PRIMARY KEY (`user_id`,`connection_id`),
  ADD KEY `FKs65m60yfjlj2plyjpbge2i0ou` (`connection_id`);

--
-- Index pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`roles_id`),
  ADD KEY `FKdbv8tdyltxa1qjmfnj9oboxse` (`roles_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `banktransaction`
--
ALTER TABLE `banktransaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT pour la table `roles`
--
ALTER TABLE `roles`
  MODIFY `roles_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `banktransaction`
--
ALTER TABLE `banktransaction`
  ADD CONSTRAINT `FKnxws7rrdcfbwt1yngquqfaiib` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Contraintes pour la table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `FK56f9rnpd6nefnk255kduaai0u` FOREIGN KEY (`userdestination_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKfuehva90xvwxjdxyjeoat3yk2` FOREIGN KEY (`usersource_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKsg7jp0aj6qipr50856wf6vbw1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`);

--
-- Contraintes pour la table `user_connections`
--
ALTER TABLE `user_connections`
  ADD CONSTRAINT `FK3yvohvstyav0ifob852rlnecu` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKs65m60yfjlj2plyjpbge2i0ou` FOREIGN KEY (`connection_id`) REFERENCES `user` (`user_id`);

--
-- Contraintes pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  ADD CONSTRAINT `FKdbv8tdyltxa1qjmfnj9oboxse` FOREIGN KEY (`roles_id`) REFERENCES `roles` (`roles_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
