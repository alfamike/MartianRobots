# MartianRobots
Martian Robots by alfamike

Imagine that the surface of Mars can be modelled by a rectangular grid around which robots are
able to move according to instructions provided from Earth. This program aims to control the movement of the robots and report their final position.


The app is available in the url: https://martian-robots-alfamike.herokuapp.com
The persistence layer has been built in a Postgresql database hosted in Heroku.

It has been chosen a Rest API Approach with the following endpoints and with inputs and outputs with json format:

- POST "/setup": Establish the grid dimensions.
	Example:
		{
			"xAxisGrid": 5,
			"yAxisGrid": 3
		}
- POST "/initialPosition": It is used to establish robot initial position and orientation.
	Example:
		{
			"xAxisInitial": 1,
			"yAxisInitial": 1,
			"orientationInitial": "W"
		}
- POST "/run": run the logic to move robots arount the grid.
	Example:
		{
			"movements": "RFRFRFRF"
		}
	Where R is turn right order, L is turn left order and F is goint forward order
	
Restrictions:
- Maximum grid dimensions are 50x50
- Maximum 100 movements orden in a row

Observations:

- If a robot falls off the edge of the grid it is lost and a new one is launched in the last known position. That position is marked as "forbidden" for future robots.
- Input, output and each operation are logged into database.
