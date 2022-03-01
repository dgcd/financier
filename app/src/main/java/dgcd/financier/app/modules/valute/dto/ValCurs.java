package dgcd.financier.app.modules.valute.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ValCurs {

    @JacksonXmlProperty(isAttribute = true, localName = "Date")
    private String date;

    @JacksonXmlProperty(isAttribute = true, localName = "name")
    private String name;

    @JsonProperty("Valute")
    private List<Valute> valutes;

}
