MYSQL
localhost:3306/test?serverTimezone=Asia/Seoul
root / 1234

CREATE TABLE `user` (
`id` bigint NOT NULL AUTO_INCREMENT,
`login_id` varchar(100) DEFAULT NULL,
`password` varchar(100) DEFAULT NULL,
`name` varchar(100) DEFAULT NULL,
`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
`create_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

CREATE TABLE `file` (
`id` bigint NOT NULL,
`ref_path` varchar(100) DEFAULT NULL,
`ref_id` bigint DEFAULT NULL,
`ori_name` varchar(100) DEFAULT NULL,
`new_name` varchar(200) DEFAULT NULL,
`save_path` varchar(200) DEFAULT NULL,
`ext` varchar(45) DEFAULT NULL,
`type` varchar(100) DEFAULT NULL,
`size` int DEFAULT NULL,
`create_time` datetime DEFAULT CURRENT_TIMESTAMP,
`create_id` bigint DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3