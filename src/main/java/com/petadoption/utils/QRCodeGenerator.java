package com.petadoption.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.UUID;

/**
 * 二维码生成工具类
 */
@Component
public class QRCodeGenerator {

    private static final String QR_CODE_PATH = "qrcodes/";
    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;
    private static final String IMAGE_FORMAT = "png";

    /**
     * 生成二维码
     */
    public String generateQRCode(String data) {
        try {
            // 创建qrcodes目录
            File dir = new File(QR_CODE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // 生成文件名
            String fileName = UUID.randomUUID().toString() + "." + IMAGE_FORMAT;
            String filePath = QR_CODE_PATH + fileName;

            // 生成二维码
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, WIDTH, HEIGHT);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, IMAGE_FORMAT, path);

            return "/" + filePath; // 返回相对路径
        } catch (Exception e) {
            throw new RuntimeException("二维码生成失败: " + e.getMessage());
        }
    }

    /**
     * 批量生成二维码
     */
    public String generateQRCodeBatch(String data, int count) {
        // 这里可以实现批量生成的逻辑
        return generateQRCode(data);
    }
}
