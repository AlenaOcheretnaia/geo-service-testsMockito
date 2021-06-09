import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MockitoTests {

    //Поверить, что MessageSenderImpl всегда отправляет только русский текст,
    // если ip относится к российскому сегменту адресов.
    @Test
    void messageForRussiaIpTest() {
        String expectedText = "Добро пожаловать";
        String ipAddr = "ipRussia";

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ipAddr)).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddr);
        String actualText = messageSender.send(headers);

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void messageForCountryRussiaTest() {
        String expectedText = "Добро пожаловать";
        String ipAddr = "172.0.0.0";

        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddr);
        String actualText = messageSender.send(headers);

        Assertions.assertEquals(expectedText, actualText);
    }

    //Поверить, что MessageSenderImpl всегда отправляет только английский текст,
    // если ip относится к американскому сегменту адресов.
    @Test
    void messageForUsaIpTest() {
        String expectedText = "Welcome";
        String ipAddr = "ipUSA";

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp(ipAddr)).thenReturn(new Location("New York", Country.USA, null, 0));
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddr);
        String actualText = messageSender.send(headers);

        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void messageForCountryUSATest() {
        String expectedText = "Welcome";
        String ipAddr = "96.0.0.0";

        GeoService geoService = new GeoServiceImpl();
        LocalizationService localizationService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ipAddr);
        String actualText = messageSender.send(headers);

        Assertions.assertEquals(expectedText, actualText);
    }

    //Написать тесты для проверки определения локации по ip (класс GeoServiceImpl)
    //Проверить работу метода public Location byIp(String ip)
    @Test
    void locationRusByIpTest() {
        Location expectedLocation = new Location("Moscow", Country.RUSSIA, null, 0);
        String ipAddr = "172.0.0.0";
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(ipAddr);
        Assertions.assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void locationUsaByIpTest() {
        Location expectedLocation = new Location("New York", Country.USA, null,  0);
        String ipAddr = "96.0.0.0";
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location actualLocation = geoService.byIp(ipAddr);
        Assertions.assertEquals(expectedLocation, actualLocation);
    }
    //Написать тесты для проверки возвращаемого текста (класс LocalizationServiceImpl)
    //Проверить работу метода public String locale(Country country)
    @Test
    void localizationRusTest() {
        String expectedText = "Добро пожаловать";
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actualText = localizationService.locale(Country.RUSSIA);
        Assertions.assertEquals(expectedText, actualText);
    }

    @Test
    void localizationEngTest() {
        String expectedText = "Welcome";
        LocalizationServiceImpl localizationService = new LocalizationServiceImpl();
        String actualText = localizationService.locale(Country.GERMANY);
        Assertions.assertEquals(expectedText, actualText);
    }


}
