package com.yuzee.app.bean;

import java.io.Serializable;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstituteProviderCode implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String value;
}
