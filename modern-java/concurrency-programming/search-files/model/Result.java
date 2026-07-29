package model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Result {
	private boolean isFound;
	private String path;
}
