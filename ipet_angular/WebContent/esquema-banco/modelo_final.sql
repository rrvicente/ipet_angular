-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema ipet_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema ipet_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `ipet_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `ipet_db` ;

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_controla_sequencia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_controla_sequencia` (
  `tabela` VARCHAR(200) NOT NULL,
  `id` INT NOT NULL)
ENGINE = InnoDB;

INSERT INTO TB_CONTROLA_SEQUENCIA (ID,TABELA) VALUES(100,`tb_atendimento`);

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_cliente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_cliente` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(200) NOT NULL,
  `cpf` VARCHAR(14) NOT NULL,
  `telefone` VARCHAR(20) NOT NULL,
  `endereco` VARCHAR(250) NULL,
  `observacao` TEXT NULL,
  `email` VARCHAR(200) NULL,
  `data_cadastro` TIMESTAMP NOT NULL DEFAULT NOW(),
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_animal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_animal` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_cliente` INT NOT NULL,
  `especie` VARCHAR(200) NOT NULL,
  `raca` VARCHAR(200) NOT NULL,
  `nome_pet` VARCHAR(200) NOT NULL,
  `data_nascimento_pet` TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

ALTER TABLE tb_animal ADD CONSTRAINT `fk_id_cliente` FOREIGN KEY (id_cliente) REFERENCES tb_cliente(id);

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_produto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_produto` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `descricao` VARCHAR(400) NOT NULL,
  `valor` DECIMAL(10) NOT NULL,
  `estoque` INT NOT NULL,
  `tipo` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_atendimento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_atendimento` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_animal` INT NOT NULL,
  `valor` DECIMAL(10) NOT NULL,
  `data_atendimento` TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

ALTER TABLE tb_atendimento ADD CONSTRAINT `fk_id_animal` FOREIGN KEY (id_animal) REFERENCES tb_animal(id);

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_atendimento_item`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_atendimento_item` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_produto` INT NOT NULL,
  `id_atendimento` INT NOT NULL,
  `valor` DECIMAL(10) NOT NULL,
  `quantidade` DECIMAL(10) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

ALTER TABLE tb_atendimento_item ADD CONSTRAINT `fk_id_atendimento` FOREIGN KEY (id_atendimento) REFERENCES tb_atendimento(id);
ALTER TABLE tb_atendimento_item ADD CONSTRAINT `fk_id_produto` FOREIGN KEY (id_produto) REFERENCES tb_produto(id);

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_noticia`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_noticia` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(200) NOT NULL,
  `descricao` VARCHAR(250) NULL,
  `texto` TEXT NULL,
  `data` TIMESTAMP NOT NULL DEFAULT NOW(),
  `status` INT NOT NULL DEFAULT 1 COMMENT '1 = bloqueado\n2 = desbloqueado',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `ipet_db`.`tb_imagem`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `ipet_db`.`tb_imagem` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(160) NOT NULL,
  `arquivo` VARCHAR(100) NOT NULL,
  `id_noticia` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_imagem_noticia_idx` (`id_noticia` ASC),
  CONSTRAINT `fk_imagem_noticia`
    FOREIGN KEY (`id_noticia`)
    REFERENCES `ipet_db`.`tb_noticia` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

------ INSERTS -------
INSERT INTO TB_ANIMAL(`especie`,`raca`) VALUES ('Cachorro','Labrador');
INSERT INTO TB_ANIMAL(`especie`,`raca`) VALUES ('Cachorro','Rotweiller');
INSERT INTO TB_ANIMAL(`especie`,`raca`) VALUES ('Cachorro','Pintcher');

INSERT INTO TB_CLIENTE(`id_animal`,`nome_pet`,`data_nascimento_pet`,`nome_cliente`,`cpf`,`telefone`,`endereco`,`observacao`,`data_cadastro`)
VALUES(1,'Pingo','2016-08-29 00:00:00','Jeiso','047.877.489-00','(47) 3322-6611','Rua 15 de Novembro','Texto de observação','2016-08-29 00:00:00');