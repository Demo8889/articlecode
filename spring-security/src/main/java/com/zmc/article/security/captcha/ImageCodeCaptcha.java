package com.zmc.article.security.captcha;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * @author: Demo
 * @date: 2021-03-22 10:29
 */
public class ImageCodeCaptcha {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageCodeCaptcha.class);

    // 图片的宽度。
    private int width = 160;
    // 图片的高度。
    private int height = 40;
    // 验证码字符个数
    private int codeCount = 4;
    // 验证码干扰线数
    private int lineCount = 30;
    // 验证码
    private String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;
    Random random = new Random();

    public ImageCodeCaptcha() {
        creatImage();
    }

    public ImageCodeCaptcha(int width, int height) {
        this.width = width;
        this.height = height;
        creatImage();
    }

    public ImageCodeCaptcha(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        creatImage();
    }

    public ImageCodeCaptcha(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        creatImage();
    }

    // 生成图片
    private void creatImage() {
        int fontWidth = width / codeCount;// 字体的宽度
        int fontHeight = height - 5;// 字体的高度
        int codeY = height - 8;
        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
        Graphics2D g = (Graphics2D) buffImg.getGraphics();

        // 设置透明背景色
/*        buffImg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g.dispose();
        g = buffImg.createGraphics();*/

        // 设置背景色
        g.setColor(new Color(226, 205, 150));
        g.fillRect(0, 0, width, height);
        // 设置字体
        Font font = new Font("Arial", Font.BOLD, fontHeight);
        g.setFont(font);
        // 添加噪点
        float yawpRate = 0.01f;// 噪声率
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            buffImg.setRGB(x, y, random.nextInt(255));
        }
        // 得到随机字符
        String str1 = randomStr(codeCount);
        this.code = str1;
        for (int i = 0; i < codeCount; i++) {
            String strRand = str1.substring(i, i + 1);
            //g.setColor(getRandColor(150, 255));
            g.setColor(new Color(0, 0, 0));

            int x = i * fontWidth + 3;
            if (i == 0 || i == codeCount - 1) {
                g.drawString(strRand, x, codeY);
            } else {
                int route = random.nextInt(60) - 30;
                double theta = route * Math.PI / 180;
                g.rotate(theta, x, codeY);
                g.drawString(strRand, x, codeY);
                g.rotate(-theta, x, codeY);
            }
        }

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }
    }

    // 得到随机字符
    private String randomStr(int n) {
        String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        String str2 = "";
        int len = str1.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            str2 = str2 + str1.charAt((int) r);
        }
        return str2;
    }

    // 得到随机颜色
    private Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
    }

    public String getBase64Data() {
        String data = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(buffImg, "png", baos);
            BASE64Encoder encoder = new BASE64Encoder();
            data = encoder.encode(baos.toByteArray());
            data = data.replaceAll("\\s", "");
            data = "data:image/jpg;base64," + data;
        } catch (Exception e) {
            LOGGER.error("验证码生成失败", e);
        }
        return data;
    }

    public String getCode() {
        return code;
    }


}
