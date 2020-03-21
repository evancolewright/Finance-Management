-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Mar 19, 2020 at 02:22 AM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `finance_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `accounts`
--

CREATE TABLE `accounts` (
  `accountID` int(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `balance` double NOT NULL,
  `passwd` varchar(255) NOT NULL COMMENT 'User login password',
  `annualInterestRate` double NOT NULL,
  `dateCreated` datetime NOT NULL DEFAULT current_timestamp(),
  `transactions` text DEFAULT NULL COMMENT 'VarChar too small'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `accounts`
--

INSERT INTO `accounts` (`accountID`, `username`, `balance`, `passwd`, `annualInterestRate`, `dateCreated`, `transactions`) VALUES
(1, 'rburgess', 4500, 'root', 0.02, '2020-02-21 14:49:08', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `transactionID` int(11) NOT NULL,
  `accountID` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `type` varchar(1) NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `accounts`
--
ALTER TABLE `accounts`
  ADD PRIMARY KEY (`accountID`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `accounts`
--
ALTER TABLE `accounts`
  MODIFY `accountID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
