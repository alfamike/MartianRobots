package dev.alfamike.martianrobots.controller;

import java.io.IOException;
import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.alfamike.martianrobots.Coordinate;
import dev.alfamike.martianrobots.Instruction;
import dev.alfamike.martianrobots.Orientation;
import dev.alfamike.martianrobots.model.ForbiddenCoordinates;
import dev.alfamike.martianrobots.model.Input;
import dev.alfamike.martianrobots.model.LogMovements;
import dev.alfamike.martianrobots.model.Output;
import dev.alfamike.martianrobots.model.Robot;
import dev.alfamike.martianrobots.repository.ForbiddenCoordinatesRepository;
import dev.alfamike.martianrobots.repository.InputRepository;
import dev.alfamike.martianrobots.repository.LogMovementsRepo;
import dev.alfamike.martianrobots.repository.OutputRepository;
import dev.alfamike.martianrobots.repository.RobotRepository;

/**
 *  RestController class for operations endpoints
 *  
 *  @author Alvaro Menacho Rodriguez
 * 
 */
@RestController
public class OperationController {
	private String grid [][]= null; // X: robot path
	Input input = null;
	Robot robot = null;
	
	@Autowired
	InputRepository repoInput;
	
	@Autowired
	RobotRepository repoRobot;
	
	@Autowired
	ForbiddenCoordinatesRepository repoForbidden;
	
	@Autowired
	LogMovementsRepo repoLog;
	
	@Autowired
	OutputRepository repoOutput;
	
	/**
	 * 	@author Alvaro Menacho Rodriguez
	 *  @param input json with grid value
	 *  @return Confirmation message
	 **/
	@PostMapping(path = "setup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> setup(@RequestBody String json) {

		ObjectMapper objectMapper = new ObjectMapper();
		String resultJson = null;
		HttpStatus status = HttpStatus.OK;
		try {
			// Read json
			JsonNode rjson = objectMapper.readTree(json);
			int xAxisGrid = rjson.get("xAxisGrid").asInt();
			int yAxisGrid = rjson.get("yAxisGrid").asInt();
			String message = null;
			if (xAxisGrid > 50 || yAxisGrid > 50) {
				message = "Grid limit is 50";
				status = HttpStatus.BAD_REQUEST;
			} else {
				//Establish grid
				grid = new String [xAxisGrid][yAxisGrid];
				input = new Input();
				input.setGridX(xAxisGrid);
				input.setGridY(yAxisGrid);
				
				// Save to ddbb
				input = repoInput.save(input);
				
				//Print to cmd
				System.out.println(xAxisGrid+"\t"+yAxisGrid);
				message = "Grid for exploration established";
			}		

			// Result json generation
			JsonFactory factory = new JsonFactory();
			StringWriter writer = new StringWriter();

			JsonGenerator generator = factory.createGenerator(writer);
			generator.writeStartObject();
			generator.writeStringField("response", message);
			generator.close();

			resultJson = writer.toString();

		} catch (JsonMappingException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<String>(resultJson, status);
	}

	/**
	 * 	@author Alvaro Menacho Rodriguez
	 *  @param input json with initial position value
	 *  @return Confirmation message
	 **/
	@PostMapping(path = "initialPosition", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> initialPosition(@RequestBody String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		String resultJson = null;
		HttpStatus status = HttpStatus.OK;
		try {
			// Read json
			JsonNode rjson = objectMapper.readTree(json);
			int xAxis = rjson.get("xAxisInitial").asInt();
			int yAxis = rjson.get("yAxisInitial").asInt();
			Orientation orientation = Orientation.valueOf(rjson.get("orientationInitial").asText());
			
			// Setting input
			input.setInitialCoordinateX(xAxis);
			input.setInitialCoordinateY(yAxis);
			input.setInitialCoordinateO(orientation);
			input = repoInput.save(input);
			
			// Position in grid
			grid[xAxis][yAxis] = "X";
			
			// Configuring robot
			robot = new Robot();
			robot.setxPosition(xAxis);
			robot.setyPosition(yAxis);
			robot.setOrientation(orientation);
			
			// Save to ddbb
			robot = repoRobot.save(robot);
			
			//Print to cmd
			System.out.println(xAxis+"\t"+yAxis+"\t"+orientation.toString());
			
			//Log
			LogMovements log = new LogMovements(robot.getId(), robot.getxPosition(), robot.getyPosition(), robot.getOrientation());
			repoLog.save(log);
			
			// Result json generation
			JsonFactory factory = new JsonFactory();
			StringWriter writer = new StringWriter();

			JsonGenerator generator = factory.createGenerator(writer);
			generator.writeStartObject();
			generator.writeStringField("response", "Initial position established");
			generator.close();

			resultJson = writer.toString();

		} catch (JsonMappingException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<String>(resultJson, status);
	}
	
	/**
	 * 	@author Alvaro Menacho Rodriguez
	 *  @param input json with movements values
	 *  @return Confirmation message
	 **/
	@PostMapping(path = "run",  consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> runExploration(@RequestBody String json){
		ObjectMapper objectMapper = new ObjectMapper();
		String resultJson = null;
		HttpStatus status = HttpStatus.OK;
		String message = null;
		Coordinate coResult = null;
		Coordinate aux = null;
		try {
			// Read json
			JsonNode rjson = objectMapper.readTree(json);
			String movements = rjson.get("movements").asText();
			
			if (movements.length() > 100) {
				status = HttpStatus.BAD_REQUEST;
				message = "Too many movements. Try less than 100";
			} else {
				input.setMovements(movements);
				//Save input object to ddbb
				input = repoInput.save(input);
				
				
				char [] movementsArray = movements.toCharArray();
				
				
				
				for (char c : movementsArray) {
					Instruction i = Instruction.valueOf(String.valueOf(c));
					//Get actual robot position
					aux = new Coordinate(robot.getxPosition(), robot.getyPosition(), robot.getOrientation());
					
					//Run movement
					 coResult = i.run(aux);

					 ForbiddenCoordinates fcoo = repoForbidden.findByXAxisAndYAxis(coResult.getxAxis(), coResult.getyAxis());
					 if (fcoo == null) {
						// Out grid
							if (coResult.getxAxis() > input.getGridX() || coResult.getyAxis() > input.getGridY()){
								
								// Position in grid
								grid[aux.getxAxis()][aux.getyAxis()] = "X";
								
								//Update robot with last position
								robot.setxPosition(aux.getxAxis());
								robot.setyPosition(aux.getyAxis());
								robot.setOrientation(aux.getOrientation());
								
								//Save to ddbb
								robot = repoRobot.save(robot);
								message = aux.getxAxis()+"\t"+aux.getyAxis()+"\t"+aux.getOrientation().toString()+"\t"+"LOST";
								System.out.println(message);
								
								// Save output
								Output out = new Output(aux.getxAxis(), aux.getyAxis(), aux.getOrientation(), "LOST");
								repoOutput.save(out);
								
								//Log
								LogMovements log = new LogMovements(robot.getId(), robot.getxPosition(), robot.getyPosition(), robot.getOrientation());
								repoLog.save(log);
								
								//Mark forbidden grid point
								grid[aux.getxAxis()][aux.getyAxis()] = "L";
								
								// Save to ddbb forbidden grid point
								ForbiddenCoordinates newFcoo= new ForbiddenCoordinates(aux.getxAxis(), aux.getyAxis());
								repoForbidden.save(newFcoo);
								
								//New robot launches
								robot = new Robot();
								robot.setxPosition(aux.getxAxis());
								robot.setyPosition(aux.getyAxis());
								robot.setOrientation(aux.getOrientation());
								
								// Save to ddbb
								robot = repoRobot.save(robot);
								
								//Log
								LogMovements log2 = new LogMovements(robot.getId(), robot.getxPosition(), robot.getyPosition(), robot.getOrientation());
								repoLog.save(log2);
								
								
							} else {
								
								// Position in grid
								grid[coResult.getxAxis()][coResult.getyAxis()] = "X";
								
								//Update robot
								robot.setxPosition(coResult.getxAxis());
								robot.setyPosition(coResult.getyAxis());
								robot.setOrientation(coResult.getOrientation());
								
								//Save to ddbb
								robot = repoRobot.save(robot);
								
								//Log
								LogMovements log = new LogMovements(robot.getId(), robot.getxPosition(), robot.getyPosition(), robot.getOrientation());
								repoLog.save(log);
								
								//Print to cmd
								message = coResult.getxAxis()+"\t"+coResult.getyAxis()+"\t"+coResult.getOrientation().toString();
							}
					 } else {
						 //Skip forbidden grid point
					 }
					
				}
				
				// Save output
				Output out = new Output(aux.getxAxis(), aux.getyAxis(), aux.getOrientation(), null);
				repoOutput.save(out);
				
				System.out.println(message);
			}
			
			// Result json generation
			JsonFactory factory = new JsonFactory();
			StringWriter writer = new StringWriter();

			JsonGenerator generator = factory.createGenerator(writer);
			generator.writeStartObject();
			generator.writeStringField("response", message);
			generator.close();

			resultJson = writer.toString();

		} catch (JsonMappingException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ResponseEntity<String>(resultJson, status);
	}
}
