-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema FKSvodna
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema FKSvodna
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `FKSvodna` DEFAULT CHARACTER SET utf8 ;
USE `FKSvodna` ;

-- -----------------------------------------------------
-- Table `FKSvodna`.`KorisnickiNalog`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`KorisnickiNalog` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  `KorisnickoIme` VARCHAR(45) NOT NULL,
  `Lozinka` VARCHAR(255) NOT NULL,
  `Admin` TINYINT NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Rukovodilac`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Rukovodilac` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  `BrojTelefona` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(100) NOT NULL,
  `Pozicija` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Sponzor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Sponzor` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Adresa` VARCHAR(150) NOT NULL,
  `Email` VARCHAR(100) NULL,
  `BrojTelefona` VARCHAR(45) NOT NULL,
  `Vrsta` VARCHAR(45) NOT NULL,
  `JmbJib` VARCHAR(13) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Uplata`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Uplata` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `SponzorId` INT NOT NULL,
  `Iznos` DECIMAL NOT NULL,
  `DatumUplate` DATETIME NOT NULL,
  `DatumIsteka` DATETIME NULL,
  PRIMARY KEY (`Id`, `SponzorId`),
  INDEX `_idx` (`SponzorId` ASC) VISIBLE,
  CONSTRAINT ``
    FOREIGN KEY (`SponzorId`)
    REFERENCES `FKSvodna`.`Sponzor` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`KontaktOsoba`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`KontaktOsoba` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  `BrojTelefona` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`SponzorKontaktOsoba`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`SponzorKontaktOsoba` (
  `SponzorId` INT NOT NULL,
  `KontaktOsobaId` INT NOT NULL,
  PRIMARY KEY (`SponzorId`, `KontaktOsobaId`),
  INDEX `2_idx` (`KontaktOsobaId` ASC) VISIBLE,
  INDEX `1_idx` (`SponzorId` ASC) VISIBLE,
  CONSTRAINT `1`
    FOREIGN KEY (`SponzorId`)
    REFERENCES `FKSvodna`.`Sponzor` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `2`
    FOREIGN KEY (`KontaktOsobaId`)
    REFERENCES `FKSvodna`.`KontaktOsoba` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Osoba`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Osoba` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  `BrojTelefona` VARCHAR(30) NOT NULL,
  `Jmb` VARCHAR(13) NOT NULL,
  `Email` VARCHAR(45) NULL,
  `Adresa` VARCHAR(100) NOT NULL,
  `BrojLicence` VARCHAR(45) NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`LjekarskiPregled`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`LjekarskiPregled` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `DatumPregleda` DATETIME NOT NULL,
  `DatumIsteka` DATETIME NOT NULL,
  `OsobaId` INT NOT NULL,
  PRIMARY KEY (`Id`, `OsobaId`),
  INDEX `4_idx` (`OsobaId` ASC) VISIBLE,
  CONSTRAINT `4`
    FOREIGN KEY (`OsobaId`)
    REFERENCES `FKSvodna`.`Osoba` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Kazna`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Kazna` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `DatumKazne` DATETIME NOT NULL,
  `NovcaniIznos` DECIMAL NULL,
  `BrojMecevaSuspenzije` INT NOT NULL,
  `Opis` VARCHAR(255) NULL,
  `OsobaId` INT NOT NULL,
  `Karton` ENUM('Zuti', 'Crveni') NULL,
  PRIMARY KEY (`Id`, `OsobaId`),
  INDEX `3_idx` (`OsobaId` ASC) VISIBLE,
  CONSTRAINT `3`
    FOREIGN KEY (`OsobaId`)
    REFERENCES `FKSvodna`.`Osoba` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Oprema`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Oprema` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `OsobaId` INT NULL,
  `TipOpreme` VARCHAR(45) NOT NULL,
  `BrojDresa` VARCHAR(45) NULL,
  `SifraOpreme` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`),
  INDEX `5_idx` (`OsobaId` ASC) VISIBLE,
  CONSTRAINT `5`
    FOREIGN KEY (`OsobaId`)
    REFERENCES `FKSvodna`.`Osoba` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Tim`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Tim` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`OsobaTim`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`OsobaTim` (
  `OsobaId` INT NOT NULL,
  `TimId` INT NOT NULL,
  `Od` DATETIME NOT NULL,
  `Do` DATETIME NULL,
  `Uloga` VARCHAR(45) NOT NULL,
  `PozicijaIgraca` VARCHAR(45) NULL,
  PRIMARY KEY (`OsobaId`, `TimId`),
  INDEX `7_idx` (`TimId` ASC) VISIBLE,
  INDEX `6_idx` (`OsobaId` ASC) VISIBLE,
  CONSTRAINT `6`
    FOREIGN KEY (`OsobaId`)
    REFERENCES `FKSvodna`.`Osoba` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `7`
    FOREIGN KEY (`TimId`)
    REFERENCES `FKSvodna`.`Tim` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Utakmica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Utakmica` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `DatumIVrijeme` DATETIME NOT NULL,
  `ProtivnickiTim` VARCHAR(50) NOT NULL,
  `Rezultat` VARCHAR(15) NULL,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`Zaduzenje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`Zaduzenje` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `Opis` VARCHAR(100) NOT NULL,
  `Odradjeno` TINYINT NOT NULL,
  `UtakmicaId` INT NOT NULL,
  PRIMARY KEY (`Id`, `UtakmicaId`),
  INDEX `8_idx` (`UtakmicaId` ASC) VISIBLE,
  CONSTRAINT `8`
    FOREIGN KEY (`UtakmicaId`)
    REFERENCES `FKSvodna`.`Utakmica` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `FKSvodna`.`OsobaTimUtakmica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `FKSvodna`.`OsobaTimUtakmica` (
  `OsobaId` INT NOT NULL,
  `TimId` INT NOT NULL,
  `UtakmicaId` INT NOT NULL,
  PRIMARY KEY (`OsobaId`, `TimId`, `UtakmicaId`),
  INDEX `10_idx` (`UtakmicaId` ASC) VISIBLE,
  INDEX `9_idx` (`OsobaId` ASC, `TimId` ASC) VISIBLE,
  CONSTRAINT `9`
    FOREIGN KEY (`OsobaId` , `TimId`)
    REFERENCES `FKSvodna`.`OsobaTim` (`OsobaId` , `TimId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `10`
    FOREIGN KEY (`UtakmicaId`)
    REFERENCES `FKSvodna`.`Utakmica` (`Id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
