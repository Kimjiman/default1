**MYSQL**

* localhost:3306/test?serverTimezone=Asia/Seoul
* root / 1234
---
<pre>
<code>
    CREATE TABLE `user` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `login_id` varchar(100) DEFAULT NULL,
    `password` varchar(100) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
</code>
</pre>
---
<pre>
<code>
    INSERT INTO user (login_id, password, name, create_id)
    VALUES ('admin', '$2a$10$JHRc1ScPG1dQncw9Jh8oJOyCtIzgi2uCYbQnu4OYz1QVcDs8kWpD2', '관리자', '1')
</code>
</pre>
---
<pre>
<code>
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
</code>
</pre>
---
<pre>
<code>
    CREATE TABLE `code_group` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code_group` varchar(10) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT NULL,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
</code>
</pre>
---
<pre>
<code>
    CREATE TABLE `code` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code_group_id` bigint DEFAULT NULL,
    `code` varchar(10) DEFAULT NULL,
    `name` varchar(100) DEFAULT NULL,
    `order` int DEFAULT NULL,
    `info` varchar(1000) DEFAULT NULL,
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `create_id` bigint DEFAULT NULL,
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
    `update_id` bigint DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
</code>
</pre>