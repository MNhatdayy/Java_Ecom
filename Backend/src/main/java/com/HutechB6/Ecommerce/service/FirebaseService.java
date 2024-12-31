package com.HutechB6.Ecommerce.service;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.io.IOException;
import java.util.UUID;
@Service
public class FirebaseService {
    public String uploadImages(MultipartFile file) throws IOException {
        // Tạo tên file duy nhất
        String fileName = "images/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Bucket bucket = StorageClient.getInstance().bucket();

        // Tải file lên Firebase Storage
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // Lấy token tự động từ metadata
        Map<String, String> metadata = blob.getMetadata();

        // Lấy token từ metadata
        String token = metadata != null ? metadata.get("firebaseStorageDownloadTokens") : null;

        // Nếu không có token, tạo token mới
        if (token == null || token.isEmpty()) {
            token = UUID.randomUUID().toString();
            blob.toBuilder().setMetadata(Map.of("firebaseStorageDownloadTokens", token)).build().update();
        }

        // Trả về URL tải xuống có token
        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
                bucket.getName(),
                URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8),
                token
        );
    }

    public String uploadAvatars(MultipartFile file) throws IOException {
        // Tạo tên file duy nhất
        String fileName = "avatars/" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        Bucket bucket = StorageClient.getInstance().bucket();

        // Tải file lên Firebase Storage
        Blob blob = bucket.create(fileName, file.getBytes(), file.getContentType());

        // Lấy token tự động từ metadata
        Map<String, String> metadata = blob.getMetadata();

        // Lấy token từ metadata
        String token = metadata != null ? metadata.get("firebaseStorageDownloadTokens") : null;

        // Nếu không có token, tạo token mới
        if (token == null || token.isEmpty()) {
            token = UUID.randomUUID().toString();
            blob.toBuilder().setMetadata(Map.of("firebaseStorageDownloadTokens", token)).build().update();
        }

        // Trả về URL tải xuống có token
        return String.format(
                "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media&token=%s",
                bucket.getName(),
                URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8),
                token
        );
    }
}
