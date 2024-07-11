package citywars.finalproject.Model;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CaptchaModel {

    private List<Image> images;
    private String generatedCaptcha;

    public CaptchaModel() {
        images = new ArrayList<>();
        generatedCaptcha = "";
    }

    public List<Image> generateCaptcha() {
        images.clear(); // پاک کردن تصاویر قبلی اگر وجود داشته باشد
        generatedCaptcha = "";

        Random rand = new Random();
        int numDigits = rand.nextInt(3) + 5; // تولید تعداد رندوم ارقام بین 5 تا 7

        for (int i = 0; i < numDigits; i++) {
            int randomNumber = rand.nextInt(10); // تولید عدد رندوم بین 0 تا 9
            generatedCaptcha += randomNumber;
            Image distortedImage = selectDistortedImage(randomNumber);
            images.add(distortedImage);
        }

        return images;
    }

    public String getGeneratedCaptcha() {
        return generatedCaptcha;
    }

    private Image selectDistortedImage(int number) {
        String imagePath = "/images/num/image_" + number + ".png";
        return new Image(getClass().getResourceAsStream(imagePath));
    }
}

