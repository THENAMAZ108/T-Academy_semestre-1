package backend.academy.project4.file;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageFormat {

    PNG("png"),
    BMP("bmp"),
    JPEG("jpg");

    private final String formatName;

}
