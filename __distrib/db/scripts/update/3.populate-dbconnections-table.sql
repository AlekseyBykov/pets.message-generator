insert into MESSAGE_GENERATOR.DB_CONNECTIONS
(name, url, driverclassname, user, password, active)
values
('local sufd stand', 'jdbc:oracle:thin:@...', 'oracle.jdbc.driver.OracleDriver', '...', '...', true),
('remote stand', 'jdbc:oracle:thin:@...', 'oracle.jdbc.driver.OracleDriver', '...', '...', false);
