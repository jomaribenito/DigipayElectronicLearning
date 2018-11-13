package ph.digipay.digipayelectroniclearning.common.constants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QuestionsList {

    public static List<String> populateQuestions() {
        List<String> questionsList = new ArrayList<>();

        questionsList.add("Ano ang Digipay HealthGuard?");

        questionsList.add("Ano ang age requirement para makapag-avail ng Digipay HealthGuard?");

        questionsList.add("Magkano ang babayaran ng customer para sa Digipay HealthGuard Silver Monthly?");

        questionsList.add("Magkano ang babayaran ng customer para sa Digipay HealthGuard Gold Yearly?");

        questionsList.add("Kung mayroon kang pre-existing medical conditions, maaari ka pa bang kumuha ng Digipay HealthGuard?");

        questionsList.add("Ilang Digipay HealthGuard ang maaaring kunin ng isang customer?");

        questionsList.add("Ang Microinsurance ay abot kayang insurance na nagbibigay proteksyon sa mga panganib o 'risks'");

        questionsList.add("Halimbawa ng Individual Risk.");

        questionsList.add("Isang halimbawa kung paano maghanda sa mga panganib o ‘risks’.");

        questionsList.add("Ano ang tawag sa sitwasyong may kinalaman sa panganib, pinsala, o pagkawala?");

        return questionsList;
    }

    public static Map<Integer, List<String>> populateOptions(){
        Map<Integer, List<String>> optionsMap = new LinkedHashMap<>();

        List<String> answers1 = new ArrayList<>();
        answers1.add("Isang 3-in-1 insurance product na may kalakip na Daily Hospital Income (DHI), Intensive Care Unit Income (ICUI), at Personal Accident (PA)");
        answers1.add("Isang Group Personal Accident Insurance para sa Security Guards");
        answers1.add("Isang Personal Property insurance para sa Micro, Small and Medium Enterprises o Businesses");

        List<String> answers2 = new ArrayList<>();
        answers2.add("1-18 years old");
        answers2.add("18-64 years old");
        answers2.add("65-80 years old");

        List<String> answers3 = new ArrayList<>();
        answers3.add("100");
        answers3.add("200");
        answers3.add("300");

        List<String> answers4 = new ArrayList<>();
        answers4.add("1,200");
        answers4.add("2,400");
        answers4.add("4,800");

        List<String> answers5 = new ArrayList<>();
        answers5.add("Oo");
        answers5.add("Hindi");

        List<String> answers6 = new ArrayList<>();
        answers6.add("Isa");
        answers6.add("Dalawa");
        answers6.add("Tatlo");

        List<String> answers7 = new ArrayList<>();
        answers7.add("Tama");
        answers7.add("Mali");

        List<String> answers8 = new ArrayList<>();
        answers8.add("Death");
        answers8.add("Natural Disasters");
        answers8.add("Environmental Calamities");

        List<String> answers9 = new ArrayList<>();
        answers9.add("Ambulance");
        answers9.add("Insurance");
        answers9.add("Entrance");

        List<String> answers10 = new ArrayList<>();
        answers10.add("Disc");
        answers10.add("Risk");
        answers10.add("Bisque");

        optionsMap.put(1, answers1);
        optionsMap.put(2, answers2);
        optionsMap.put(3, answers3);
        optionsMap.put(4, answers4);
        optionsMap.put(5, answers5);
        optionsMap.put(6, answers6);
        optionsMap.put(7, answers7);
        optionsMap.put(8, answers8);
        optionsMap.put(9, answers9);
        optionsMap.put(10, answers10);
        return optionsMap;
    }
}
