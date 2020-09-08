package DataTransferObjects;

import java.security.PublicKey;

public class CustomerPathDTO {
    public CustomerPathDTO(String pathName, int count) {
        PathName = pathName;
        Count = count;
    }

    public String PathName;
    public  int Count;
}
