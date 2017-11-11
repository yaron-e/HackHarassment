CREATE TABLE `User` (`userID` int(11) NOT NULL AUTO_INCREMENT, 
`userName` varchar(45) NOT NULL, 
`email` varchar(45) default NULL,
`platform` varchar(45) default NULL,
`password` varchar(45) default NULL, 
 primary key(`userID`))