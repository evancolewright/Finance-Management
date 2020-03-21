
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

CREATE TABLE `accounts` (
  `accountID` int(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `balance` double NOT NULL,
  `passwd` varchar(255) NOT NULL COMMENT 'User login password',
  `annualInterestRate` double NOT NULL,
  `dateCreated` datetime NOT NULL DEFAULT current_timestamp(),
  `transactions` text DEFAULT NULL COMMENT 'VarChar too small'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `accounts` (`accountID`, `username`, `balance`, `passwd`, `annualInterestRate`, `dateCreated`, `transactions`) VALUES
(1, 'rburgess', 21366, 'root', 0.02, '2020-02-21 14:49:08', NULL);


CREATE TABLE `transactions` (
  `transactionID` int(11) NOT NULL,
  `accountID` int(11) NOT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp(),
  `type` varchar(1) NOT NULL,
  `amount` double NOT NULL,
  `description` varchar(500) NOT NULL,
  `balance` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


ALTER TABLE `accounts`
  ADD PRIMARY KEY (`accountID`);

ALTER TABLE `transactions`
  ADD PRIMARY KEY (`transactionID`);

ALTER TABLE `accounts`
  MODIFY `accountID` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

ALTER TABLE `transactions`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=116;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;