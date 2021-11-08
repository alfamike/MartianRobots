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

import dev.alfamike.martianrobots.Instruction;
import dev.alfamike.martianrobots.Orientation;
import dev.alfamike.martianrobots.model.Coordinate;
import dev.alfamike.martianrobots.model.Input;
import dev.alfamike.martianrobots.model.Robot;
import dev.alfamike.martianrobots.repository.InputRepository;
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
	Input input = new Input();
	Robot robot = new Robot();
	
	@Autowired
	InputRepository repoInput;
	
	@Autowired
	RobotRepository repoRobot;
	
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
			
			//Establish grid
			grid = new String [xAxisGrid][yAxisGrid];
			input.setGridX(xAxisGrid);
			input.setGridY(yAxisGrid);
			
			// Result json generation
			JsonFactory factory = new JsonFactory();
			StringWriter writer = new StringWriter();

			JsonGenerator generator = factory.createGenerator(writer);
			generator.writeStartObject();
			generator.writeStringField("response", "Grid for exploration established");
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
			
			// Position in grid
			grid[xAxis][yAxis] = "X";
			
			// Configuring robot
			robot.setxPosition(xAxis);
			robot.setyPosition(yAxis);
			robot.setOrientation(orientation);
			
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
		
		try {
			// Read json
			JsonNode rjson = objectMapper.readTree(json);
			String movements = rjson.get("movements").asText();
			
			if (movements.length() > 100) {
				status = HttpStatus.BAD_REQUEST;
				message = "Too many movements. Try less than 100";
			} else {
				message = "Final position reached: ";
				input.setMovements(movements);
				//Save input object to ddbb
				repoInput.save(input);
				
				
				char [] movementsArray = movements.toCharArray();
				
				for (char c : movementsArray) {
					Instruction i = Instruction.valueOf(String.valueOf(c));
					//Get actual robot position
					Coordinate aux = new Coordinate(robot.getxPosition(), robot.getyPosition(), robot.getOrientation());
					
					//Run movement
					Coordinate coResult = i.run(aux);
					
					//Update robot
					robot.setxPosition(coResult.getxAxis());
					robot.setyPosition(coResult.getyAxis());
					robot.setOrientation(coResult.getOrientation());
					
					
				}
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
