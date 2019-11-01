CREATE TABLE IF NOT EXISTS flink2.`user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `userid` int(11) DEFAULT NULL,
    `name` varchar(40) DEFAULT NULL,
    `password` varchar(40) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `user_unique` (`userid`,`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE IF NOT EXISTS flink2.`user` (
    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
    `userid` int(11) DEFAULT NULL,
    `name` varchar(40) DEFAULT NULL,
    `password` varchar(40) DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `user_unique` (`userid`,`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

drop trigger if exists flink.tri_insert;
DELIMITER $$
CREATE TRIGGER `flink`.`tri_insert` AFTER INSERT
ON `flink`.`user`
FOR EACH ROW BEGIN
    INSERT INTO `flink2`.`user`
        ( userid, name, password )
    VALUES
        ( new.userid, new.name, new.password )
    ON DUPLICATE KEY UPDATE
        userid=new.userid,
        name=new.name,
        password=new.password ;
END$$
DELIMITER ;


drop trigger if exists flink.tri_update;
DELIMITER $$
CREATE TRIGGER `flink`.`tri_update` AFTER UPDATE
    ON `flink`.`user`
    FOR EACH ROW BEGIN
    INSERT INTO `flink2`.`user`
    ( userid, name, password )
    VALUES
    ( new.userid, new.name, new.password )
    ON DUPLICATE KEY UPDATE
        userid=new.userid,
        name=new.name,
        password=new.password ;
END$$
DELIMITER ;


