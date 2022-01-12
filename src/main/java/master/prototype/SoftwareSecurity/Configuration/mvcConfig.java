package master.prototype.SoftwareSecurity.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class mvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imgUploadDir = Paths.get("./qa-images");
        String imgUploadPath = imgUploadDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/qa-images/**").addResourceLocations("file:/" + imgUploadPath + "/");

    }
}
