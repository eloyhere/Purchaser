package team.item.purchaser.forum.provider;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import team.item.purchaser.forum.exception.CaptchaCreateException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component(value = "CaptchaProvider")
public class CaptchaProvider {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    public Captcha captcha(){
        try(ByteArrayOutputStream stream = new ByteArrayOutputStream()){
            String captcha = defaultKaptcha.createText();
            BufferedImage image = defaultKaptcha.createImage(captcha);
            ImageIO.write(image, "png", stream);
            String base64 = "data:image/png;base64,"+ Base64.getEncoder().encodeToString(stream.toByteArray());
            return new Captcha() {
                @Override
                public String text() {
                    return captcha;
                }

                @Override
                public String image() {
                    return base64;
                }
            };
        }catch (IOException exception){
            throw new CaptchaCreateException(exception);
        }
    }

    public static interface Captcha{

        public String text();

        public String image();
    }
}
