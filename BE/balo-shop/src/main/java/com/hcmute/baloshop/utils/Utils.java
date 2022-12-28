package com.hcmute.baloshop.utils;


import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

//    public static final Environment ENVIRONMENT = Environment.selectEnv("dev");
    public static final String URL = "http://192.168.1.8:8080/api/v1/";
    public static final String URL_FE = "localhost:5173";
    public static final String COLOR_ATTRIBUTE = "Màu";
    public static final String ROM_ATTRIBUTE = "ROM";
    public static final String RAM_ATTRIBUTE = "RAM";
    public static final String PRODUCT_EXISTED = "Product is existed";
    public static final String PRODUCT_NOT_FOUND = "Product not found";
    public static final String NO_PRODUCT = "No product were found";
    public static final Boolean PRODUCT_TRADING = true;
    public static final Boolean PRODUCT_STOP_TRADE = false;
    public static final int LENGTH_DISCOUNT_CODE = 10;
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_PAGE_NUMBER = "1";
    public static final String DEFAULT_TOP = "10";
    public static final String NOT_FOUND_DISCOUNT_BY_ID = "Not found discount by id %s";
    public static final String NOT_FOUND_DISCOUNT_BY_CODE = "Not found discount by code %s";
    public static final String DISCOUNT_CODE_IS_EXIST = "Discount code %s is existed";
    public static final String BILL_TO_PAY = "To pay";
    public static final String BILL_TO_SHIP = "To ship";
    public static final String BILL_TO_RECEIVE = "To receive";
    public static final String BILL_COMPLETED = "Completed";
    public static final String BILL_CANCELLED = "Cancelled";
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");
    public static final Pattern EMAILPATTERN =
            Pattern.compile("^[a-zA-Z0-9_!#$%&'*+|=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    public static final Pattern PHONEPATTERN = Pattern.compile("(0|84)?[0-9]{9}");

    public static String toSlug(String input) {
        String noWhitespace;
        try{
            noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        }catch (Exception e){
            throw new RuntimeException();
        }
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

//    public static double calculateAvgRate(List<Feedback> feedbackList) {
//        double totalRate = 0.0;
//        if (feedbackList.isEmpty()) {
//            return 0.0;
//        } else {
//            for (Feedback fb : feedbackList) {
//                totalRate += fb.getRate();
//            }
//        }
//        return totalRate / feedbackList.size();
//    }

    public static boolean isEmptyString(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean isValidEmail(String emailAddress) {
        return EMAILPATTERN.matcher(emailAddress).matches();
    }

    public static boolean isValidPhone(String phone) {
        Matcher match = PHONEPATTERN.matcher(phone);
        return (match.find() && match.group().equals(phone));
    }

//    public static boolean isValidQuantity(
//            Long productId, Integer availableQuantity, Integer newQuantity) {
//        if (newQuantity < 1) {
//            if (newQuantity == 0) {
//                return false;
//            } else {
//                throw new InvalidFieldException("Quantity field has an invalid value");
//            }
//        } else if (availableQuantity < newQuantity) {
//            throw new InvalidFieldException(
//                    "The available quantity of a product with id = "
//                            + productId
//                            + " not enough for this bill");
//        }
//        return true;
//    }

    public static boolean isValidDate(Date startDate, Date endDate) {
        Date currentDate = new Date();
        return currentDate.after(startDate) && currentDate.before(endDate);
    }

    public static boolean isValidCancelStatus(String status) {
        return Objects.equals(status, BILL_TO_PAY) || Objects.equals(status, BILL_TO_SHIP);
    }

    public static String getBaseURL(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        StringBuffer url = new StringBuffer();
        url.append(scheme).append("://").append(serverName);
        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }
        url.append(contextPath);
        if (url.toString().endsWith("/")) {
            url.append("/");
        }
        return url.toString();
    }

    public static List<String> splitStringByComma(String string){
        List<String> result = Arrays.asList(string.split(", "));
        return result;
    }

    public static String removeCharacterAfterDot(String string){
        String result = "";
        for(int i =0;i< string.length();i++){
            if(string.charAt(i) == '.'){
                break;
            }
            result += string.charAt(i);
        }
        return result;
    }

    public static String removeWhitespace(String string){
        List<String> resultList = Arrays.asList(string.split(" "));
        String result = "";
        for(String str : resultList){
            result += str;
        }

        return result;
    }
}