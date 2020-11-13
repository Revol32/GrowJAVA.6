package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String dir = "src/main/resources/sensitive_data.txt";
        String regexCardNumber = "\\d{4}\\s\\d{4}\\s\\d{4}\\s\\d{4}";
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = Pattern.compile(regexCardNumber).matcher(line);
        while (matcher.find()) {
            line = line.replace(matcher.group(), matcher.group().substring(0, 5) + "**** ****" + matcher.group().substring(14));
        }
        return line;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String dir = "src/main/resources/sensitive_data.txt";
        String regexAmount = "\\$\\{payment_amount}";
        String regexBalance = "\\$\\{balance}";
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(dir);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";
        try {
            line = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line.replaceAll(regexAmount, new DecimalFormat("#.##").format(paymentAmount)).replaceAll(regexBalance, new DecimalFormat("#.##").format(balance));
    }
}
