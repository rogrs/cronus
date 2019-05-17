package br.com.rogrs.cronus.domain;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String author;
    private String review;
    private LocalDate date;
}
