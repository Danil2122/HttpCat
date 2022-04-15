import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;

import java.io.IOException;
import java.util.List;


public class Main {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();
        HttpGet request = new HttpGet(
                "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");

        CloseableHttpResponse response = httpClient.execute(request);
//        Создадим в классе Main.java, json mapper: А в методе main добавим код для преобразования json в java:
//        Выведем список постов на экран:
        //Это хорошо известная проблема со стиранием типов в Java: T — это просто переменная типа, и вы должны указать
        // фактический класс, обычно в качестве аргумента класса. Без такой информации лучшее, что можно сделать, — это
        // использовать границы; и простой T примерно такой же, как «T extends Object». Затем Джексон свяжет объекты
        // JSON как карты.
        //В этом случае метод тестера должен иметь доступ к классу, и вы можете построить
//        JavaType type = mapper.getTypeFactory().
//                constructCollectionType(List.class, Cat.class);
        List<Cat> posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {});
        //либо же вместо new TypeReference<List<Cat>>() {} ===== type (из строки 34)
        posts.stream()
                .filter(x -> x.getUpvotes() != 0 && x.getUpvotes() > 0)
                .forEach(System.out::println);
//        posts.forEach(System.out::println);
//        Gson gson = new Gson();
//        Map map = gson.fromJson(EntityUtils.toString(response.getEntity()), Map.class);
//        System.out.println(map.toString());


    }
}
