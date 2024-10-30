import com.google.gson.Gson;
import com.dampcake.bencode.Bencode;

public class Main {
  private static final Gson gson = new Gson();

  public static void main(String[] args) throws Exception {

    String command = args[0];
    if("decode".equals(command)) {
       String bencodedValue = args[1];
       try {
        Object result = decode(bencodedValue);
        if (result instanceof String) {
          System.out.println(gson.toJson((String) result));
        } else if (result instanceof Long) {
          System.out.println(gson.toJson((Long) result));
        }
       } catch (Exception e) {
        System.out.println("Invalid bencoded value: " + bencodedValue);
       }
    } else {
      System.out.println("Unknown command: " + command);
    }

  }

  public static <T> T decode(String bencodedString){
    if (bencodedString == null || bencodedString.isEmpty()){
      return null;
    } else if (Character.isDigit(bencodedString.charAt(0))){
      return (T) decodeString(bencodedString);      
    } else if (bencodedString.startsWith("i")) {
      return (T) decodeInteger(bencodedString);
    }
    throw new IllegalArgumentException("Invalid bencoded format.");
  }

  public static Long decodeInteger(String bencodedString) {
    int indexOfE = bencodedString.indexOf('e');
    return Long.parseLong(bencodedString.substring(1, indexOfE));
  }
  
  public static String decodeString(String bencodedString){
    int firstColonIndex = bencodedString.indexOf(':');
    int length = Integer.parseInt(bencodedString.substring(0, firstColonIndex));
    return bencodedString.substring(firstColonIndex + 1, firstColonIndex + 1 + length);
  }
  
}
