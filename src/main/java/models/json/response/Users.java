package models.json.response;

//import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@JsonDeserialize
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    Integer page;
    Integer per_page;
    Integer total;
    Integer total_pages;
//    List<UserData> data;

}
