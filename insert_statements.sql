INSERT INTO congo_customers 
	(fname, lname, address1, city, postcode, phone, email, password)
VALUES 
	('John', 'Lennon', '1 Penny Lane', 'Liverpool', 'L1 23TB', '0151 123 4567', 'john@thebeatles.com', 'thesinger'),
	('Paul', 'McCartney', '2 Penny Lane', 'Liverpool', 'L1 34TB', '0151 234 5678', 'paul@thebeatles.com', 'thebassplayer'),
	('George', 'Harrison', '3 Penny Lane', 'Liverpool', 'L1 56TB', '0151 345 6789', 'george@thebeatles.com', 'theguitarist'),
	('Ringo', 'Starr', '4 Penny Lane', 'Liverpool', 'L1 78TB', '0151 456 7890', 'ringo@thebeatles.com', 'thedrummer');

INSERT INTO congo_orders
	(custid, order_date)
VALUES
	(2, '2015-12-15'),
	(3, '2015-12-10'),
	(4, '2015-12-08'),
	(5, '2015-12-04'),
	(2, '2015-11-29'),
	(3, '2015-11-25'),
	(4, '2015-11-23'),
	(5, '2015-11-20'),
	(2, '2015-11-17'),
	(3, '2015-11-15'),
	(4, '2015-11-13'),
	(5, '2015-11-11'),
	(2, '2015-11-10'),
	(3, '2015-11-08'),
	(4, '2015-11-05'),
	(5, '2015-11-03');

INSERT INTO congo_order_details
	(orderid, recording_id, order_quantity)
VALUES
	(7,2021,1),
	(7,2034,1),
	(7,2048,2),
	(8,2032,1),
	(8,2022,1),
	(9,2000,1),
	(10,2001,2),
	(10,2037,1),
	(11,2040,1),
	(12,2045,2),
	(12,2046,1),
	(13,2049,1),
	(14,2041,1),
	(15,2033,2),
	(15,2034,1),
	(16,2039,1),
	(16,2025,1),
	(16,2024,1),
	(17,2034,1),
	(18,2035,4),
	(18,2042,4),
	(18,2046,4),
	(18,2048,4),
	(19,2041,1),
	(19,2038,1),
	(20,2042,1),
	(21,2044,1),
	(21,2043,2),
	(22,2023,1);